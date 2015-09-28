package com.johnzank.addressbook;

import java.io.Serializable;


/**
 * Created by johnzank on 9/23/15.
 */

@SuppressWarnings("serial")
public class Contact implements Serializable {

    public String contactName;
    public String contactPhone;
    public String contactEmail;
    public String contactStreet;
    public String contactCityStZip;

    public Contact(String contactName, String contactPhone, String contactEmail, String contactStreet, String contactCityStZip) {
        this.contactName = contactName;
        this.contactPhone = contactPhone;
        this.contactEmail = contactEmail;
        this.contactStreet = contactStreet;
        this.contactCityStZip = contactCityStZip;
    }
}
