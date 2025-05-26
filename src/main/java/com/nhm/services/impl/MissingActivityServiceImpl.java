/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhm.services.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.nhm.pojo.ActivityConfirmedAttendance;
import com.nhm.pojo.ExecuteStatus;
import com.nhm.pojo.MissingActivity;
import com.nhm.repositories.MissingActivityRepository;
import com.nhm.services.ActivityConfirmedAttendanceService;
import com.nhm.services.MissingActivityService;
import java.io.IOException;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author GIGABYTE
 */
@Service
public class MissingActivityServiceImpl implements MissingActivityService {
    
    @Autowired
    private MissingActivityRepository missingRepo;
    
    @Autowired
    private ActivityConfirmedAttendanceService attendanceService;
    
    @Autowired
    private Cloudinary cloudinary;

    @Override
    public MissingActivity addOrUpdate(MissingActivity missing, MultipartFile proofPicture) throws IOException, Exception {
        String publicId = null;
        if (proofPicture != null && !proofPicture.isEmpty()) {
            Map res = this.cloudinary.uploader().upload(proofPicture.getBytes(), ObjectUtils.asMap("resource_type", "auto"));
            missing.setProofPicture(res.get("secure_url").toString());
            publicId = res.get("public_id").toString();
        }

        try {
            missing = this.missingRepo.addOrUpdate(missing);
        } catch (Exception e) {
            if (publicId != null) {
                this.cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap());
            }
            throw new Exception("have some error when work with data");
        }
        
        return missing;
    }

    @Override
    public MissingActivity getMissingById(int id) {
        return this.missingRepo.getMissingById(id);
    }
    
}
