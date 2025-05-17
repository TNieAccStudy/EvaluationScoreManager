/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhm.services.impl;

import com.nhm.pojo.Term;
import com.nhm.repositories.TermRepository;
import com.nhm.services.TermService;
import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author GIGABYTE
 */
@Service
public class TermServiceImpl implements TermService {
    
    @Autowired
    TermRepository termRepository;

    @Override
    public Term addOrUpdate(Term term) {
        return termRepository.addOrUpdate(term);
    }

    @Override
    public Collection<Term> getTerms() {
        return termRepository.getTerms();
    }
    
}
