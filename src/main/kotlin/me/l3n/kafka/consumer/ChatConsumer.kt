package me.l3n.kafka.consumer

import me.l3n.kafka.ChatMessage
import org.eclipse.microprofile.reactive.messaging.Incoming
import org.eclipse.microprofile.reactive.messaging.Message
import org.jboss.logging.Logger
import java.util.concurrent.CompletionStage
import javax.enterprise.context.ApplicationScoped
import javax.inject.Inject

@ApplicationScoped
class ChatConsumer {

    @Inject
    lateinit var log: Logger

    @Incoming("messages-in")
    fun consume(message: Message<ChatMessage>): CompletionStage<Void> {
        val payload = message.payload

        if (payload.author == "Siddhartha") message.nack(Exception("Invalid Buddha!"))

        log.info("Got this ${message.payload}")
        return message.ack()
    }
}