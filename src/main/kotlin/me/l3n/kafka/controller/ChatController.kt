package me.l3n.kafka.controller

import me.l3n.kafka.model.ChatMessage
import me.l3n.kafka.service.ChatService
import javax.ws.rs.POST
import javax.ws.rs.Path
import javax.ws.rs.core.Response

@Path("/messages")
class ChatController(
    private val service: ChatService
) {

    @POST
    fun new(message: ChatMessage): Response {
        service.create(message)

        return Response.ok().build()
    }
}