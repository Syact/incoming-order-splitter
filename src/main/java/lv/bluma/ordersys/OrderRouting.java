package lv.bluma.ordersys;

import lv.bluma.ordersys.model.incoming.OrderBatch;
import lv.bluma.ordersys.model.outgoing.IndividualOrderItem;
import lv.bluma.ordersys.processor.OrderSplitter;
import lv.bluma.ordersys.processor.TestMessageFilter;
import lv.bluma.ordersys.processor.WiretapProcessor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.springframework.stereotype.Component;

@Component
public class OrderRouting extends RouteBuilder {

    private static final JacksonDataFormat INDIVIDUAL_ORDER_ITEM_DATA_FORMAT =
            new JacksonDataFormat(IndividualOrderItem.class);

    private static final String NEW_ORDER_BATCH_QUEUE = "rabbitmq://localhost:5672/" +
            "Exchange.OrderSystem.NewOrderBatch?" +
            "queue=Queue.OrderSystem.NewOrderBatch&autoDelete=false";

    private static final String NEW_ORDER_LINE_QUEUE = "rabbitmq://localhost:5672/" +
            "Exchange.OrderSystem.NewOrderLine?" +
            "queue=Queue.OrderSystem.NewOrderLine&autoDelete=false";

    private static final String DIRECT_SPLITTER = "direct:splitter";
    private static final String DIRECT_MOCK_WIRETAP = "direct:mockWiretap";
    private static final String DIRECT_FILTER_TEST_MESSAGES = "direct:filterTestMessages";

    @Override
    public void configure() {
        setupDeadLetterChannel();
        setupIncomingJsonConversion();
        setupSplitterRoute();
        setupMockWiretapRoute();
        setupTestMessageFilterRoute();
    }

    private void setupIncomingJsonConversion() {
        JacksonDataFormat orderBatchDataFormat = new JacksonDataFormat(OrderBatch.class);

        from(NEW_ORDER_BATCH_QUEUE)
                .routeId("incoming-order-batch-route")
                .log("Processing incoming order batch: ${body}")
                .unmarshal(orderBatchDataFormat) // no String uz objektu, kas reprezentē OrderBatch objektu
                .to(DIRECT_SPLITTER)
                .end();
    }

    private void setupSplitterRoute() {
        from(DIRECT_SPLITTER)
                .routeId("order-splitter-route")
                .split(method(new OrderSplitter(), "splitOrder"))
                .wireTap(DIRECT_MOCK_WIRETAP)
                .to(DIRECT_FILTER_TEST_MESSAGES)
                .end();
    }

    private void setupMockWiretapRoute() {
        from(DIRECT_MOCK_WIRETAP)
                .routeId("mock-wiretap-route")
                .marshal(INDIVIDUAL_ORDER_ITEM_DATA_FORMAT) // no objekta uz String
                .process(new WiretapProcessor())
                .end();
    }

    private void setupTestMessageFilterRoute() {
        from(DIRECT_FILTER_TEST_MESSAGES)
                .routeId("test-message-filter-route")
                .filter(method(new TestMessageFilter(), "isNotTestMessage"))
                .marshal(INDIVIDUAL_ORDER_ITEM_DATA_FORMAT)// no objekta uz String
                .to(NEW_ORDER_LINE_QUEUE)
                .end();
    }

    private void setupDeadLetterChannel() {
        errorHandler(deadLetterChannel(
                "rabbitmq://localhost:5672/Exchange.OrderSystem.DLQ" +
                        "?queue=Queue.OrderSystem.DLQ" +
                        "&autoDelete=false" +
                        "&arg.queue.x-message-ttl=60000")
        );
    }
}
