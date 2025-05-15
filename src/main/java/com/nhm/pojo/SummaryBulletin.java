/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhm.pojo;

import com.fasterxml.jackson.annotation.JsonView;
import com.nhm.viewconfigs.CollectionView;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlTransient;
import java.io.Serializable;
import java.util.Collection;

/**
 *
 * @author GIGABYTE
 */
@Entity
@Table(name = "summary_bulletin")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SummaryBulletin.findAll", query = "SELECT s FROM SummaryBulletin s")})
@PrimaryKeyJoinColumn(name = "bulletin_ptr_id")
public class SummaryBulletin extends Bulletin implements Serializable {
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "summaryBulletinId")
    @JsonView(CollectionView.SummaryBulletinCollection.class)
    private Collection<MissingActivity> missingActivityCollection;

    public SummaryBulletin() {
    }

    @XmlTransient
    public Collection<MissingActivity> getMissingActivityCollection() {
        return missingActivityCollection;
    }

    public void setMissingActivityCollection(Collection<MissingActivity> missingActivityCollection) {
        this.missingActivityCollection = missingActivityCollection;
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
        if (!(object instanceof SummaryBulletin)) {
            return false;
        }
        SummaryBulletin other = (SummaryBulletin) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.nhm.pojo.SummaryBulletin[ id=" + id + " ]";
    }
    
}