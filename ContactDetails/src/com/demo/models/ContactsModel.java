package com.demo.models;

import java.util.Locale;

/**
 * 
 * @author vinayak bevinakatti
 *
 */
public class ContactsModel implements Comparable<ContactsModel>
{    
    private String firstName;
    
    private String surName;
    
    private String phoneNumber;
    
    private String emailAddr;

    public ContactsModel() {
        // TODO Auto-generated constructor stub
    }
    
    public ContactsModel(String firstName, String surName, String phoneNumber, String emailAddr) {
        super();
        this.firstName = firstName;
        this.surName = surName;
        this.phoneNumber = phoneNumber;
        this.emailAddr = emailAddr;
    }

    public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getSurName() {
		return surName;
	}

	public void setSurName(String surName) {
		this.surName = surName;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getEmailAddr() {
		return emailAddr;
	}

	public void setEmailAddr(String emailAddr) {
		this.emailAddr = emailAddr;
	}

	@Override
    public int compareTo(ContactsModel contact) {
        return this.getFirstName().toLowerCase(Locale.ENGLISH).compareTo(contact.getFirstName().toLowerCase(Locale.ENGLISH));
    }
}
