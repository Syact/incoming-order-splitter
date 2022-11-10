package lv.bluma.ordersys.processor;

import lv.bluma.ordersys.model.outgoing.IndividualOrderItem;
import org.apache.camel.Exchange;

public class TestMessageFilter {

    public boolean isNotTestMessage(Exchange exchange) {
        IndividualOrderItem orderItem = exchange.getIn().getBody(IndividualOrderItem.class);
        return !(orderItem.getExternalOrderId() == null || orderItem.getExternalOrderId().startsWith("test"));
    }
}
