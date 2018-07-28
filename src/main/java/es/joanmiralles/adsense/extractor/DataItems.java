package es.joanmiralles.adsense.extractor;

import java.util.List;

class DataItems {

    private final List<DataItem> list;

    DataItems(List<DataItem> list) {
        this.list = list;
    }


    public List<DataItem> getList() {
        return list;
    }

    public int size() {
        return this.list.size();
    }

    public DataItem get(int index) {
        return this.list.get(index);
    }
}

