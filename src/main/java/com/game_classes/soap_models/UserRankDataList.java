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
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for UserRankDataList complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="UserRankDataList">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="UserRankDataList" type="{http://www.game_classes.com/soap-models}UserRankDataSoap" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "UserRankDataList", propOrder = {
    "userRankDataList"
})
public class UserRankDataList {

    @XmlElement(name = "UserRankDataList")
    protected List<UserRankDataSoap> userRankDataList;

    /**
     * Gets the value of the userRankDataList property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the userRankDataList property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getUserRankDataList().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link UserRankDataSoap }
     * 
     * 
     */
    public List<UserRankDataSoap> getUserRankDataList() {
        if (userRankDataList == null) {
            userRankDataList = new ArrayList<UserRankDataSoap>();
        }
        return this.userRankDataList;
    }

}
