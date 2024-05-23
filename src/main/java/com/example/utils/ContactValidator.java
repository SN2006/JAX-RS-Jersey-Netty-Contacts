package com.example.utils;

import com.example.domain.Contact;
import com.example.exceptions.ContactDataException;

public class ContactValidator {

    public Contact validateContact(Contact contact) {
        StringBuilder builder = new StringBuilder();
        if (contact.getName() == null || contact.getName().isEmpty()){
            builder.append("name: name cannot be empty\n");
        }
        if (contact.getPhone() == null
                || AppValidator.isPhoneValid(contact.getPhone())){
            builder.append("phone: phone must be in format xxx xxx-xxxx\n");
        }
        if (builder.toString().isEmpty()){
            return contact;
        }
        throw new ContactDataException(builder.toString());
    }

}
