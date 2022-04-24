package me.l3n.kafka.service

import io.smallrye.mutiny.Multi
import me.l3n.kafka.ChatMessage
import org.eclipse.microprofile.reactive.messaging.Channel
import org.eclipse.microprofile.reactive.messaging.Emitter
import org.eclipse.microprofile.reactive.messaging.Message
import org.eclipse.microprofile.reactive.messaging.Outgoing
import java.time.Duration
import javax.enterprise.context.ApplicationScoped

@ApplicationScoped
class ChatService {

    @Channel("messages-via-rest")
    private lateinit var messages: Emitter<ChatMessage>

    fun create(message: ChatMessage) {
        messages.send(message)
    }

    @Outgoing("messages-via-gen")
    fun generate(): Multi<Message<ChatMessage>> {
        return Multi.createFrom()
            .ticks()
            .every(Duration.ofSeconds(3))
            .map { Message.of(ChatMessage("Joe", "#$it message")) }
    }
}