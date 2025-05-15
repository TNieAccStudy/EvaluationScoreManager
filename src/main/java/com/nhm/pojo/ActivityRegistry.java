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
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.NotNull;
import jakarta.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author GIGABYTE
 */
@Entity
@Table(name = "activity_registry")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ActivityRegistry.findAll", query = "SELECT a FROM ActivityRegistry a"),
    @NamedQuery(name = "ActivityRegistry.findById", query = "SELECT a FROM ActivityRegistry a WHERE a.id = :id"),
    @NamedQuery(name = "ActivityRegistry.findByActive", query = "SELECT a FROM ActivityRegistry a WHERE a.active = :active"),
    @NamedQuery(name = "ActivityRegistry.findByCreatedDate", query = "SELECT a FROM ActivityRegistry a WHERE a.createdDate = :createdDate"),
    @NamedQuery(name = "ActivityRegistry.findByUpdatedDate", query = "SELECT a FROM ActivityRegistry a WHERE a.updatedDate = :updatedDate")})
public class ActivityRegistry implements Serializable {

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
    @JoinColumn(name = "student_id", referencedColumnName = "id")
    @ManyToOne
    private Student studentId;
    @OneToOne(mappedBy = "activityRegistryId")
    private ActivityConfirmedAttendance activityConfirmedAttendance;

    public ActivityRegistry() {
    }

    public ActivityRegistry(Long id) {
        this.id = id;
    }

    public ActivityRegistry(Long id, boolean active, Date createdDate, Date updatedDate) {
        this.id = id;
        this.active = active;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
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

    public Student getStudentId() {
        return studentId;
    }

    public void setStudentId(Student studentId) {
        this.studentId = studentId;
    }

    public ActivityConfirmedAttendance getActivityConfirmedAttendance() {
        return activityConfirmedAttendance;
    }

    public void setActivityConfirmedAttendance(ActivityConfirmedAttendance activityConfirmedAttendance) {
        this.activityConfirmedAttendance = activityConfirmedAttendance;
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
        if (!(object instanceof ActivityRegistry)) {
            return false;
        }
        ActivityRegistry other = (ActivityRegistry) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.nhm.pojo.ActivityRegistry[ id=" + id + " ]";
    }
    
}