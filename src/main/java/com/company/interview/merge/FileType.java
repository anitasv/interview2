package com.company.interview.merge;

import java.nio.file.Path;
import java.nio.file.Paths;

public interface FileType {
    Path MAIN_JAVA = Paths.get("main", "java");
    Path MAIN_RESOURCE = Paths.get("main", "resources");
    Path TEST_JAVA = Paths.get("test", "java");
    Path TEST_RESOURCE = Paths.get("test", "resources");
}
