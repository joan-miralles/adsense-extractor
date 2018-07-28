package es.joanmiralles.adsense.extractor;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

public class AdSenseExtractorTest {

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

}

