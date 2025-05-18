/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.nhm.dto;

/**
 *
 * @author GIGABYTE
 */
public interface ConvertData<T, TService> {
    T convertData(TService service);
}
