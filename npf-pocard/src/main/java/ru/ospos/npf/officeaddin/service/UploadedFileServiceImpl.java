package ru.ospos.npf.officeaddin.service;

import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;
import ru.ospos.npf.commons.util.GenericNpfException;
import ru.ospos.npf.officeaddin.domain.FileOperation;

import java.io.File;
import java.io.IOException;

@Service
public class UploadedFileServiceImpl implements UploadedFileService {

    @Override
    public File getUploadedFile(FileOperation fo) {
        var file = FileUtils.getFile("R:\\", "TRANSFER", fo.getSubpath(), fo.getFilename()); // TODO path

        if (!file.exists()) {
            throw new GenericNpfException("Не удается прочитать файл " + fo.getFilename());
        }
        return file;
    }

    @Override
    public Long getChecksum(File file) {
        try {
            return FileUtils.checksumCRC32(file);
        } catch (IOException e) {
            throw new GenericNpfException("Не удается вычислить контрольную сумму для файла " + file.getName(), e);
        }
    }

    @Override
    public Long getSize(File file) {
        return FileUtils.sizeOf(file);
    }
}
