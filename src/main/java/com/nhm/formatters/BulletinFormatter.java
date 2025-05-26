/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhm.formatters;

import com.nhm.pojo.Bulletin;
import java.text.ParseException;
import java.util.Locale;
import org.springframework.format.Formatter;

/**
 *
 * @author GIGABYTE
 */
public class BulletinFormatter implements Formatter<Bulletin> {
    @Override
    public String print(Bulletin object, Locale locale) {
        return String.valueOf(object.getId());
    }

    @Override
    public Bulletin parse(String text, Locale locale) throws ParseException {
        Bulletin u = new Bulletin();
        u.setId(Long.valueOf(text));
        
        return u;
    }
}
