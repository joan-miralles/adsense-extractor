package es.joanmiralles.adsense.extractor;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

class AdSenseCSVFileHeader {
    private final List<String> columns;

    public AdSenseCSVFileHeader(String headerLine) {
        this.columns = Arrays.stream(headerLine.split("\t")).collect(Collectors.toList());
    }

    public List<String> getColumns() {
        return columns;
    }

    public String getColumn(int columnNumber) {
        return columns.get(columnNumber);
    }

    public String format() {
        return String.join("\t", columns);
    }
}
