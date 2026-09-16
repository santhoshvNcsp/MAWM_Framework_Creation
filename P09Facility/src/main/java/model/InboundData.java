package model;

import java.util.List;

public class InboundData {

    private final String ilpn;
    private final String location;
    private final List<InboundItem> items;

    public InboundData(String ilpn, String location, List<InboundItem> items) {
        this.ilpn = ilpn;
        this.location = location;
        this.items = items;
    }

    public String getIlpn() {
        return ilpn;
    }

    public String getLocation() {
        return location;
    }

    public List<InboundItem> getItems() {
        return items;
    }

    @Override
    public String toString() {
        return "ILPN=" + ilpn +
                ", Location=" + location +
                ", Items=" + items;
    }
}