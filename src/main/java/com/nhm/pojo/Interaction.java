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
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author GIGABYTE
 */
@Inheritance(strategy = InheritanceType.JOINED)
@Entity
@Table(name = "interaction")
@NamedQueries({
    @NamedQuery(name = "Interaction.findAll", query = "SELECT i FROM Interaction i"),
    @NamedQuery(name = "Interaction.findById", query = "SELECT i FROM Interaction i WHERE i.id = :id"),
    @NamedQuery(name = "Interaction.findByActive", query = "SELECT i FROM Interaction i WHERE i.active = :active"),
    @NamedQuery(name = "Interaction.findByCreatedDate", query = "SELECT i FROM Interaction i WHERE i.createdDate = :createdDate"),
    @NamedQuery(name = "Interaction.findByUpdatedDate", query = "SELECT i FROM Interaction i WHERE i.updatedDate = :updatedDate")})
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "interactionType",
        defaultImpl = UserInfo.class
)
@JsonSubTypes(
        value = {
            @JsonSubTypes.Type(value = Comment.class, name = "comment"),
            @JsonSubTypes.Type(value = Reactions.class, name = "reactions")
        }
)
public class Interaction extends BaseModel implements Serializable {

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
    @JoinColumn(name = "bulletin_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    @JsonView({
        DisplayView.Public.class,
        DisplayView.Simplify.class
    })
    protected Bulletin bulletinId;
    @JoinColumn(name = "student_id", referencedColumnName = "user_ptr_id")
    @ManyToOne(optional = false)
    @JsonView({
        DisplayView.Public.class,
        DisplayView.Simplify.class
    })
    protected Student studentId;

    public Interaction() {
    }

    public Interaction(Long id) {
        this.id = id;
    }

    public Interaction(Long id, boolean active, Date createdDate, Date updatedDate) {
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

    public Bulletin getBulletinId() {
        return bulletinId;
    }

    public void setBulletinId(Bulletin bulletinId) {
        this.bulletinId = bulletinId;
    }

    public Student getStudentId() {
        return studentId;
    }

    public void setStudentId(Student studentId) {
        this.studentId = studentId;
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
        if (!(object instanceof Interaction)) {
            return false;
        }
        Interaction other = (Interaction) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.nhm.pojo.Interaction[ id=" + id + " ]";
    }
    
    public static Class<? extends Interaction> getSubClassByString(String interactionType) {
        if (interactionType == null)
            return Interaction.class;
        
        JsonSubTypes jsonSubTypeAnnotation = Interaction.class.getAnnotation(JsonSubTypes.class);
        for(var t : jsonSubTypeAnnotation.value()) {
            if (t.name().equals(interactionType))
                return (Class<? extends Interaction>) t.value();
        }
        return Interaction.class;
    }
    
}