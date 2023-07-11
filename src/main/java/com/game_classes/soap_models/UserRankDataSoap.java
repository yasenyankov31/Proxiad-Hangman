//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference
// Implementation, vhudson-jaxb-ri-2.1-2
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a>
// Any modifications to this file will be lost upon recompilation of the source schema.
// Generated on: 2023.07.10 at 01:34:46 сл.об. EEST
//

package com.game_classes.soap_models;

import java.util.GregorianCalendar;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import com.game_classes.interfaces.modelInterfaces.UserRankData;

/**
 * Java class for UserRankDataSoap complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="UserRankDataSoap">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="gameStatus" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="word" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="lettersUsed" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="attemptsLeft" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="startDate" type="{http://www.w3.org/2001/XMLSchema}date"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
    name = "UserRankDataSoap",
    propOrder = {"gameStatus", "word", "lettersUsed", "attemptsLeft", "startDate"})
public class UserRankDataSoap {

  @XmlElement(required = true)
  protected String gameStatus;

  @XmlElement(required = true)
  protected String word;

  @XmlElement(required = true)
  protected String lettersUsed;

  protected int attemptsLeft;

  @XmlElement(required = true)
  @XmlSchemaType(name = "date")
  protected XMLGregorianCalendar startDate;

  /**
   * Gets the value of the gameStatus property.
   *
   * @return possible object is {@link String }
   */
  public String getGameStatus() {
    return gameStatus;
  }

  public UserRankDataSoap() {}

  public UserRankDataSoap(UserRankData rankData) throws DatatypeConfigurationException {
    this.gameStatus = rankData.getGameStatus();
    this.word = rankData.getWord();
    this.lettersUsed = rankData.getLettersUsed();
    this.attemptsLeft = rankData.getAttemptsLeft();
    GregorianCalendar calendar = new GregorianCalendar();
    calendar.setTime(rankData.getStartDate());
    this.startDate = DatatypeFactory.newInstance().newXMLGregorianCalendar(calendar);
  }
  /**
   * Sets the value of the gameStatus property.
   *
   * @param value allowed object is {@link String }
   */
  public void setGameStatus(String value) {
    this.gameStatus = value;
  }

  /**
   * Gets the value of the word property.
   *
   * @return possible object is {@link String }
   */
  public String getWord() {
    return word;
  }

  /**
   * Sets the value of the word property.
   *
   * @param value allowed object is {@link String }
   */
  public void setWord(String value) {
    this.word = value;
  }

  /**
   * Gets the value of the lettersUsed property.
   *
   * @return possible object is {@link String }
   */
  public String getLettersUsed() {
    return lettersUsed;
  }

  /**
   * Sets the value of the lettersUsed property.
   *
   * @param value allowed object is {@link String }
   */
  public void setLettersUsed(String value) {
    this.lettersUsed = value;
  }

  /** Gets the value of the attemptsLeft property. */
  public int getAttemptsLeft() {
    return attemptsLeft;
  }

  /** Sets the value of the attemptsLeft property. */
  public void setAttemptsLeft(int value) {
    this.attemptsLeft = value;
  }

  /**
   * Gets the value of the startDate property.
   *
   * @return possible object is {@link XMLGregorianCalendar }
   */
  public XMLGregorianCalendar getStartDate() {
    return startDate;
  }

  /**
   * Sets the value of the startDate property.
   *
   * @param value allowed object is {@link XMLGregorianCalendar }
   */
  public void setStartDate(XMLGregorianCalendar value) {
    this.startDate = value;
  }
}