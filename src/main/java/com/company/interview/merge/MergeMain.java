package com.company.interview.merge;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class MergeMain {

    public static void main(String[] args) throws IOException {
        Args parsed = Args.parse(args);

        String modelDirArg = parsed.getString("model", "C:\\Users\\anita\\Downloads\\merge.tar\\merge\\model");
        String sourceDirArg = parsed.getString("source", "C:\\Users\\anita\\Downloads\\merge.tar\\merge\\source");
        String destDirArg = parsed.getString("dest", "C:\\Users\\anita\\Downloads\\merge.tar\\merge\\dest");
        boolean skipMismatch = parsed.getBoolean("skipMismatch", true);
        boolean dryRun = parsed.getBoolean("dryRun", false);
        boolean overwrite = parsed.getBoolean("overwrite", false);

        Path modelDir = Paths.get(modelDirArg);
        Model model = Model.create(modelDir);

        Path srcDir = Paths.get(sourceDirArg);
        Matching matching = Matching.create(model, srcDir, skipMismatch);

        Map<Path, ModelVal> matched = matching.getMatched();
        Map<Path, Path> instruction = getCopyInstruction(destDirArg, matched);
        executeCopy(instruction, overwrite, dryRun);
    }

    private static void executeCopy(Map<Path, Path> instruction, boolean overwrite, boolean dryRun) throws IOException {
        for (Map.Entry<Path, Path> entry : instruction.entrySet()) {
            Path targetPath = entry.getValue();
            Path targetDir = targetPath.getParent();
            File targetDirFile = targetDir.toFile();
            if (targetDirFile.exists()) {
                if (!targetDirFile.isDirectory()) {
                    System.err.println("Target is not a directory: " + targetDir);
                    continue;
                }
            } else {
                System.out.println("Making directory: " +  targetDir);
                if (!dryRun) {
                    if (!targetDirFile.mkdirs()) {
                        System.err.println("Failed to create directory: " + targetDir);
                        continue;
                    }
                }
            }
            if (targetPath.toFile().exists() && !overwrite) {
                System.err.println("Not overwriting (Pass --overwrite): " + targetPath);
                continue;
            }
            System.out.println("cp " + entry.getKey() + " => " + entry.getValue());
            if (!dryRun) {
                Files.copy(entry.getKey(), targetPath);
            }
        }
    }

    private static Map<Path, Path> getCopyInstruction(String destDirArg, Map<Path, ModelVal> matched) {
        Map<Path, Path> instruction = new HashMap<>();

        Path destDir = Paths.get(destDirArg);

        for (Map.Entry<Path, ModelVal> entry : matched.entrySet()) {
            Path target = entry.getValue().getTarget();
            Path project = target.getName(0).resolve(target.getName(1));
            Path typeDir = entry.getValue().getFileType();
            if (typeDir == null) {
                System.err.println("Internal type error: " + entry.getValue().getFileType());
            }
            Path strippedDir = project.relativize(target);
            Path targetPath = destDir.resolve(project).resolve(typeDir).resolve(strippedDir);
            instruction.put(entry.getKey(), targetPath);
        }
        return instruction;
    }

}
