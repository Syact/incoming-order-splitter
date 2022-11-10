package lv.bluma.ordersys.processor;

import lv.bluma.ordersys.model.incoming.Order;
import lv.bluma.ordersys.model.incoming.OrderBatch;
import lv.bluma.ordersys.model.incoming.OrderItem;
import lv.bluma.ordersys.model.outgoing.IndividualOrderItem;
import org.apache.camel.Exchange;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class OrderSplitter {

    public List<IndividualOrderItem> splitOrder(Exchange exchange) {
        OrderBatch orderBatch = exchange.getIn().getBody(OrderBatch.class);
        if (orderBatch == null || orderBatch.getOrders() == null) {
            throw new RuntimeException("No orders in order batch ");
        }

        List<IndividualOrderItem> orderItems = new ArrayList<>();
        for (Order order : orderBatch.getOrders()) {
            if (order.getOrderItems() == null)
                continue;
            int currentIndex = 0;
            for (OrderItem orderItem : order.getOrderItems()) {
                IndividualOrderItem individualOrderItem = new IndividualOrderItem();
                individualOrderItem.setOrderItemId(UUID.randomUUID().toString());
                individualOrderItem.setOrderItemLineNo(currentIndex++);
                individualOrderItem.setTotalOrderItemsInOrder(order.getOrderItems().size());
                individualOrderItem.setOrderBatchId(orderBatch.getBatchId());
                individualOrderItem.setClientId(order.getClientId());
                individualOrderItem.setExternalOrderId(order.getExternalOrderId());
                individualOrderItem.setItemId(orderItem.getItemId());
                individualOrderItem.setAmount(orderItem.getAmount());
                orderItems.add(individualOrderItem);
            }
        }
        return orderItems;
    }
}
