package com.company.interview.merge;

import lombok.Data;

import java.nio.file.Path;

@Data
public class ModelVal {

    private final Path fileType;

    private final boolean directory;

    private final Path target;
}
