package es.joanmiralles.adsense.extractor;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

import static es.joanmiralles.adsense.extractor.AdSenseExtractor.CSV_FILE_CHARSET;

public class AdSenseCSVFile {
    private String fileName;
    private final List<String> lines;
    private final AdSenseCSVFileHeader header;
    private final AdSenseCSVFileData data;

    public AdSenseCSVFile(String fileName, File reportCSVFile) throws IOException {
        this.fileName = fileName;
        this.lines = Files.readAllLines(Paths.get(reportCSVFile.toURI()), CSV_FILE_CHARSET);
        this.header = new AdSenseCSVFileHeader(this.lines.get(0));
        this.data = new AdSenseCSVFileData(this.lines.stream().skip(1).collect(Collectors.toList()));
    }

    public List<String> getLines() {
        return this.lines;
    }

    public AdSenseCSVFileHeader getHeader() {
        return this.header;
    }

    public AdSenseCSVFileData getData() {
        return this.data;
    }

    public void printReport() {
//        printHeader();
//        printEmptyLine();
//        printData();
        printTotal();
    }

    private void printTotal() {
        System.out.println(fileName + " :: " + "Total Estimated Earnings = " + this.data.getTotalEstimatedEarnings());
    }

    private void printData() {
        this.data.getItems().getList()
                .forEach(dataItem -> printValue(dataItem.format()));
    }

    private void printHeader() {
        System.out.println(this.header.format());
    }

    private void printEmptyLine() {
        System.out.println("\r\n");
    }

    private void printValue(String value) {
        System.out.println(value);
    }
}
