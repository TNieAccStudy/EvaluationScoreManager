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
@Table(name = "semester")
@NamedQueries({
    @NamedQuery(name = "Semester.findAll", query = "SELECT s FROM Semester s"),
    @NamedQuery(name = "Semester.findById", query = "SELECT s FROM Semester s WHERE s.id = :id"),
    @NamedQuery(name = "Semester.findByName", query = "SELECT s FROM Semester s WHERE s.name = :name"),
    @NamedQuery(name = "Semester.findByYear", query = "SELECT s FROM Semester s WHERE s.year = :year")})
public class Semester implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    @JsonView({
        DisplayView.Public.class,
        DisplayView.Simplify.class
    })
    private Long id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "name")
    @JsonView({
        DisplayView.Public.class,
        DisplayView.Simplify.class
    })
    private String name;
    @Basic(optional = false)
    @NotNull
    @Column(name = "year")
    @JsonView({
        DisplayView.Public.class,
        DisplayView.Simplify.class
    })
    private int year;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "semesterId")
    @JsonView(CollectionView.SemesterColelction.class)
    private Collection<ExtraActivity> extraActivityCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "semesterId")
    @JsonView(CollectionView.SemesterColelction.class)
    private Collection<Bulletin> bulletinCollection;

    public Semester() {
    }

    public Semester(Long id) {
        this.id = id;
    }

    public Semester(Long id, String name, int year) {
        this.id = id;
        this.name = name;
        this.year = year;
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

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public Collection<ExtraActivity> getExtraActivityCollection() {
        return extraActivityCollection;
    }

    public void setExtraActivityCollection(Collection<ExtraActivity> extraActivityCollection) {
        this.extraActivityCollection = extraActivityCollection;
    }

    public Collection<Bulletin> getBulletinCollection() {
        return bulletinCollection;
    }

    public void setBulletinCollection(Collection<Bulletin> bulletinCollection) {
        this.bulletinCollection = bulletinCollection;
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
        if (!(object instanceof Semester)) {
            return false;
        }
        Semester other = (Semester) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.nhm.pojo.Semester[ id=" + id + " ]";
    }
    
}