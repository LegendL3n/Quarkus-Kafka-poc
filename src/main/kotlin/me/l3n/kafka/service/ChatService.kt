package me.l3n.kafka.service

import io.smallrye.mutiny.Multi
import io.smallrye.reactive.messaging.kafka.KafkaRecord
import io.smallrye.reactive.messaging.kafka.OutgoingKafkaRecord
import me.l3n.kafka.MessageKey
import me.l3n.kafka.MessageValue
import me.l3n.kafka.model.ChatMessage
import org.eclipse.microprofile.reactive.messaging.Channel
import org.eclipse.microprofile.reactive.messaging.Emitter
import org.eclipse.microprofile.reactive.messaging.Outgoing
import java.time.Duration
import javax.enterprise.context.ApplicationScoped

@ApplicationScoped
class ChatService {

    @Channel("messages-via-rest")
    private lateinit var messages: Emitter<MessageValue>

    fun create(message: ChatMessage) {
        messages.send(KafkaRecord.of(MessageKey(message.author), MessageValue(message.author, message.content)))
    }

    @Outgoing("messages-via-gen")
    fun generate(): Multi<OutgoingKafkaRecord<MessageKey, MessageValue>> {
        return Multi.createFrom()
            .ticks()
            .every(Duration.ofSeconds(3))
            .map { KafkaRecord.of(MessageKey("Joe"), MessageValue("Joe", "#$it message")) }
    }
}