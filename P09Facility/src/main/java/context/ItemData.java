package context;

public class ItemData {

    private String item;
    private String shippedQty;

    public ItemData(String item, String shippedQty) {
        this.item = item;
        this.shippedQty = shippedQty;
    }

    public String getItem() {
        return item;
    }

    public String getShippedQty() {
        return shippedQty;
    }

    @Override
    public String toString() {
        return "ItemData{" +
                "item='" + item + '\'' +
                ", shippedQty='" + shippedQty + '\'' +
                '}';
    }
}