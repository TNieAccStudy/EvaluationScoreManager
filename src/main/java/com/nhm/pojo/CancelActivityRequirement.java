/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhm.pojo;

import com.fasterxml.jackson.annotation.JsonView;
import com.nhm.viewconfigs.DisplayView;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import java.io.Serializable;

/**
 *
 * @author GIGABYTE
 */
@Entity
@Table(name = "cancel_activity_requirement")
@NamedQueries({
    @NamedQuery(name = "CancelActivityRequirement.findAll", query = "SELECT c FROM CancelActivityRequirement c")})
@PrimaryKeyJoinColumn(name = "cancelrequirement_ptr_id")
public class CancelActivityRequirement extends CancelRequirement implements Serializable {

    @JoinColumn(name = "extra_activity_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    @JsonView(DisplayView.Public.class)
    private ExtraActivity extraActivityId;

    public CancelActivityRequirement() {
    }

    public CancelActivityRequirement(ExtraActivity extraActivityId) {
        this.extraActivityId = extraActivityId;
    }

    public ExtraActivity getExtraActivityId() {
        return extraActivityId;
    }

    public void setExtraActivityId(ExtraActivity extraActivityId) {
        this.extraActivityId = extraActivityId;
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
        if (!(object instanceof CancelActivityRequirement)) {
            return false;
        }
        CancelActivityRequirement other = (CancelActivityRequirement) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.tnieyu.pojo.CancelActivityRequirement[ id=" + id + " ]";
    }
    
}
