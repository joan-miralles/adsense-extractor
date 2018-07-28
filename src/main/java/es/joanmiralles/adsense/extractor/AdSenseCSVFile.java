package es.joanmiralles.adsense.extractor;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static es.joanmiralles.adsense.extractor.AdSenseExtractor.CSV_FILE_CHARSET;

class AdSenseCSVFile {
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
