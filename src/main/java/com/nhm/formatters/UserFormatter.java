/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhm.formatters;

import com.nhm.pojo.UserInfo;
import java.text.ParseException;
import java.util.Locale;
import org.springframework.format.Formatter;

/**
 *
 * @author GIGABYTE
 */
public class UserFormatter implements Formatter<UserInfo> {
    @Override
    public String print(UserInfo object, Locale locale) {
        return String.valueOf(object.getId());
    }

    @Override
    public UserInfo parse(String text, Locale locale) throws ParseException {
        UserInfo u = new UserInfo();
        u.setId(Long.valueOf(text));
        
        return u;
    }
}
