//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.1-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2023.07.10 at 01:34:46 сл.об. EEST 
//


package com.game_classes.soap_models;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlList;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="statusValues" type="{http://www.game_classes.com/soap-models}StatusValuesList"/>
 *         &lt;element name="userRankDataPaged" type="{http://www.game_classes.com/soap-models}UserRankDataList"/>
 *         &lt;element name="winCount" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="lossCount" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "statusValues",
    "userRankDataPaged",
    "winCount",
    "lossCount"
})
@XmlRootElement(name = "UserProfileSoap")
public class UserProfileSoap {

    @XmlList
    @XmlElement(type = Integer.class)
    protected List<Integer> statusValues;
    @XmlElement(required = true)
    protected UserRankDataList userRankDataPaged;
    protected int winCount;
    protected int lossCount;

    /**
     * Gets the value of the statusValues property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the statusValues property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getStatusValues().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Integer }
     * 
     * 
     */
    public List<Integer> getStatusValues() {
        if (statusValues == null) {
            statusValues = new ArrayList<Integer>();
        }
        return this.statusValues;
    }

    /**
     * Gets the value of the userRankDataPaged property.
     * 
     * @return
     *     possible object is
     *     {@link UserRankDataList }
     *     
     */
    public UserRankDataList getUserRankDataPaged() {
        return userRankDataPaged;
    }

    /**
     * Sets the value of the userRankDataPaged property.
     * 
     * @param value
     *     allowed object is
     *     {@link UserRankDataList }
     *     
     */
    public void setUserRankDataPaged(UserRankDataList value) {
        this.userRankDataPaged = value;
    }

    /**
     * Gets the value of the winCount property.
     * 
     */
    public int getWinCount() {
        return winCount;
    }

    /**
     * Sets the value of the winCount property.
     * 
     */
    public void setWinCount(int value) {
        this.winCount = value;
    }

    /**
     * Gets the value of the lossCount property.
     * 
     */
    public int getLossCount() {
        return lossCount;
    }

    /**
     * Sets the value of the lossCount property.
     * 
     */
    public void setLossCount(int value) {
        this.lossCount = value;
    }

}
