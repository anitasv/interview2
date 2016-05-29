package com.company.interview.merge;

import lombok.Getter;

import java.nio.file.Path;
import java.nio.file.Paths;

@Getter
public enum FileType {
    MAIN_JAVA(Paths.get("main", "java")),
    MAIN_RESOURCE(Paths.get("main", "resources")),
    TEST_JAVA(Paths.get("test", "java")),
    TEST_RESOURCE(Paths.get("test", "resources"));

    private final Path path;

    FileType(Path path) {
        this.path = path;
    }
}
