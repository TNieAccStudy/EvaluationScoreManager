/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.nhm.repositories;

import com.nhm.pojo.Interaction;

/**
 *
 * @author GIGABYTE
 */
public interface InteractionRepository {
    Interaction addOrUpdate(Interaction interaction);
    void deleteInteractionById(int id);
    Interaction getInteractionById(int id);
}
