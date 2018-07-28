package es.joanmiralles.adsense.extractor;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

class AdSenseCSVFileData {

    private final DataItems dataItems;

    AdSenseCSVFileData(List<String> linesDataItems) {
        this.dataItems = new DataItems(linesDataItems.stream().map(DataItem::new).collect(Collectors.toList()));
    }

    public DataItems getItems() {
        return this.dataItems;
    }

    public Double getTotalEstimatedEarnings() {
        return this.getItems().getList().stream().map(DataItem::getEstimatedEarnings).mapToDouble(BigDecimal::doubleValue).sum();
    }
}
