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
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlTransient;
import java.io.Serializable;
import java.util.Collection;

/**
 *
 * @author GIGABYTE
 */
@Entity
@Table(name = "student")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Student.findAll", query = "SELECT s FROM Student s"),
    @NamedQuery(name = "Student.findByAchievement", query = "SELECT s FROM Student s WHERE s.achievement = :achievement"),
    @NamedQuery(name = "Student.findByMssv", query = "SELECT s FROM Student s WHERE s.mssv = :mssv")})
@PrimaryKeyJoinColumn(name = "user_ptr_id")
public class Student extends UserInfo implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "achievement")
    @JsonView(DisplayView.Internal.class)
    private String achievement;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 11)
    @Column(name = "mssv")
    @JsonView(DisplayView.Internal.class)
    private String mssv;
    @OneToMany(mappedBy = "studentId")
    @JsonView(CollectionView.StudentCollection.class)
    private Collection<ActivityRegistry> activityRegistryCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "studentId")
    @JsonView(CollectionView.StudentCollection.class)
    private Collection<Interaction> interactionCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "studentId")
    @JsonView(CollectionView.StudentCollection.class)
    private Collection<MissingActivity> missingActivityCollection;

    public Student() {
    }

    public Student(String achievement, String mssv) {
        this.achievement = achievement;
        this.mssv = mssv;
    }

    public String getAchievement() {
        return achievement;
    }

    public void setAchievement(String achievement) {
        this.achievement = achievement;
    }

    public String getMssv() {
        return mssv;
    }

    public void setMssv(String mssv) {
        this.mssv = mssv;
    }

    @XmlTransient
    public Collection<ActivityRegistry> getActivityRegistryCollection() {
        return activityRegistryCollection;
    }

    public void setActivityRegistryCollection(Collection<ActivityRegistry> activityRegistryCollection) {
        this.activityRegistryCollection = activityRegistryCollection;
    }

    @XmlTransient
    public Collection<Interaction> getInteractionCollection() {
        return interactionCollection;
    }

    public void setInteractionCollection(Collection<Interaction> interactionCollection) {
        this.interactionCollection = interactionCollection;
    }

    @XmlTransient
    public Collection<MissingActivity> getMissingActivityCollection() {
        return missingActivityCollection;
    }

    public void setMissingActivityCollection(Collection<MissingActivity> missingActivityCollection) {
        this.missingActivityCollection = missingActivityCollection;
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
        if (!(object instanceof Student)) {
            return false;
        }
        Student other = (Student) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.nhm.pojo.Student[ id=" + id + " ]";
    }
    
}