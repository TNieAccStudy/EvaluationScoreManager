/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tnieyu.pojo;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
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
@Table(name = "student_assistant")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "StudentAssistant.findAll", query = "SELECT s FROM StudentAssistant s")})
public class StudentAssistant extends UserInfo implements Serializable {
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "studentAssistantId")
    private Collection<ExtraActivity> extraActivityCollection;
    @OneToMany(mappedBy = "studentAssistantId")
    private Collection<MissingActivity> missingActivityCollection;

    public StudentAssistant() {
    }

    @XmlTransient
    public Collection<ExtraActivity> getExtraActivityCollection() {
        return extraActivityCollection;
    }

    public void setExtraActivityCollection(Collection<ExtraActivity> extraActivityCollection) {
        this.extraActivityCollection = extraActivityCollection;
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
        if (!(object instanceof StudentAssistant)) {
            return false;
        }
        StudentAssistant other = (StudentAssistant) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.tnieyu.pojo.StudentAssistant[ id=" + id + " ]";
    }
    
}
