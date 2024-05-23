package com.example.utils;

public class AppValidator {

    public final static String PHONE_RGX = "[0-9]{3} [0-9]{3}-[0-9]{4}";

    public static boolean isPhoneValid(String phone) {
        if (phone != null)
            return phone.isEmpty() || !phone.matches(PHONE_RGX);
        return false;
    }

}
