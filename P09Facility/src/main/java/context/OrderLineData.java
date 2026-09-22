package context;

public class OrderLineData {

    private String lineNumber;
    private String item;
    private String quantity;

    public OrderLineData(
            String lineNumber,
            String item,
            String quantity) {

        this.lineNumber = lineNumber;
        this.item = item;
        this.quantity = quantity;
    }

    public String getLineNumber() {
        return lineNumber;
    }

    public void setLineNumber(String lineNumber) {
        this.lineNumber = lineNumber;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "OrderLineData{" +
                "lineNumber='" + lineNumber + '\'' +
                ", item='" + item + '\'' +
                ", quantity='" + quantity + '\'' +
                '}';
    }
}