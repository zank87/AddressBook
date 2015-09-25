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

}
