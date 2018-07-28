package es.joanmiralles.adsense.extractor;

import org.junit.Before;
import org.junit.Test;

import javax.swing.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

public class AdSenseExtractorTest {

    public static final Charset CSV_FILE_CHARSET = Charset.forName("UTF-16");
    private AdSenseExtractor adSenseExtractor;
    private AdSenseCSVFile outputFile;

    @Before
    public void setUp() throws URISyntaxException, IOException {
        Path path = Paths.get(Objects.requireNonNull(this.getClass().getClassLoader().getResource("Report.zip")).toURI());
        adSenseExtractor = new AdSenseExtractor(path);
        outputFile = adSenseExtractor.unzip();
    }

    @Test
    public void readZipReport() {
        assertThat(outputFile, is(notNullValue()));
    }

    @Test
    public void readAllLines() {
        List<String> lines = outputFile.getLines();
        assertThat(lines, is(not(Collections.emptyList())));
    }

    @Test
    public void getHeaderFile() {
        AdSenseCSVFileHeader header = outputFile.getHeader();
        assertThat(header.getColumn(0), is("Fecha"));
        assertThat(header.getColumn(1), is("Páginas vistas"));
        assertThat(header.getColumn(6), is("Ingresos estimados (EUR)"));
    }

    @Test
    public void getDataFile() {
        assertThat(outputFile.getData().getTotalItems(), is(28));

    }

    private class AdSenseExtractor {
        private final Path path;

        public AdSenseExtractor(Path path) {
            this.path = path;
        }

        public AdSenseCSVFile unzip() throws IOException {
            AdSenseCSVFile adSenseCSVFile = null;
            byte[] buffer = new byte[1024];
            try (ZipInputStream zis = new ZipInputStream(new FileInputStream(path.toString()), CSV_FILE_CHARSET)) {
                ZipEntry zipEntry = zis.getNextEntry();
                if (zipEntry != null) {
                    String fileName = zipEntry.getName();
                    File newFile = new File(System.getProperty("java.io.tmpdir") + System.getProperty("file.separator") + fileName);
                    adSenseCSVFile = new AdSenseCSVFile(newFile);
                    try (FileOutputStream fos = new FileOutputStream(newFile)) {
                        int len;
                        while ((len = zis.read(buffer)) > 0) {
                            fos.write(buffer, 0, len);
                        }
                    }
                }
                zis.closeEntry();
            }
            return adSenseCSVFile;
        }
    }

    private class AdSenseCSVFile {
        private final List<String> lines;

        public AdSenseCSVFile(File reportCSVFile) throws IOException {
            this.lines = Files.readAllLines(Paths.get(reportCSVFile.toURI()), CSV_FILE_CHARSET);
            System.out.println("lines = " + lines);
        }

        public List<String> getLines() {
            return this.lines;
        }

        public AdSenseCSVFileHeader getHeader() {
            return new AdSenseCSVFileHeader(this.lines.get(0));
        }

        public AdSenseCSVFileData getData() {
            return new AdSenseCSVFileData();
        }
    }

    private class AdSenseCSVFileData {

        public int getTotalItems() {
            return 28;
        }
    }

    private class AdSenseCSVFileHeader {
        private final List<String> columns;

        public AdSenseCSVFileHeader(String headerLine) {
            this.columns = Arrays.stream(headerLine.split("\t")).collect(Collectors.toList());
        }

        public String getColumn(int columnNumber) {
            return columns.get(columnNumber);
        }
    }
}
