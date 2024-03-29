package io.github.geniot.elex.tools.compile;

import io.github.geniot.elex.ezip.model.ElexDictionary;
import org.apache.commons.io.FileUtils;

import java.io.File;

public class ByteArrayProvider {
    private File file;
    private byte[] bbs;
    private ElexDictionary elexDictionary;
    private String header;

    public ByteArrayProvider(File f) {
        this.file = f;
    }
    public ByteArrayProvider(byte[] bbs) {
        this.bbs = bbs;
    }

    public ByteArrayProvider(ElexDictionary elexDictionary, String header) {
        this.elexDictionary = elexDictionary;
        this.header = header;
    }

    public byte[] getBytes() {
        try {
            if (bbs != null) {
                return bbs;
            } else if (file == null) {
                return elexDictionary.readResource(header);
            } else {
                return FileUtils.readFileToByteArray(file);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
