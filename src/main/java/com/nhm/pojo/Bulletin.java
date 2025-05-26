/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhm.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
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
import jakarta.persistence.Inheritance;
import jakarta.persistence.Lob;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import org.springframework.format.annotation.DateTimeFormat;

/**
 *
 * @author GIGABYTE
 */
@Inheritance(strategy = InheritanceType.JOINED)
@Entity
@Table(name = "bulletin")
@NamedQueries({
    @NamedQuery(name = "Bulletin.findAll", query = "SELECT b FROM Bulletin b"),
    @NamedQuery(name = "Bulletin.findById", query = "SELECT b FROM Bulletin b WHERE b.id = :id"),
    @NamedQuery(name = "Bulletin.findByActive", query = "SELECT b FROM Bulletin b WHERE b.active = :active"),
    @NamedQuery(name = "Bulletin.findByCreatedDate", query = "SELECT b FROM Bulletin b WHERE b.createdDate = :createdDate"),
    @NamedQuery(name = "Bulletin.findByUpdatedDate", query = "SELECT b FROM Bulletin b WHERE b.updatedDate = :updatedDate"),
    @NamedQuery(name = "Bulletin.findByTitle", query = "SELECT b FROM Bulletin b WHERE b.title = :title"),
    @NamedQuery(name = "Bulletin.findByDuration", query = "SELECT b FROM Bulletin b WHERE b.duration = :duration"),
    @NamedQuery(name = "Bulletin.findByState", query = "SELECT c FROM Bulletin c WHERE c.state = :state")})
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "bulletinType",
        defaultImpl = UserInfo.class
)
@JsonSubTypes(
        value = {
            @JsonSubTypes.Type(value = ActivityBulletin.class, name = "activity"),
            @JsonSubTypes.Type(value = SummaryBulletin.class, name = "summary")
        }
)
public class Bulletin extends BaseModel implements Serializable {

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
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "title")
    @JsonView(DisplayView.Public.class)
    protected String title;
    @Lob
    @Size(max = 2147483647)
    @Column(name = "content")
    @JsonView(DisplayView.Public.class)
    protected String content;
    @Basic(optional = false)
    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @Column(name = "duration")
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Ho_Chi_Minh")
    @JsonView(DisplayView.Public.class)
    protected Date duration;
    @Basic(optional = false)
    @Size(min = 1, max = 50)
    @Column(name = "state")
    @JsonView({
        DisplayView.Public.class,
        DisplayView.Simplify.class
    })
    protected String state = BulletinState.OPENING.name();
    @JoinColumn(name = "student_assistant_id", referencedColumnName = "user_ptr_id")
    @ManyToOne(optional = false)
    @JsonView({
        DisplayView.Public.class,
        DisplayView.Simplify.class
    })
    protected StudentAssistant studentAssistantId;
    @JoinColumn(name = "semester_id", referencedColumnName = "id")
    @NotNull
    @ManyToOne(optional = false)
    @JsonView({
        DisplayView.Public.class,
        DisplayView.Simplify.class
    })
    protected Semester semesterId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "bulletinId")
    @JsonView(CollectionView.BulletinCollection.class)
    protected Collection<CancelBulletinRequirement> cancelBulletinRequirementCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "bulletinId")
    @JsonView(CollectionView.BulletinCollection.class)
    protected Collection<Interaction> interactionCollection;

    public Bulletin() {
    }

    public Bulletin(Long id) {
        this.id = id;
    }

    public Bulletin(Long id, boolean active, Date createdDate, Date updatedDate, String title, Date duration, String state) {
        this.id = id;
        this.active = active;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
        this.title = title;
        this.duration = duration;
        this.state = state;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getDuration() {
        return duration;
    }

    public void setDuration(Date duration) {
        this.duration = duration;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public StudentAssistant getStudentAssistantId() {
        return studentAssistantId;
    }

    public void setStudentAssistantId(StudentAssistant studentAssistantId) {
        this.studentAssistantId = studentAssistantId;
    }

    public Semester getSemesterId() {
        return semesterId;
    }

    public void setSemesterId(Semester semesterId) {
        this.semesterId = semesterId;
    }

    public Collection<CancelBulletinRequirement> getCancelBulletinRequirementCollection() {
        return cancelBulletinRequirementCollection;
    }

    public void setCancelBulletinRequirementCollection(Collection<CancelBulletinRequirement> cancelBulletinRequirementCollection) {
        this.cancelBulletinRequirementCollection = cancelBulletinRequirementCollection;
    }

    public Collection<Interaction> getInteractionCollection() {
        return interactionCollection;
    }

    public void setInteractionCollection(Collection<Interaction> interactionCollection) {
        this.interactionCollection = interactionCollection;
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
        if (!(object instanceof Bulletin)) {
            return false;
        }
        Bulletin other = (Bulletin) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.nhm.pojo.Bulletin[ id=" + id + " ]";
    }
    
    public ExtraActivity getExtraActivity() {
        return null;
    }
    
    public static enum BulletinState {
        OPENING,
        CLOSED
    }
    
    public static Class<? extends Bulletin> getSubClassByString(String bulletinType) {
        if (bulletinType == null)
            return Bulletin.class;
        
        JsonSubTypes jsonSubTypeAnnotation = Bulletin.class.getAnnotation(JsonSubTypes.class);
        for(var t : jsonSubTypeAnnotation.value()) {
            if (t.name().equals(bulletinType))
                return (Class<? extends Bulletin>) t.value();
        }
        return Bulletin.class;
    }
    
}