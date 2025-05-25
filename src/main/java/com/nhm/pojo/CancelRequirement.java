/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhm.pojo;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonView;
import com.nhm.viewconfigs.DisplayView;
import jakarta.persistence.Basic;
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
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author GIGABYTE
 */
@Inheritance(strategy = InheritanceType.JOINED)
@Entity
@Table(name = "cancel_requirement")
@NamedQueries({
    @NamedQuery(name = "CancelRequirement.findAll", query = "SELECT c FROM CancelRequirement c"),
    @NamedQuery(name = "CancelRequirement.findById", query = "SELECT c FROM CancelRequirement c WHERE c.id = :id"),
    @NamedQuery(name = "CancelRequirement.findByActive", query = "SELECT c FROM CancelRequirement c WHERE c.active = :active"),
    @NamedQuery(name = "CancelRequirement.findByCreatedDate", query = "SELECT c FROM CancelRequirement c WHERE c.createdDate = :createdDate"),
    @NamedQuery(name = "CancelRequirement.findByUpdatedDate", query = "SELECT c FROM CancelRequirement c WHERE c.updatedDate = :updatedDate"),
    @NamedQuery(name = "CancelRequirement.findByReason", query = "SELECT c FROM CancelRequirement c WHERE c.reason = :reason"),
    @NamedQuery(name = "CancelRequirement.findByExecutedStatus", query = "SELECT c FROM CancelRequirement c WHERE c.executedStatus = :executedStatus")})
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "cancelRequirementType",
        defaultImpl = UserInfo.class
)
@JsonSubTypes(
        value = {
            @JsonSubTypes.Type(value = CancelActivityRequirement.class, name = "activity"),
            @JsonSubTypes.Type(value = CancelBulletinRequirement.class, name = "bulletin")
        }
)
public class CancelRequirement extends BaseModel implements Serializable {

    protected static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    @JsonView({
        DisplayView.Public.class,
        DisplayView.Simplify.class
    })
    protected Long id;
    @Size(max = 255)
    @Column(name = "reason")
    @JsonView(DisplayView.Public.class)
    protected String reason;
    @Lob
    @Size(max = 2147483647)
    @Column(name = "reason_detail")
    @JsonView(DisplayView.Public.class)
    protected String reasonDetail;
    @Basic(optional = false)
    @Size(min = 1, max = 50)
    @Column(name = "executed_status")
    @JsonView({
        DisplayView.Public.class,
        DisplayView.Simplify.class
    })
    protected String executedStatus = ExecuteStatus.PENDING.name();
    @JoinColumn(name = "student_assistant_id", referencedColumnName = "id")
    @ManyToOne
    @JsonView({
        DisplayView.Public.class,
        DisplayView.Simplify.class
    })
    protected StudentAssistant studentAssistantId;
    @JoinColumn(name = "student_affairs_officer_id", referencedColumnName = "id")
    @ManyToOne
    @JsonView(DisplayView.Public.class)
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

    public StudentAssistant getStudentAssistantId() {
        return studentAssistantId;
    }

    public void setStudentAssistantId(StudentAssistant studentAssistantId) {
        this.studentAssistantId = studentAssistantId;
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
    
    public static Class<? extends CancelRequirement> getSubClassByString(String cancelType) {
        if (cancelType == null)
            return CancelRequirement.class;
        
        JsonSubTypes jsonSubTypeAnnotation = CancelRequirement.class.getAnnotation(JsonSubTypes.class);
        for(var t : jsonSubTypeAnnotation.value()) {
            if (t.name().equals(cancelType))
                return (Class<? extends CancelRequirement>) t.value();
        }
        return CancelRequirement.class;
    }
    
}