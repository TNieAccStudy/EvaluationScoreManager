/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.nhm.services;

import com.nhm.pojo.MissingActivity;
import java.util.Collection;
import java.io.IOException;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author GIGABYTE
 */
public interface MissingActivityService {
    MissingActivity addOrUpdate(MissingActivity missing, MultipartFile proofPicture) throws IOException, Exception;
    MissingActivity getMissingById(int id);
    Collection<MissingActivity> getMissingActivities();
}
