/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhm.formatters;

import com.nhm.pojo.StudentAffairsOfficer;
import java.text.ParseException;
import java.util.Locale;
import org.springframework.format.Formatter;

/**
 *
 * @author GIGABYTE
 */
public class StudentAffairsOfficerFormatter implements Formatter<StudentAffairsOfficer> {

    @Override
    public String print(StudentAffairsOfficer object, Locale locale) {
        return String.valueOf(object.getId());
    }

    @Override
    public StudentAffairsOfficer parse(String text, Locale locale) throws ParseException {
        if (text == null || text.trim().isEmpty()) {
            return null;
        }
        StudentAffairsOfficer sa = new StudentAffairsOfficer();
        sa.setId(Long.valueOf(text));
        return sa;
    }
}
