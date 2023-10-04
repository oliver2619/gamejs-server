package pluto

import io.vertx.core.impl.ConcurrentHashSet
import jakarta.enterprise.context.ApplicationScoped
import jakarta.websocket.*
import jakarta.websocket.server.PathParam
import jakarta.websocket.server.ServerEndpoint

@ApplicationScoped
@ServerEndpoint("/game/{name}", subprotocols = ["gamejs"])
class StartWebSocket {

    private val sessions = ConcurrentHashSet<Session>()

    @OnOpen
    fun onOpen(session: Session, @PathParam("name") name: String) {
        sessions.add(session)
        println("onOpen $name ${session.negotiatedSubprotocol}")
    }

    @OnClose
    fun onClose(session: Session, @PathParam("name") name: String) {
        sessions.remove(session)
        println("onClose $name")
    }

    @OnError
    fun onError(session: Session, @PathParam("name") name: String, throwable: Throwable) {
        println("onError $name: $throwable")
    }

    @OnMessage
    fun onMessage(message: String, @PathParam("name") name: String) {
        println("onMessage $name: $message")
        sessions.forEach {
            it.asyncRemote.sendText("response") {
                if (!it.isOK) {
                    it.exception.printStackTrace()
                }
            }
        }
    }
}
