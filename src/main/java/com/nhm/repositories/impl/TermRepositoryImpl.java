/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhm.repositories.impl;

import com.nhm.pojo.ExtraActivity;
import com.nhm.pojo.Term;
import com.nhm.repositories.TermRepository;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author GIGABYTE
 */
@Repository
@Transactional
public class TermRepositoryImpl extends BaseRepositoryImpl implements TermRepository {

    @Override
    public Term addOrUpdate(Term term) {
        try {
            return super.addOrUpdate(term, Term.class);
        } catch (NoSuchMethodException ex) {
            Logger.getLogger(TermRepositoryImpl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(TermRepositoryImpl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvocationTargetException ex) {
            Logger.getLogger(TermRepositoryImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public Collection<Term> getTerms() {
        return super.getItems(Term.class);
    }

    @Override
    public Collection<ExtraActivity> getActivitesByTermId(int termId) {
        return super.getItems(ExtraActivity.class, (cb, data) -> {
            return cb.equal(data.get("termId").get("id"), Long.valueOf(termId));
        });
    }

    @Override
    public Term getTermById(int id) {
        return getItemById(id, Term.class);
    }
    
}
