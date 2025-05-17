/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.nhm.services;

import com.nhm.pojo.Term;
import java.util.Collection;

/**
 *
 * @author GIGABYTE
 */
public interface TermService {
    Term addOrUpdate(Term term);
    Collection<Term> getTerms();
}
