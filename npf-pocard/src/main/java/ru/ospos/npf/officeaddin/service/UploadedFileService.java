package ru.ospos.npf.officeaddin.service;

import ru.ospos.npf.officeaddin.domain.FileOperation;

import java.io.File;

public interface UploadedFileService {

    File getUploadedFile(FileOperation fileOperation);

    Long getChecksum(File file);

    Long getSize(File file);

}
