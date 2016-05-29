package com.company.interview.merge;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class MergeUtils {

    public static List<ScanResult> scanDir(Path modelDir) {
        List<ScanResult> internal = new ArrayList<>();
        File dir = modelDir.toFile();
        recursiveAdd(internal, dir);
        return internal;
    }

    private static void recursiveAdd(List<ScanResult> internal, File dir) {
        boolean isDir = dir.isDirectory();
        internal.add(new ScanResult(isDir, dir.toPath()));
        if (isDir) {
            for (File file : dir.listFiles()) {
                recursiveAdd(internal, file);
            }
        }
    }

}
