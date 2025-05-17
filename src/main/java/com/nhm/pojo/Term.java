/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhm.pojo;

import com.fasterxml.jackson.annotation.JsonView;
import com.nhm.viewconfigs.CollectionView;
import com.nhm.viewconfigs.DisplayView;
import jakarta.persistence.Basic;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import java.util.Collection;

/**
 *
 * @author GIGABYTE
 */
@Entity
@Table(name = "term")
@NamedQueries({
    @NamedQuery(name = "Term.findAll", query = "SELECT t FROM Term t"),
    @NamedQuery(name = "Term.findById", query = "SELECT t FROM Term t WHERE t.id = :id"),
    @NamedQuery(name = "Term.findByName", query = "SELECT t FROM Term t WHERE t.name = :name"),
    @NamedQuery(name = "Term.findByMaxValue", query = "SELECT t FROM Term t WHERE t.maxValue = :maxValue")})
public class Term implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    @JsonView(DisplayView.Public.class)
    private Long id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "name")
    @JsonView(DisplayView.Public.class)
    private String name;
    @Basic(optional = false)
    @NotNull
    @Column(name = "max_value")
    @JsonView(DisplayView.Public.class)
    private int maxValue;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "termId")
    @JsonView(CollectionView.TermCollection.class)
    private Collection<ExtraActivity> extraActivityCollection;

    public Term() {
    }

    public Term(Long id) {
        this.id = id;
    }

    public Term(Long id, String name, int maxValue) {
        this.id = id;
        this.name = name;
        this.maxValue = maxValue;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(int maxValue) {
        this.maxValue = maxValue;
    }

    public Collection<ExtraActivity> getExtraActivityCollection() {
        return extraActivityCollection;
    }

    public void setExtraActivityCollection(Collection<ExtraActivity> extraActivityCollection) {
        this.extraActivityCollection = extraActivityCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Term)) {
            return false;
        }
        Term other = (Term) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.nhm.pojo.Term[ id=" + id + " ]";
    }
    
}