package context;

import java.util.ArrayList;
import java.util.List;

public class OrderData {

    private String orderId;
    private String orderType;
    private String orderPriority;
    private int orderLineCount;

    private final List<OrderLineData> orderLines =
            new ArrayList<>();

    public OrderData() {
    }

    public OrderData(String orderId) {
        this.orderId = orderId;
    }

    public OrderData(
            String orderId,
            String orderType,
            String orderPriority,
            int orderLineCount) {

        this.orderId = orderId;
        this.orderType = orderType;
        this.orderPriority = orderPriority;
        this.orderLineCount = orderLineCount;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getOrderPriority() {
        return orderPriority;
    }

    public void setOrderPriority(String orderPriority) {
        this.orderPriority = orderPriority;
    }

    public int getOrderLineCount() {
        return orderLineCount;
    }

    public void setOrderLineCount(int orderLineCount) {
        this.orderLineCount = orderLineCount;
    }

    public List<OrderLineData> getOrderLines() {
        return orderLines;
    }

    public void addOrderLine(OrderLineData orderLineData) {
        this.orderLines.add(orderLineData);
    }

    @Override
    public String toString() {
        return "OrderData{" +
                "orderId='" + orderId + '\'' +
                ", orderType='" + orderType + '\'' +
                ", orderPriority='" + orderPriority + '\'' +
                ", orderLineCount=" + orderLineCount +
                ", orderLines=" + orderLines +
                '}';
    }
}