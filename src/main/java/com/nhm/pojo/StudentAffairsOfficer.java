/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tnieyu.pojo;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlTransient;
import java.io.Serializable;
import java.util.Collection;

/**
 *
 * @author GIGABYTE
 */
@Entity
@Table(name = "student_affairs_officer")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "StudentAffairsOfficer.findAll", query = "SELECT s FROM StudentAffairsOfficer s")})
public class StudentAffairsOfficer extends UserInfo implements Serializable {

    @OneToMany(mappedBy = "studentAffairsOfficerId")
    private Collection<CancelRequirement> cancelRequirementCollection;

    public StudentAffairsOfficer() {
    }
    
    @XmlTransient
    public Collection<CancelRequirement> getCancelRequirementCollection() {
        return cancelRequirementCollection;
    }

    public void setCancelRequirementCollection(Collection<CancelRequirement> cancelRequirementCollection) {
        this.cancelRequirementCollection = cancelRequirementCollection;
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
        if (!(object instanceof StudentAffairsOfficer)) {
            return false;
        }
        StudentAffairsOfficer other = (StudentAffairsOfficer) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.tnieyu.pojo.StudentAffairsOfficer[ id=" + id + " ]";
    }
    
}
