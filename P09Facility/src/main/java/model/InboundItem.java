package model;

public class InboundItem {

    private final String item;
    private final String quantity;

    public InboundItem(String item, String quantity) {
        this.item = item;
        this.quantity = quantity;
    }

    public String getItem() {
        return item;
    }

    public String getQuantity() {
        return quantity;
    }

    @Override
    public String toString() {
        return "Item=" + item + ", Quantity=" + quantity;
    }
}