package com.game_classes.soap_models;

import org.springframework.ws.soap.server.endpoint.annotation.FaultCode;
import org.springframework.ws.soap.server.endpoint.annotation.SoapFault;

@SoapFault(faultCode = FaultCode.CUSTOM, customFaultCode = "{" + SoapException.NAMESPACE
		+ "}User with this username doesn't exist!", faultStringOrReason = "@faultString")
public class SoapException extends Exception {
	/**
	 * 
	 */
	public static final String NAMESPACE = "http://www.game_classes.com/soap-models";
	private static final long serialVersionUID = 1L;

	public SoapException(String message) {
		super(message);
	}

}
