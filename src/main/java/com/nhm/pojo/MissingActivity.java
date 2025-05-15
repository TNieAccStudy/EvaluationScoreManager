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
@Entity
@Table(name = "missing_activity")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MissingActivity.findAll", query = "SELECT m FROM MissingActivity m"),
    @NamedQuery(name = "MissingActivity.findById", query = "SELECT m FROM MissingActivity m WHERE m.id = :id"),
    @NamedQuery(name = "MissingActivity.findByActive", query = "SELECT m FROM MissingActivity m WHERE m.active = :active"),
    @NamedQuery(name = "MissingActivity.findByCreatedDate", query = "SELECT m FROM MissingActivity m WHERE m.createdDate = :createdDate"),
    @NamedQuery(name = "MissingActivity.findByUpdatedDate", query = "SELECT m FROM MissingActivity m WHERE m.updatedDate = :updatedDate"),
    @NamedQuery(name = "MissingActivity.findByProofPicture", query = "SELECT m FROM MissingActivity m WHERE m.proofPicture = :proofPicture"),
    @NamedQuery(name = "MissingActivity.findByExecutedStatus", query = "SELECT m FROM MissingActivity m WHERE m.executedStatus = :executedStatus")})
public class MissingActivity implements Serializable {

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
    @Lob
    @Size(max = 2147483647)
    @Column(name = "proof_content")
    private String proofContent;
    @Size(max = 255)
    @Column(name = "proof_picture")
    private String proofPicture;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "executed_status")
    private String executedStatus;
    @JoinColumn(name = "missing_activity_id", referencedColumnName = "id")
    @OneToOne
    private ActivityConfirmedAttendance missingActivityId;
    @JoinColumn(name = "extra_activity_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private ExtraActivity extraActivityId;
    @JoinColumn(name = "student_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Student studentId;
    @JoinColumn(name = "student_assistant_id", referencedColumnName = "id")
    @ManyToOne
    private StudentAssistant studentAssistantId;
    @JoinColumn(name = "summary_bulletin_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private SummaryBulletin summaryBulletinId;

    public MissingActivity() {
    }

    public MissingActivity(Long id) {
        this.id = id;
    }

    public MissingActivity(Long id, boolean active, Date createdDate, Date updatedDate, String executedStatus) {
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

    public String getProofContent() {
        return proofContent;
    }

    public void setProofContent(String proofContent) {
        this.proofContent = proofContent;
    }

    public String getProofPicture() {
        return proofPicture;
    }

    public void setProofPicture(String proofPicture) {
        this.proofPicture = proofPicture;
    }

    public String getExecutedStatus() {
        return executedStatus;
    }

    public void setExecutedStatus(String executedStatus) {
        this.executedStatus = executedStatus;
    }

    public ActivityConfirmedAttendance getMissingActivityId() {
        return missingActivityId;
    }

    public void setMissingActivityId(ActivityConfirmedAttendance missingActivityId) {
        this.missingActivityId = missingActivityId;
    }

    public ExtraActivity getExtraActivityId() {
        return extraActivityId;
    }

    public void setExtraActivityId(ExtraActivity extraActivityId) {
        this.extraActivityId = extraActivityId;
    }

    public Student getStudentId() {
        return studentId;
    }

    public void setStudentId(Student studentId) {
        this.studentId = studentId;
    }

    public StudentAssistant getStudentAssistantId() {
        return studentAssistantId;
    }

    public void setStudentAssistantId(StudentAssistant studentAssistantId) {
        this.studentAssistantId = studentAssistantId;
    }

    public SummaryBulletin getSummaryBulletinId() {
        return summaryBulletinId;
    }

    public void setSummaryBulletinId(SummaryBulletin summaryBulletinId) {
        this.summaryBulletinId = summaryBulletinId;
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
        if (!(object instanceof MissingActivity)) {
            return false;
        }
        MissingActivity other = (MissingActivity) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.nhm.pojo.MissingActivity[ id=" + id + " ]";
    }
    
}