package com.company.interview.merge;

import lombok.RequiredArgsConstructor;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
public class Model {

    private final Map<Path, List<ModelVal>> map;

    public ModelVal lookup(Path src) {

        Path fileName = src.getFileName();

        List<Path> srcParts = new ArrayList<>();
        for (Path srcPart : src) {
            srcParts.add(srcPart);
        }

        List<ModelVal> candidates = map.get(fileName);

        if (candidates == null) {
            return null;
        }

        int bestScore = -1;
        ModelVal bestCand = null;

        for (ModelVal cand : candidates) {
            List<Path> candParts = new ArrayList<>();
            for (Path candPart : cand.getTarget()) {
                candParts.add(candPart);
            }
            int score = 0;
            for (int i = 0; i < candParts.size() && i < srcParts.size(); i++) {
                Path candPart = candParts.get(candParts.size() - 1 - i);
                Path srcPart = srcParts.get(srcParts.size() - 1 - i);
                if (candPart.equals(srcPart)) {
                    score++;
                }
            }
            if (score > bestScore) {
                bestScore = score;
                bestCand = cand;
            }
        }

        return bestCand;
    }

    public static Model buildModel(Path modelDir) {
        List<ScanResult> internal = MergeUtils.scanDir(modelDir);

        Map<Path, List<ModelVal>> internalMap = convertToModelMap(modelDir, internal);

        return new Model(internalMap);
    }


    private static Map<Path, List<ModelVal>> convertToModelMap(Path modelDir, List<ScanResult> internal) {
        Map<Path, List<ModelVal>> internalMap = new HashMap<>();
        for (ScanResult scanResult : internal) {
            Path relativePath = modelDir.relativize(scanResult.getPath());
            if (relativePath.getNameCount() < 2) {
                continue;
            }
            if (relativePath.endsWith(".settings")) {
                continue;
            }
            Path typeName = relativePath.getName(1);
            FileType fileType = null;
            switch (typeName.toString()) {
                case "src" : fileType = FileType.MAIN_JAVA; break;
                case "src.resources" : fileType = FileType.MAIN_RESOURCE; break;
                case "tests.base" : fileType = FileType.TEST_JAVA; break;
                case "tests.system" : fileType = FileType.TEST_JAVA; break;
                case "tests.unit" : fileType = FileType.TEST_JAVA; break;
                case "tests.base.data" : fileType = FileType.TEST_RESOURCE; break;
                case "tests.base.resources" : fileType = FileType.TEST_RESOURCE; break;
                case "tests.unit.resources" : fileType = FileType.TEST_RESOURCE; break;
                case "tests.system.resources" : fileType = FileType.TEST_RESOURCE; break;
            }
            if (fileType != null) {
                ModelVal modelVal = new ModelVal(fileType, scanResult.isDirectory(), relativePath);
                internalMap.computeIfAbsent(scanResult.getPath().getFileName(), (x) -> new ArrayList<>()).add(modelVal);
            }
        }
        return internalMap;
    }

}
