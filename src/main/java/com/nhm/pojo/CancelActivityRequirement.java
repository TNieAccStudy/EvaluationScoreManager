/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tnieyu.pojo;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 *
 * @author GIGABYTE
 */
@Entity
@Table(name = "cancel_activity_requirement")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CancelActivityRequirement.findAll", query = "SELECT c FROM CancelActivityRequirement c")})
public class CancelActivityRequirement extends CancelRequirement implements Serializable {

    @JoinColumn(name = "extra_activity_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
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
