package com.rubinochat.encoders;
import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rubinochat.bean.Message;

public class MessageEncoder implements Encoder.Text<Message>{

	@Override
	public void init(EndpointConfig config) {
		
	}

	@Override
	public void destroy() {
		
	}

	@Override
	public String encode(Message object) throws EncodeException {
		String value = "";
		ObjectMapper mapper = new ObjectMapper();  
	    try {
			value = mapper.writeValueAsString(object);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
	    return value;
	}

}
