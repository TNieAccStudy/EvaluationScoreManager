/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tnieyu.pojo;

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
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlTransient;
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import jakarta.persistence.InheritanceType;

/**
 *
 * @author GIGABYTE
 */
@Inheritance(strategy = InheritanceType.JOINED)
@Entity
@Table(name = "bulletin")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Bulletin.findAll", query = "SELECT b FROM Bulletin b"),
    @NamedQuery(name = "Bulletin.findById", query = "SELECT b FROM Bulletin b WHERE b.id = :id"),
    @NamedQuery(name = "Bulletin.findByActive", query = "SELECT b FROM Bulletin b WHERE b.active = :active"),
    @NamedQuery(name = "Bulletin.findByCreatedDate", query = "SELECT b FROM Bulletin b WHERE b.createdDate = :createdDate"),
    @NamedQuery(name = "Bulletin.findByUpdatedDate", query = "SELECT b FROM Bulletin b WHERE b.updatedDate = :updatedDate"),
    @NamedQuery(name = "Bulletin.findByTitle", query = "SELECT b FROM Bulletin b WHERE b.title = :title"),
    @NamedQuery(name = "Bulletin.findByDuration", query = "SELECT b FROM Bulletin b WHERE b.duration = :duration")})
public class Bulletin implements Serializable {

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
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "title")
    protected String title;
    @Lob
    @Size(max = 2147483647)
    @Column(name = "content")
    protected String content;
    @Basic(optional = false)
    @NotNull
    @Column(name = "duration")
    @Temporal(TemporalType.TIMESTAMP)
    protected Date duration;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "bulletinId")
    protected Collection<CancelBulletinRequirement> cancelBulletinRequirementCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "bulletinId")
    protected Collection<Interaction> interactionCollection;

    public Bulletin() {
    }

    public Bulletin(Long id) {
        this.id = id;
    }

    public Bulletin(Long id, boolean active, Date createdDate, Date updatedDate, String title, Date duration) {
        this.id = id;
        this.active = active;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
        this.title = title;
        this.duration = duration;
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

    @XmlTransient
    public Collection<CancelBulletinRequirement> getCancelBulletinRequirementCollection() {
        return cancelBulletinRequirementCollection;
    }

    public void setCancelBulletinRequirementCollection(Collection<CancelBulletinRequirement> cancelBulletinRequirementCollection) {
        this.cancelBulletinRequirementCollection = cancelBulletinRequirementCollection;
    }

    @XmlTransient
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
        return "com.tnieyu.pojo.Bulletin[ id=" + id + " ]";
    }
    
}
