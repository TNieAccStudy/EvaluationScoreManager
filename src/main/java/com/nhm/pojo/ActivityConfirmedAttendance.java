/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhm.pojo;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
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
@Entity
@Table(name = "activity_confirmed_attendance")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ActivityConfirmedAttendance.findAll", query = "SELECT a FROM ActivityConfirmedAttendance a"),
    @NamedQuery(name = "ActivityConfirmedAttendance.findById", query = "SELECT a FROM ActivityConfirmedAttendance a WHERE a.id = :id"),
    @NamedQuery(name = "ActivityConfirmedAttendance.findByActive", query = "SELECT a FROM ActivityConfirmedAttendance a WHERE a.active = :active"),
    @NamedQuery(name = "ActivityConfirmedAttendance.findByCreatedDate", query = "SELECT a FROM ActivityConfirmedAttendance a WHERE a.createdDate = :createdDate"),
    @NamedQuery(name = "ActivityConfirmedAttendance.findByUpdatedDate", query = "SELECT a FROM ActivityConfirmedAttendance a WHERE a.updatedDate = :updatedDate"),
    @NamedQuery(name = "ActivityConfirmedAttendance.findByProofPicture", query = "SELECT a FROM ActivityConfirmedAttendance a WHERE a.proofPicture = :proofPicture")})
public class ActivityConfirmedAttendance implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "active")
    private boolean active;
    @Basic(optional = false)
    @NotNull
    @Column(name = "created_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;
    @Basic(optional = false)
    @NotNull
    @Column(name = "updated_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedDate;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "proofPicture")
    private String proofPicture;
    @JoinColumn(name = "activity_registry_id", referencedColumnName = "id")
    @OneToOne
    private ActivityRegistry activityRegistryId;
    @OneToOne(mappedBy = "missingActivityId")
    private MissingActivity missingActivity;

    public ActivityConfirmedAttendance() {
    }

    public ActivityConfirmedAttendance(Long id) {
        this.id = id;
    }

    public ActivityConfirmedAttendance(Long id, boolean active, Date createdDate, Date updatedDate, String proofPicture) {
        this.id = id;
        this.active = active;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
        this.proofPicture = proofPicture;
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

    public String getProofPicture() {
        return proofPicture;
    }

    public void setProofPicture(String proofPicture) {
        this.proofPicture = proofPicture;
    }

    public ActivityRegistry getActivityRegistryId() {
        return activityRegistryId;
    }

    public void setActivityRegistryId(ActivityRegistry activityRegistryId) {
        this.activityRegistryId = activityRegistryId;
    }

    public MissingActivity getMissingActivity() {
        return missingActivity;
    }

    public void setMissingActivity(MissingActivity missingActivity) {
        this.missingActivity = missingActivity;
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
        if (!(object instanceof ActivityConfirmedAttendance)) {
            return false;
        }
        ActivityConfirmedAttendance other = (ActivityConfirmedAttendance) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.nhm.pojo.ActivityConfirmedAttendance[ id=" + id + " ]";
    }
    
}