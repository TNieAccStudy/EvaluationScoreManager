/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhm.formatters;

import com.nhm.pojo.Term;
import java.text.ParseException;
import java.util.Locale;
import org.springframework.format.Formatter;

/**
 *
 * @author GIGABYTE
 */
public class TermFormatter implements Formatter<Term> {
    @Override
    public String print(Term object, Locale locale) {
        return String.valueOf(object.getId());
    }

    @Override
    public Term parse(String text, Locale locale) throws ParseException {
        Term t = new Term();
        t.setId(Long.valueOf(text));
        
        return t;
    }
}
