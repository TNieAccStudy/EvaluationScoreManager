/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhm.pojo;

import com.fasterxml.jackson.annotation.JsonView;
import com.nhm.viewconfigs.CollectionView;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.util.Collection;

/**
 *
 * @author GIGABYTE
 */
@Entity
@Table(name = "student_assistant")
@NamedQueries({
    @NamedQuery(name = "StudentAssistant.findAll", query = "SELECT s FROM StudentAssistant s")})
@PrimaryKeyJoinColumn(name = "user_ptr_id")
public class StudentAssistant extends UserInfo implements Serializable {
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "studentAssistantId")
    @JsonView(CollectionView.StudentAssistantCollection.class)
    private Collection<ExtraActivity> extraActivityCollection;
    @OneToMany(mappedBy = "studentAssistantId")
    @JsonView(CollectionView.StudentAssistantCollection.class)
    private Collection<MissingActivity> missingActivityCollection;

    public StudentAssistant() {
    }

    public Collection<ExtraActivity> getExtraActivityCollection() {
        return extraActivityCollection;
    }

    public void setExtraActivityCollection(Collection<ExtraActivity> extraActivityCollection) {
        this.extraActivityCollection = extraActivityCollection;
    }

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
        return "com.nhm.pojo.StudentAssistant[ id=" + id + " ]";
    }
    
}