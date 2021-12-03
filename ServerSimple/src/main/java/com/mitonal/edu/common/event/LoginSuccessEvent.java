package com.mitonal.edu.common.event;

import com.mitonal.edu.common.entities.DefaultUserDetails;

import org.springframework.context.PayloadApplicationEvent;
import org.springframework.core.ResolvableType;

public class LoginSuccessEvent extends PayloadApplicationEvent<DefaultUserDetails> {

	public LoginSuccessEvent(Object source, DefaultUserDetails payload) {
		super(source, payload);
	}

	@Override
	public ResolvableType getResolvableType() {
		return ResolvableType.forRawClass(LoginSuccessEvent.class);
	}

	@Override
	public DefaultUserDetails getPayload() {
		return super.getPayload();
	}

}
