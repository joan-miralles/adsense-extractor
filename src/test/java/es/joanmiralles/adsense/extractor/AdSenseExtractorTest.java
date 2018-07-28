package es.joanmiralles.adsense.extractor;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.math.BigDecimal;
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
    private AdSenseCSVFileHeader header;
    private AdSenseCSVFileData data;

    @Before
    public void setUp() throws URISyntaxException, IOException {
        Path path = Paths.get(Objects.requireNonNull(this.getClass().getClassLoader().getResource("Report.zip")).toURI());
        adSenseExtractor = new AdSenseExtractor();
        outputFile = adSenseExtractor.unzip(path);
        header = outputFile.getHeader();
        data = outputFile.getData();
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
        assertThat(header.getColumn(0), is("Fecha"));
        assertThat(header.getColumn(1), is("Páginas vistas"));
        assertThat(header.getColumn(2), is("Clics"));
        assertThat(header.getColumn(3), is("CTR pág."));
        assertThat(header.getColumn(4), is("CPC (EUR)"));
        assertThat(header.getColumn(5), is("RPM pág. (EUR)"));
        assertThat(header.getColumn(6), is("Ingresos estimados (EUR)"));
    }

    @Test
    public void getDataFile() {
        DataItems items = data.getItems();
        assertThat(items.size(), is(28));
    }

    @Test
    public void getDataFromFirstItem() {
        DataItem firstItem = data.getItems().get(0);
        assertThat(firstItem.getDate(), is("2018-07-01"));
        assertThat(firstItem.getPageViews(), is(595));
        assertThat(firstItem.getClics(), is(9));
        assertThat(firstItem.getCtrPercentage(), is(BigDecimal.valueOf(1.51)));
        assertThat(firstItem.getCpc(), is(BigDecimal.valueOf(0.18)));
        assertThat(firstItem.getRpmPag(), is(BigDecimal.valueOf(2.79)));
        assertThat(firstItem.getEstimatedEarnings(), is(BigDecimal.valueOf(1.66)));
    }

    @Test
    public void getTotalEstimatedEarnings() {
        assertThat(data.getTotalEstimatedEarnings(), is(65.7));
    }

    @Test
    public void testPrintReport() {
        outputFile.printReport();
    }
}

