package es.joanmiralles.adsense;

import es.joanmiralles.adsense.extractor.AdSenseCSVFile;
import es.joanmiralles.adsense.extractor.AdSenseExtractor;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class AdSense {
    private AdSenseExtractor adSenseExtractor;

    public AdSense(AdSenseExtractor adSenseExtractor) {
        this.adSenseExtractor = adSenseExtractor;
    }

    public static void main(String[] args) throws IOException {
        String pathToReportFile = args[0];
        Path pathToReportDir = Paths.get(pathToReportFile);
        AdSense adSense = new AdSense(new AdSenseExtractor());
        try(Stream<Path> paths = Files.walk(pathToReportDir).sorted()) {
            paths.forEach(adSense::readAdSenseReport);
        }
    }

    private void readAdSenseReport(Path path) {
        if (path.toFile().isFile()) {
            AdSenseCSVFile outputFile;
            try {
                outputFile = adSenseExtractor.unzip(path);
                outputFile.printReport();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
