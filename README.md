# incoming-order-splitter

This microservice reads a message from the RabbitMQ message broker queue Queue.OrderSystem.NewOrderBatch and using the Splitter, splits it into separate messages, where each message contains information about one separate item. Those messages are stored in the RAM queue direct:splitter.

Then this service reads messages from RAM queue and sends them to another memory queue direct:mockWiretap in order to demonstrate Wiretap process, and also post messages in the RAM queue direct:filterTestMessages, from which messages are read by Message Filter, which filters them according to the logic created in the program.

Unfiltered messages are put into the RabbitMQ message broker queue Queue.OrderSystem.NewOrderLine.
