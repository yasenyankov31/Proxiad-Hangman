//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.1-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2023.07.07 at 06:43:39 PM EEST 
//

package com.game_classes.soap_models;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlList;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Java class for RankingDataSoap complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 * 
 * <pre>
 * &lt;complexType name="RankingDataSoap">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="rankType" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="userNames" type="{http://www.game_classes.com/soap-models}UserNameList"/>
 *         &lt;element name="winCounts" type="{http://www.game_classes.com/soap-models}WinCountList"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RankingDataSoap", propOrder = { "rankType", "userNames", "winCounts" })
public class RankingDataSoap {

	@XmlElement(required = true)
	protected String rankType;
	@XmlList
	@XmlElement(required = true)
	protected List<String> userNames;
	@XmlList
	@XmlElement(type = Integer.class)
	protected List<Integer> winCounts;

	/**
	 * Gets the value of the rankType property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getRankType() {
		return rankType;
	}

	/**
	 * Sets the value of the rankType property.
	 * 
	 * @param value allowed object is {@link String }
	 * 
	 */
	public void setRankType(String value) {
		this.rankType = value;
	}

	/**
	 * Gets the value of the userNames property.
	 * 
	 * <p>
	 * This accessor method returns a reference to the live list, not a snapshot.
	 * Therefore any modification you make to the returned list will be present
	 * inside the JAXB object. This is why there is not a <CODE>set</CODE> method
	 * for the userNames property.
	 * 
	 * <p>
	 * For example, to add a new item, do as follows:
	 * 
	 * <pre>
	 * getUserNames().add(newItem);
	 * </pre>
	 * 
	 * 
	 * <p>
	 * Objects of the following type(s) are allowed in the list {@link String }
	 * 
	 * 
	 */
	public List<String> getUserNames() {
		if (userNames == null) {
			userNames = new ArrayList<String>();
		}
		return this.userNames;
	}

	/**
	 * Gets the value of the winCounts property.
	 * 
	 * <p>
	 * This accessor method returns a reference to the live list, not a snapshot.
	 * Therefore any modification you make to the returned list will be present
	 * inside the JAXB object. This is why there is not a <CODE>set</CODE> method
	 * for the winCounts property.
	 * 
	 * <p>
	 * For example, to add a new item, do as follows:
	 * 
	 * <pre>
	 * getWinCounts().add(newItem);
	 * </pre>
	 * 
	 * 
	 * <p>
	 * Objects of the following type(s) are allowed in the list {@link Integer }
	 * 
	 * 
	 */
	public List<Integer> getWinCounts() {
		if (winCounts == null) {
			winCounts = new ArrayList<Integer>();
		}
		return this.winCounts;
	}

	public void setUserNames(List<String> userNames) {
		this.userNames = userNames;
	}

	public void setWinCounts(List<Integer> winCounts) {
		this.winCounts = winCounts;
	}

}