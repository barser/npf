package ru.ospos.npf.officeaddin.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class FileOperation {

    private UUID id;
    private String state;

    private String filename;
    private Integer pocardId;
    private String subpath;
}
