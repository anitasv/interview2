package com.company.interview.merge;

import lombok.Data;

import java.nio.file.Path;

@Data
public class ScanResult {

    private final boolean directory;

    private final Path path;
}
