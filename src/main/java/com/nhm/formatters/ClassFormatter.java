/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhm.formatters;

import com.nhm.pojo.Classe;
import java.text.ParseException;
import java.util.Locale;
import org.springframework.format.Formatter;

/**
 *
 * @author GIGABYTE
 */
public class ClassFormatter implements Formatter<Classe> {
    @Override
    public String print(Classe object, Locale locale) {
        return String.valueOf(object.getId());
    }

    @Override
    public Classe parse(String text, Locale locale) throws ParseException {
        Classe u = new Classe();
        u.setId(Long.valueOf(text));
        
        return u;
    }
}
