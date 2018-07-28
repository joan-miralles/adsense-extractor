package es.joanmiralles.adsense.extractor;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class AdSenseExtractor {
    public static final Charset CSV_FILE_CHARSET = Charset.forName("UTF-16");

    public AdSenseCSVFile unzip(Path path) throws IOException {
        AdSenseCSVFile adSenseCSVFile = null;
        byte[] buffer = new byte[1024];
        try (ZipInputStream zis = new ZipInputStream(new FileInputStream(path.toString()), CSV_FILE_CHARSET)) {
            ZipEntry zipEntry = zis.getNextEntry();
            if (zipEntry != null) {
                String fileName = zipEntry.getName();
                File newFile = new File(System.getProperty("java.io.tmpdir") + File.separator + fileName);
                try (FileOutputStream fos = new FileOutputStream(newFile)) {
                    int len;
                    while ((len = zis.read(buffer)) > 0) {
                        fos.write(buffer, 0, len);
                    }
                }
                adSenseCSVFile = new AdSenseCSVFile(path.toString(), newFile);
            }
            zis.closeEntry();
        }
        return adSenseCSVFile;
    }
}
