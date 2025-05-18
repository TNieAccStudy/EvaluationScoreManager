/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhm.dto;

import com.nhm.pojo.ExtraActivity;
import com.nhm.services.ExtraActivityService;

/**
 *
 * @author GIGABYTE
 */
public class ActivityDTO implements ConvertData<ExtraActivity, ExtraActivityService> {
    private int bonusScore;
    private String title;
    private String description;
    private int semesterId;
    private int termId;
    private int studentAssistantId;

    @Override
    public ExtraActivity convertData(ExtraActivityService service) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}
