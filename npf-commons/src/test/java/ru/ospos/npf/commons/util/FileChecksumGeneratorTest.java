package ru.ospos.npf.commons.util;

import org.apache.commons.io.FileUtils;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

public class FileChecksumGeneratorTest {

    @Test
    public void when_GenerateChecksum_Expect_Correct() throws IOException, NoSuchAlgorithmException {

        var file = FileUtils.getFile("src", "test", "resources", "sampleFile.txt");

        assertEquals(3_506_599_308L, FileUtils.checksumCRC32(file));
        assertEquals("ae1950f9dd407ee5ebef851e7d0d0d1f", FileChecksumGenerator.getFileMD5Checksum(file));
        assertEquals("9bd87921ca839d6053d019c7fbff017690df6f42", FileChecksumGenerator.getFileSHAChecksum(file));
    }

}
