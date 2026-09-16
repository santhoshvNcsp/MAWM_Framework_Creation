package context;

import context.ItemData;

import java.util.ArrayList;
import java.util.List;


public class ILPNData {

    private String ilpn;
    private List<ItemData> items;

    public ILPNData(String ilpn) {
        this.ilpn = ilpn;
        this.items = new ArrayList<>();
    }

    public String getIlpn() {
        return ilpn;
    }

    public void setIlpn(String ilpn) {
        this.ilpn = ilpn;
    }

    public List<ItemData> getItems() {
        return items;
    }

    public void addItem(ItemData item) {
        this.items.add(item);
    }

    @Override
    public String toString() {
        return "ILPNData{" +
                "ilpn='" + ilpn + '\'' +
                ", items=" + items +
                '}';
    }
}