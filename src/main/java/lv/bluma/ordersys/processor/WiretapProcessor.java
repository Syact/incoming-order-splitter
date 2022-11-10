package lv.bluma.ordersys.processor;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

public class WiretapProcessor implements Processor {

    @Override
    public void process(Exchange exchange) {
        String body = exchange.getIn().getBody(String.class);
        System.out.println("\nWiretapping:");
        System.out.println(body);
        System.out.println();
    }
}
