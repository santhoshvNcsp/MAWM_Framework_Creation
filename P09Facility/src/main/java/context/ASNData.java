package context;

import java.util.ArrayList;
import java.util.List;

public class ASNData {

    private String asn;

    // Items that were created as part of ASN creation
    private List<ItemData> items;

    // ILPNs can be populated later during receiving
    private List<ILPNData> ilpns;

    public ASNData(String asn) {
        this.asn = asn;
        this.items = new ArrayList<>();
        this.ilpns = new ArrayList<>();
    }

    public String getAsn() {
        return asn;
    }

    public List<ItemData> getItems() {
        return items;
    }

    public List<ILPNData> getIlpns() {
        return ilpns;
    }

    public void addItem(ItemData item) {
        this.items.add(item);
    }

    public void addIlpn(ILPNData ilpn) {
        this.ilpns.add(ilpn);
    }

    @Override
    public String toString() {
        return "ASNData{" +
                "asn='" + asn + '\'' +
                ", items=" + items +
                ", ilpns=" + ilpns +
                '}';
    }
}