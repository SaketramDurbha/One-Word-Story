package me.onewordstory.websockets;

import java.util.ArrayList;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;


public class WebSocketImpl {


	private static final ArrayList<WebSocketImpl> connections = new ArrayList<WebSocketImpl>();
	//private static final Set<WebSocket> connections = new CopyOnWriteArraySet<>();

	private Session session;

	public WebSocketImpl() {
	}

	@OnOpen
	public void start(Session session) {
		this.session = session;

		connections.add(this);

	}


	@OnClose
	public void end() {
		connections.remove(this);
	}


	@OnMessage
	public void incoming(String message) {
		System.out.println("WebSocket.incoming: " + message);

		// Never trust the client
		//String filteredMessage = com.kollabz.sitecore.utils.HTMLFilter.filter(message.toString());
	}

	@OnError
	public void onError(Throwable t) throws Throwable {
		t.printStackTrace();
	}


	public static void broadcast(String messageKey, String msg) {
		String messageToSend = String.format("{\"messageKey\": \"%s\", \"message\":\"%s\"}", messageKey, msg);
		for (WebSocketImpl client: connections) {
			client.session.getAsyncRemote().sendText(messageToSend);
			/*
                try {
                    synchronized (client) {
                        client.session.getBasicRemote().sendText(msg);
                    }
                } catch (IOException e) {
                    logger.debug("Chat Error: Failed to send message to client", e);
                    connections.remove(client);
                    try {
                        client.session.close();
                    } catch (IOException e1) {
                        // Ignore
                    }
                }
			 */
		}
	}
}