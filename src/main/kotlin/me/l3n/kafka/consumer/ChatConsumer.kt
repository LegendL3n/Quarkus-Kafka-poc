package me.l3n.kafka.consumer

import io.smallrye.reactive.messaging.kafka.KafkaRecord
import me.l3n.kafka.MessageKey
import me.l3n.kafka.MessageValue
import org.eclipse.microprofile.reactive.messaging.Incoming
import org.jboss.logging.Logger
import java.util.concurrent.CompletionStage
import javax.enterprise.context.ApplicationScoped
import javax.inject.Inject

@ApplicationScoped
class ChatConsumer {

    @Inject
    private lateinit var log: Logger

    @Incoming("messages-in")
    fun consume(record: KafkaRecord<MessageKey, MessageValue>): CompletionStage<Void> {
        val payload = record.payload

        if (payload.author == "Siddhartha")
            return record.nack(Exception("Invalid Buddha!"))

        log.info("Got this $payload")
        return record.ack()
    }
}