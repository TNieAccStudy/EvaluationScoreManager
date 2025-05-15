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
@Inheritance(strategy = InheritanceType.JOINED)
@Entity
@Table(name = "interaction")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Interaction.findAll", query = "SELECT i FROM Interaction i"),
    @NamedQuery(name = "Interaction.findById", query = "SELECT i FROM Interaction i WHERE i.id = :id"),
    @NamedQuery(name = "Interaction.findByActive", query = "SELECT i FROM Interaction i WHERE i.active = :active"),
    @NamedQuery(name = "Interaction.findByCreatedDate", query = "SELECT i FROM Interaction i WHERE i.createdDate = :createdDate"),
    @NamedQuery(name = "Interaction.findByUpdatedDate", query = "SELECT i FROM Interaction i WHERE i.updatedDate = :updatedDate")})
public class Interaction implements Serializable {

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
    @JoinColumn(name = "bulletin_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    protected Bulletin bulletinId;
    @JoinColumn(name = "student_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
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
    
}