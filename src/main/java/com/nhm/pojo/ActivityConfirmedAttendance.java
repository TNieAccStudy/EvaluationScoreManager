/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhm.pojo;

import com.fasterxml.jackson.annotation.JsonView;
import com.nhm.viewconfigs.DisplayView;
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
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author GIGABYTE
 */
@Entity
@Table(name = "activity_confirmed_attendance")
@NamedQueries({
    @NamedQuery(name = "ActivityConfirmedAttendance.findAll", query = "SELECT a FROM ActivityConfirmedAttendance a"),
    @NamedQuery(name = "ActivityConfirmedAttendance.findById", query = "SELECT a FROM ActivityConfirmedAttendance a WHERE a.id = :id"),
    @NamedQuery(name = "ActivityConfirmedAttendance.findByActive", query = "SELECT a FROM ActivityConfirmedAttendance a WHERE a.active = :active"),
    @NamedQuery(name = "ActivityConfirmedAttendance.findByCreatedDate", query = "SELECT a FROM ActivityConfirmedAttendance a WHERE a.createdDate = :createdDate"),
    @NamedQuery(name = "ActivityConfirmedAttendance.findByUpdatedDate", query = "SELECT a FROM ActivityConfirmedAttendance a WHERE a.updatedDate = :updatedDate"),
    @NamedQuery(name = "ActivityConfirmedAttendance.findByProofPicture", query = "SELECT a FROM ActivityConfirmedAttendance a WHERE a.proofPicture = :proofPicture"),
    @NamedQuery(name = "ActivityConfirmedAttendance.findByCensorState", query = "SELECT a FROM ActivityConfirmedAttendance a WHERE a.censorState = :censorState")})
public class ActivityConfirmedAttendance extends BaseModel implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    @JsonView({
        DisplayView.Public.class,
        DisplayView.Simplify.class
    })
    private Long id;
    @Basic(optional = false)
    @Size(min = 1, max = 255)
    @Column(name = "proofPicture")
    @JsonView(DisplayView.Public.class)
    private String proofPicture;
    @Basic(optional = false)
    @Size(min = 1, max = 50)
    @Column(name = "censor_state")
    @JsonView({
        DisplayView.Public.class,
        DisplayView.Simplify.class
    })
    protected String censorState = CensorState.PENDING.name();
    @JoinColumn(name = "activity_registry_id", referencedColumnName = "id")
    @OneToOne
    @JsonView({
        DisplayView.Public.class,
        DisplayView.Simplify.class
    })
    private ActivityRegistry activityRegistryId;
    @OneToOne(mappedBy = "activityConfirmedAttendanceId")
    @JsonView(DisplayView.Attach.class)
    private MissingActivity missingActivity;

    public ActivityConfirmedAttendance() {
    }

    public ActivityConfirmedAttendance(Long id) {
        this.id = id;
    }

    public ActivityConfirmedAttendance(Long id, boolean active, Date createdDate, Date updatedDate, String proofPicture, String censorState) {
        this.id = id;
        this.active = active;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
        this.proofPicture = proofPicture;
        this.censorState = censorState;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProofPicture() {
        return proofPicture;
    }

    public void setProofPicture(String proofPicture) {
        this.proofPicture = proofPicture;
    }

    public String getCensorState() {
        return censorState;
    }

    public void setCensorState(String censorState) {
        this.censorState = censorState;
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
    
    public static enum CensorState {
        PENDING,
        CONFIRMED,
        CANCELED;
        
        public static CensorState responseCensorStateFormExecStatus(ExecuteStatus execStatus) throws Exception {
            switch(execStatus) {
                case CANCELED:
                    return CensorState.CANCELED;
                case CONFIRMED:
                    return CensorState.CONFIRMED;
                case PENDING:
                    return CensorState.PENDING;
                default:
                    throw new Exception("don't have any type of execStatus");
            }
        }
    }
    
}