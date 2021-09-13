package com.rubinochat.decoders;
import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rubinochat.bean.Message;

public class MessageDecoder implements Decoder.Text<Message>{

	@Override
	public void init(EndpointConfig config) {
		
	}

	@Override
	public void destroy() {
		
	}

	@Override
	public Message decode(String s) throws DecodeException {
		ObjectMapper objectMapper = new ObjectMapper();
		Message p = null;
		try {
			p = objectMapper.readValue(s, Message.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return p;
	}

	@Override
	public boolean willDecode(String s) {
		return true;
	}

}
