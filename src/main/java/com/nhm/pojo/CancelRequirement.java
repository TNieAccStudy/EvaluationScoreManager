/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhm.pojo;

import jakarta.persistence.Basic;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author GIGABYTE
 */
@Inheritance(strategy = InheritanceType.JOINED)
@Entity
@Table(name = "cancel_requirement")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CancelRequirement.findAll", query = "SELECT c FROM CancelRequirement c"),
    @NamedQuery(name = "CancelRequirement.findById", query = "SELECT c FROM CancelRequirement c WHERE c.id = :id"),
    @NamedQuery(name = "CancelRequirement.findByActive", query = "SELECT c FROM CancelRequirement c WHERE c.active = :active"),
    @NamedQuery(name = "CancelRequirement.findByCreatedDate", query = "SELECT c FROM CancelRequirement c WHERE c.createdDate = :createdDate"),
    @NamedQuery(name = "CancelRequirement.findByUpdatedDate", query = "SELECT c FROM CancelRequirement c WHERE c.updatedDate = :updatedDate"),
    @NamedQuery(name = "CancelRequirement.findByReason", query = "SELECT c FROM CancelRequirement c WHERE c.reason = :reason"),
    @NamedQuery(name = "CancelRequirement.findByExecutedStatus", query = "SELECT c FROM CancelRequirement c WHERE c.executedStatus = :executedStatus")})
public class CancelRequirement implements Serializable {

    protected static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    protected Long id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "active")
    protected boolean active;
    @Basic(optional = false)
    @NotNull
    @Column(name = "created_date")
    @Temporal(TemporalType.TIMESTAMP)
    protected Date createdDate;
    @Basic(optional = false)
    @NotNull
    @Column(name = "updated_date")
    @Temporal(TemporalType.TIMESTAMP)
    protected Date updatedDate;
    @Size(max = 255)
    @Column(name = "reason")
    protected String reason;
    @Lob
    @Size(max = 2147483647)
    @Column(name = "reason_detail")
    protected String reasonDetail;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "executed_status")
    protected String executedStatus;
    @JoinColumn(name = "student_affairs_officer_id", referencedColumnName = "id")
    @ManyToOne
    protected StudentAffairsOfficer studentAffairsOfficerId;

    public CancelRequirement() {
    }

    public CancelRequirement(Long id) {
        this.id = id;
    }

    public CancelRequirement(Long id, boolean active, Date createdDate, Date updatedDate, String executedStatus) {
        this.id = id;
        this.active = active;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
        this.executedStatus = executedStatus;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean getActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getReasonDetail() {
        return reasonDetail;
    }

    public void setReasonDetail(String reasonDetail) {
        this.reasonDetail = reasonDetail;
    }

    public String getExecutedStatus() {
        return executedStatus;
    }

    public void setExecutedStatus(String executedStatus) {
        this.executedStatus = executedStatus;
    }

    public StudentAffairsOfficer getStudentAffairsOfficerId() {
        return studentAffairsOfficerId;
    }

    public void setStudentAffairsOfficerId(StudentAffairsOfficer studentAffairsOfficerId) {
        this.studentAffairsOfficerId = studentAffairsOfficerId;
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
        if (!(object instanceof CancelRequirement)) {
            return false;
        }
        CancelRequirement other = (CancelRequirement) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.nhm.pojo.CancelRequirement[ id=" + id + " ]";
    }
    
}