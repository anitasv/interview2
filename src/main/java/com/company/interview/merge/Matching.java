package com.company.interview.merge;

import java.nio.file.Path;
import java.util.*;

public class Matching {

    private final Map<Path, Set<ModelVal>> targetMap = new HashMap<>();

    private final Map<ModelVal, Set<Path>> sourceMap = new HashMap<>();

    private void addConnection(Path src, ModelVal target) {
        targetMap.computeIfAbsent(src, (x) -> new HashSet<>()).add(target);
        sourceMap.computeIfAbsent(target, (x) -> new HashSet<>()).add(src);
    }

    public boolean checkConflicts() {
        boolean success = true;
        for (Map.Entry<Path, Set<ModelVal>> entry : targetMap.entrySet()) {
            if (entry.getValue().size() > 1) {
                success = false;
                System.err.println("Multiple Targets: " + entry.getKey() + " -- " + entry.getValue());
            }
        }
        for (Map.Entry<ModelVal, Set<Path>> entry : sourceMap.entrySet()) {
            if (entry.getValue().size() > 1) {
                success = false;
                System.err.println("Overwrite: " + entry.getKey() + " -- " + entry.getValue());
            }
        }
        return success;
    }

    public Map<Path, ModelVal> getMatched() {
        Map<Path, ModelVal> matched = new HashMap<>();
        for (Map.Entry<Path, Set<ModelVal>> entry : targetMap.entrySet()) {
            for (ModelVal modelVal : entry.getValue()) {
                matched.put(entry.getKey(), modelVal);
            }
        }
        return matched;
    }

    public static Matching create(Model model, Path srcDir, boolean skipMismatch) {
        List<ScanResult> srcFiles = MergeUtils.scanDir(srcDir);
        Matching matching = new Matching();
        boolean success = true;
        for (ScanResult scanResult : srcFiles) {
            if (scanResult.isDirectory()) {
                continue;
            }
            ModelVal modelResult = model.lookup(scanResult.getPath());
            if (modelResult != null) {
                matching.addConnection(scanResult.getPath(), modelResult);
            } else {
                success = false;
                System.err.println("No match found for: " + srcDir.relativize(scanResult.getPath()));
            }
        }

        boolean noConflicts = matching.checkConflicts();
        if (!success || !noConflicts) {
            if (!skipMismatch) {
                System.err.println("Fix errors and restart, or pass --skipMismatch to continue");
                System.exit(1);
            }
        }
        return matching;
    }
}
