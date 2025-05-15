/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhm.pojo;

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
import java.io.Serializable;

/**
 *
 * @author MINH
 */
@Entity
@Table(name = "cancel_activity_requirement")
@NamedQueries({
    @NamedQuery(name = "CancelActivityRequirement.findAll", query = "SELECT c FROM CancelActivityRequirement c"),
    @NamedQuery(name = "CancelActivityRequirement.findByCancelrequirementPtrId", query = "SELECT c FROM CancelActivityRequirement c WHERE c.cancelrequirementPtrId = :cancelrequirementPtrId")})
public class CancelActivityRequirement implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "cancelrequirement_ptr_id")
    private Long cancelrequirementPtrId;
    @JoinColumn(name = "cancelrequirement_ptr_id", referencedColumnName = "id", insertable = false, updatable = false)
    @OneToOne(optional = false)
    private CancelRequirement cancelRequirement;
    @JoinColumn(name = "extra_activity_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private ExtraActivity extraActivityId;

    public CancelActivityRequirement() {
    }

    public CancelActivityRequirement(Long cancelrequirementPtrId) {
        this.cancelrequirementPtrId = cancelrequirementPtrId;
    }

    public Long getCancelrequirementPtrId() {
        return cancelrequirementPtrId;
    }

    public void setCancelrequirementPtrId(Long cancelrequirementPtrId) {
        this.cancelrequirementPtrId = cancelrequirementPtrId;
    }

    public CancelRequirement getCancelRequirement() {
        return cancelRequirement;
    }

    public void setCancelRequirement(CancelRequirement cancelRequirement) {
        this.cancelRequirement = cancelRequirement;
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
        hash += (cancelrequirementPtrId != null ? cancelrequirementPtrId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CancelActivityRequirement)) {
            return false;
        }
        CancelActivityRequirement other = (CancelActivityRequirement) object;
        if ((this.cancelrequirementPtrId == null && other.cancelrequirementPtrId != null) || (this.cancelrequirementPtrId != null && !this.cancelrequirementPtrId.equals(other.cancelrequirementPtrId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.nhm.pojo.CancelActivityRequirement[ cancelrequirementPtrId=" + cancelrequirementPtrId + " ]";
    }
    
}
