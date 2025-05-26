/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhm.utils;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author GIGABYTE
 */
public class CloudinaryUtils {
    public static String extractPublicId(String url) {
    try {
        // Tách phần path sau domain
        String path = new URL(url).getPath(); // /demo/image/upload/v1653500000/user_folder/my_avatar_123.jpg

        // Tìm vị trí sau version (v1653...)
        int versionIndex = path.indexOf("/v");
        if (versionIndex == -1) return null;

        // Lấy phần còn lại sau version
        String afterVersion = path.substring(versionIndex);
        String[] parts = afterVersion.split("/");

        // Từ v1653500000 trở đi, public_id bắt đầu sau phần version
        List<String> publicIdParts = new ArrayList<>(Arrays.asList(parts));
        publicIdParts.remove(0); // remove vxxxxx

        // Tách file name & bỏ phần mở rộng
        String lastPart = publicIdParts.get(publicIdParts.size() - 1);
        int dotIndex = lastPart.lastIndexOf('.');
        if (dotIndex != -1) {
            lastPart = lastPart.substring(0, dotIndex);
            publicIdParts.set(publicIdParts.size() - 1, lastPart);
        }

        return String.join("/", publicIdParts);
    } catch (MalformedURLException e) {
        return null;
    }
}
}
