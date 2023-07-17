package com.yasen.soapContractLast.soap_models;

import javax.xml.namespace.QName;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPFactory;
import javax.xml.soap.SOAPFault;

public class SOAPFaultHelper {
  public static SOAPFault createSOAPFault(String faultCode, String faultString)
      throws SOAPException {
    SOAPFactory soapFactory = SOAPFactory.newInstance();
    SOAPFault soapFault = soapFactory.createFault();
    soapFault.setFaultCode(new QName(faultCode));
    soapFault.setFaultString(faultString);
    return soapFault;
  }
}
