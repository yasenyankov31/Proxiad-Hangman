package com.game_classes.models;

public class SuccessResponse {
	public String message;

	public String action;

	public SuccessResponse(String action, String message) {
		this.message = message;
		this.action = action;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
