package com.rubinochat.websocket;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;

import javax.websocket.EncodeException;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.rubinochat.bean.Message;
import com.rubinochat.decoders.MessageDecoder;
import com.rubinochat.encoders.MessageEncoder;
import com.rubinochat.utility.Constants;
import com.rubinochat.utility.Utility;

@ServerEndpoint(value = "/chat", encoders = MessageEncoder.class, decoders = MessageDecoder.class)
public class ChatRoom {

	private static List<String> userConnected = new LinkedList<String>();
	
	private static final Logger logger = LogManager.getLogger(ChatRoom.class.getName());
	
	private static LinkedHashSet<String> typingUsersList = new LinkedHashSet<String>();
	
	@OnOpen
	public void onOpen(Session session) {
		logger.info("ChatRoom - Connection open.");
	}

	@OnMessage
	public void onMessage(Message message, Session session) {
		String dateTime = Utility.getDateString();
		String username = message.getUsername();
		logger.info("Message recived : {}" + message.toString());
		if (message.getType().equals("join") && userConnected.contains(username)) {
			logger.error("User already in use : {}", username);
			try {
				session.getBasicRemote().sendObject(new Message("error", "Utente già utilizzato", username, dateTime));
				session.close();
			} catch (IOException | EncodeException e) {
				e.printStackTrace();
			}
		} else {
			logger.info("Added {} to user connected." , username);
			if(message.getType().equals(Constants.JOIN)) {
				userConnected.add(username);
			}
			if(message.getType().equals("typing")) {
				typingUsersList.add(username);
			} else if(message.getType().equals("reset-typing")) {
				typingUsersList.remove(username);
			}
			for (Session sessions : session.getOpenSessions()) {
				try {
					if (message.getType().equals(Constants.JOIN)) {
						//logger.info("Join request from user {}" , username);
						Message msg = new Message(Constants.JOIN, Constants.EMPTY, message.getUsername(), dateTime);
						session.getUserProperties().put("username", username);
						sessions.getBasicRemote().sendObject(msg);
						sessions.getBasicRemote().sendObject(new Message("online", userConnected.toString(), "", ""));
						Message msgTyping = new Message("typing", typingUsersList.toString(), "", "");
						sessions.getBasicRemote().sendObject(msgTyping);
					} else if (message.getType().equals(Constants.CHAT)) {
						typingUsersList.add(username);
						//logger.info("Chat request from user {}" , username);
						Message msg = new Message(Constants.CHAT, message.getMessage(), username, dateTime);
						sessions.getBasicRemote().sendObject(msg);
					} else if(message.getType().equals("typing") || message.getType().equals("reset-typing")) {
						//logger.debug(message.getUsername());
						for(String s : typingUsersList) {
							//logger.debug(s);
						}
						Message msg = new Message("typing", typingUsersList.toString(), "", "");
						sessions.getBasicRemote().sendObject(msg);
					}
				} catch (IOException e) {
					e.printStackTrace();
				} catch (EncodeException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	@OnMessage
	public void uploadFile(Session session, ByteBuffer msg) {
		System.out.println("Binary message: " + msg.toString());
	}

	@OnClose
	public void close(Session session) {
		String username = session.getUserProperties().get("username").toString();
		logger.info("session closing for user : {}" , username);
		if (session.getUserProperties().containsKey("username")) {
			typingUsersList.remove(username);
			for (Session sessions : session.getOpenSessions()) {
				userConnected.remove(username);
				if(sessions.isOpen() && !session.equals(sessions)) {
					try {
						sessions.getBasicRemote().sendObject(new Message(Constants.LEFT, Constants.EMPTY, username, Utility.getDateString()));
						sessions.getBasicRemote().sendObject(new Message("online", userConnected.toString(), "", ""));
						sessions.getBasicRemote().sendObject(new Message("typing", typingUsersList.toString(), "", ""));
					} catch (IOException e) {
						e.printStackTrace();
					} catch (EncodeException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

	@OnError
	public void error(Session session, Throwable t) {

	}
	

}
