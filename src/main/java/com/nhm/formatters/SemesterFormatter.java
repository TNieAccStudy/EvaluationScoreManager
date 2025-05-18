/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhm.formatters;

import com.nhm.pojo.Semester;
import java.text.ParseException;
import java.util.Locale;
import org.springframework.format.Formatter;

/**
 *
 * @author GIGABYTE
 */
public class SemesterFormatter implements Formatter<Semester> {

    @Override
    public String print(Semester object, Locale locale) {
        return String.valueOf(object.getId());
    }

    @Override
    public Semester parse(String text, Locale locale) throws ParseException {
        Semester s = new Semester();
        s.setId(Long.valueOf(text));
        
        return s;
    }
    
}
