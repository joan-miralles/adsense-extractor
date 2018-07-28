package es.joanmiralles.adsense.extractor;

import lombok.Data;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

@Data
class DataItem {
    private final String date;
    private final int pageViews;
    private final int clics;
    private final BigDecimal ctrPercentage;
    private final BigDecimal cpc;
    private final BigDecimal rpmPag;
    private final BigDecimal estimatedEarnings;

    public DataItem(String lineDataItem) {
        String[] splittedItem = lineDataItem.split("\t");
        this.date = splittedItem[0];
        this.pageViews = Integer.parseInt(splittedItem[1]);
        this.clics = Integer.parseInt(splittedItem[2]);
        this.ctrPercentage = convertBigDecimalValue(splittedItem[3].replaceAll("\"", "").replaceAll("%", ""));
        this.cpc = convertBigDecimalValue(splittedItem[4].replaceAll("\"", ""));
        this.rpmPag = convertBigDecimalValue(splittedItem[5].replaceAll("\"", ""));
        this.estimatedEarnings = convertBigDecimalValue(splittedItem[6].replaceAll("\"", ""));
    }

    private BigDecimal convertBigDecimalValue(String stringValue) {
        NumberFormat df = DecimalFormat.getInstance(new Locale("ES"));
        Double doubleValue;
        try {
            doubleValue = df.parse(stringValue).doubleValue();
        } catch (ParseException e) {
            doubleValue = 0.0d;
        }
        return BigDecimal.valueOf(doubleValue);
    }

    public String format() {
        return this.date + "\t" +
                this.pageViews + "\t" +
                this.clics + "\t" +
                this.ctrPercentage + "\t" +
                this.cpc + "\t" +
                this.rpmPag + "\t" +
                this.estimatedEarnings + "\t";
    }
}
