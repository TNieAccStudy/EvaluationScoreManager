/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhm.formatters;

import com.nhm.pojo.StudentAssistant;
import java.text.ParseException;
import java.util.Locale;
import org.springframework.format.Formatter;

/**
 *
 * @author GIGABYTE
 */
public class AsisstantFormatter implements Formatter<StudentAssistant> {
    @Override
    public String print(StudentAssistant object, Locale locale) {
        return String.valueOf(object.getId());
    }

    @Override
    public StudentAssistant parse(String text, Locale locale) throws ParseException {
        StudentAssistant u = new StudentAssistant();
        u.setId(Long.valueOf(text));
        
        return u;
    }
    
}
