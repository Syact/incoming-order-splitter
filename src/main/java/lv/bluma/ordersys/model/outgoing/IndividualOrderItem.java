package lv.bluma.ordersys.model.outgoing;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"orderItemId", "orderItemLineNo", "totalOrderItemsInOrder", "orderBatchId", "clientId",
        "externalOrderId", "itemId", "amount"})
public class IndividualOrderItem {

    @JsonProperty("orderItemId")
    private String orderItemId;

    @JsonProperty("orderItemLineNo")
    private Integer orderItemLineNo;

    @JsonProperty("totalOrderItemsInOrder")
    private Integer totalOrderItemsInOrder;

    @JsonProperty("orderBatchId")
    private String orderBatchId;

    @JsonProperty("clientId")
    private String clientId;

    @JsonProperty("externalOrderId")
    private String externalOrderId;

    @JsonProperty("itemId")
    private String itemId;

    @JsonProperty("amount")
    private Integer amount;

    public String getOrderItemId() {
        return orderItemId;
    }

    public void setOrderItemId(String orderItemId) {
        this.orderItemId = orderItemId;
    }

    public Integer getOrderItemLineNo() {
        return orderItemLineNo;
    }

    public void setOrderItemLineNo(Integer orderItemLineNo) {
        this.orderItemLineNo = orderItemLineNo;
    }

    public Integer getTotalOrderItemsInOrder() {
        return totalOrderItemsInOrder;
    }

    public void setTotalOrderItemsInOrder(Integer totalOrderItemsInOrder) {
        this.totalOrderItemsInOrder = totalOrderItemsInOrder;
    }

    public String getOrderBatchId() {
        return orderBatchId;
    }

    public void setOrderBatchId(String orderBatchId) {
        this.orderBatchId = orderBatchId;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getExternalOrderId() {
        return externalOrderId;
    }

    public void setExternalOrderId(String externalOrderId) {
        this.externalOrderId = externalOrderId;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }
}
