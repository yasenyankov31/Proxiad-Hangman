package com.yasen.soapContractLast.soap_models;

import javax.xml.soap.SOAPFault;
import javax.xml.ws.soap.SOAPFaultException;

class CustomSoapFaultExceptionion extends SOAPFaultException {

  /** */
  private static final long serialVersionUID = 1L;

  public CustomSoapFaultExceptionion(SOAPFault fault) {
    super(fault);
  }
}
