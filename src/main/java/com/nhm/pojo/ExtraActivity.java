/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhm.pojo;

import com.fasterxml.jackson.annotation.JsonView;
import com.nhm.viewconfigs.CollectionView;
import com.nhm.viewconfigs.DisplayView;
import jakarta.persistence.Basic;
import jakarta.persistence.CascadeType;
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
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

/**
 *
 * @author GIGABYTE
 */
@Entity
@Table(name = "extra_activity")
@NamedQueries({
    @NamedQuery(name = "ExtraActivity.findAll", query = "SELECT e FROM ExtraActivity e"),
    @NamedQuery(name = "ExtraActivity.findById", query = "SELECT e FROM ExtraActivity e WHERE e.id = :id"),
    @NamedQuery(name = "ExtraActivity.findByActive", query = "SELECT e FROM ExtraActivity e WHERE e.active = :active"),
    @NamedQuery(name = "ExtraActivity.findByCreatedDate", query = "SELECT e FROM ExtraActivity e WHERE e.createdDate = :createdDate"),
    @NamedQuery(name = "ExtraActivity.findByUpdatedDate", query = "SELECT e FROM ExtraActivity e WHERE e.updatedDate = :updatedDate"),
    @NamedQuery(name = "ExtraActivity.findByBonusScore", query = "SELECT e FROM ExtraActivity e WHERE e.bonusScore = :bonusScore"),
    @NamedQuery(name = "ExtraActivity.findByTitle", query = "SELECT e FROM ExtraActivity e WHERE e.title = :title")})
public class ExtraActivity extends BaseModel implements Serializable {

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
    @NotNull
    @Column(name = "bonus_score")
    @JsonView({
        DisplayView.Public.class,
        DisplayView.Simplify.class
    })
    private int bonusScore;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "title")
    @JsonView({
        DisplayView.Public.class,
        DisplayView.Simplify.class
    })
    private String title;
    @Lob
    @Size(max = 2147483647)
    @Column(name = "description")
    @JsonView(DisplayView.Public.class)
    private String description;
    @OneToMany(mappedBy = "extraActivityId")
    @JsonView(CollectionView.ActivityColelction.class)
    private Collection<ActivityBulletin> activityBulletinCollection;
    @JoinColumn(name = "semester_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    @JsonView({
        DisplayView.Public.class,
        DisplayView.Simplify.class
    })
//    @JsonDeserialize(using = TermDeserializer.class)
    private Semester semesterId;
    @JoinColumn(name = "student_assistant_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    @JsonView(DisplayView.Internal.class)
//    @JsonDeserialize(using = StudentAssistantDeserializer.class)
    private StudentAssistant studentAssistantId;
    @JoinColumn(name = "term_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    @JsonView({
        DisplayView.Public.class,
        DisplayView.Simplify.class
    })
//    @JsonDeserialize(using = TermDeserializer.class)
    private Term termId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "extraActivityId")
    @JsonView(CollectionView.ActivityColelction.class)
    private Collection<CancelActivityRequirement> cancelActivityRequirementCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "extraActivityId")
    @JsonView(CollectionView.ActivityColelction.class)
    private Collection<MissingActivity> missingActivityCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "extraActivityId")
    @JsonView(CollectionView.ActivityColelction.class)
    private Collection<ActivityRegistry> activityRegistryCollection;

    public ExtraActivity() {
    }

    public ExtraActivity(Long id) {
        this.id = id;
    }

    public ExtraActivity(Long id, boolean active, Date createdDate, Date updatedDate, int bonusScore, String title) {
        this.id = id;
        this.active = active;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
        this.bonusScore = bonusScore;
        this.title = title;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public int getBonusScore() {
        return bonusScore;
    }

    public void setBonusScore(int bonusScore) {
        this.bonusScore = bonusScore;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Collection<ActivityBulletin> getActivityBulletinCollection() {
        return activityBulletinCollection;
    }

    public void setActivityBulletinCollection(Collection<ActivityBulletin> activityBulletinCollection) {
        this.activityBulletinCollection = activityBulletinCollection;
    }

    public Semester getSemesterId() {
        return semesterId;
    }

    public void setSemesterId(Semester semesterId) {
        this.semesterId = semesterId;
    }

    public StudentAssistant getStudentAssistantId() {
        return studentAssistantId;
    }

    public void setStudentAssistantId(StudentAssistant studentAssistantId) {
        this.studentAssistantId = studentAssistantId;
    }

    public Term getTermId() {
        return termId;
    }

    public void setTermId(Term termId) {
        this.termId = termId;
    }

    public Collection<CancelActivityRequirement> getCancelActivityRequirementCollection() {
        return cancelActivityRequirementCollection;
    }

    public void setCancelActivityRequirementCollection(Collection<CancelActivityRequirement> cancelActivityRequirementCollection) {
        this.cancelActivityRequirementCollection = cancelActivityRequirementCollection;
    }

    public Collection<MissingActivity> getMissingActivityCollection() {
        return missingActivityCollection;
    }

    public void setMissingActivityCollection(Collection<MissingActivity> missingActivityCollection) {
        this.missingActivityCollection = missingActivityCollection;
    }

    public Collection<ActivityRegistry> getActivityRegistryCollection() {
        return activityRegistryCollection;
    }

    public void setActivityRegistryCollection(Collection<ActivityRegistry> activityRegistryCollection) {
        this.activityRegistryCollection = activityRegistryCollection;
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
        if (!(object instanceof ExtraActivity)) {
            return false;
        }
        ExtraActivity other = (ExtraActivity) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.nhm.pojo.ExtraActivity[ id=" + id + " ]";
    }
    
}