/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhm.dto;

import com.nhm.pojo.ExtraActivity;
import java.util.List;

/**
 *
 * @author GIGABYTE
 */
public class CSVAttendancesData {
    private List<String> attendedStudents;
    private ExtraActivity extraActivityId;
    private String proofPictureGeneral;
    
    public CSVAttendancesData() {
        
    }

    public CSVAttendancesData(List<String> attendedStudents, ExtraActivity extraActivityId, String proofPictureGeneral) {
        this.attendedStudents = attendedStudents;
        this.extraActivityId = extraActivityId;
        this.proofPictureGeneral = proofPictureGeneral;
    }
    
    

    public List<String> getAttendedStudents() {
        return attendedStudents;
    }

    public void setAttendedStudents(List<String> attendedStudents) {
        this.attendedStudents = attendedStudents;
    }

    public ExtraActivity getExtraActivityId() {
        return extraActivityId;
    }

    public void setExtraActivityId(ExtraActivity extraActivityId) {
        this.extraActivityId = extraActivityId;
    }

    public String getProofPictureGeneral() {
        return proofPictureGeneral;
    }

    public void setProofPictureGeneral(String proofPictureGeneral) {
        this.proofPictureGeneral = proofPictureGeneral;
    }
    
    
}
