/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhm.pojo;

import com.fasterxml.jackson.annotation.JsonView;
import com.nhm.viewconfigs.DisplayView;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import java.io.Serializable;

/**
 *
 * @author GIGABYTE
 */
@Entity
@Table(name = "activity_bulletin")
@NamedQueries({
    @NamedQuery(name = "ActivityBulletin.findAll", query = "SELECT a FROM ActivityBulletin a")})
@PrimaryKeyJoinColumn(name = "bulletin_ptr_id")
public class ActivityBulletin extends Bulletin implements Serializable {

    @JoinColumn(name = "extra_activity_id", referencedColumnName = "id")
    @ManyToOne
    @JsonView({
        DisplayView.Public.class,
        DisplayView.Simplify.class
    })
    private ExtraActivity extraActivityId;

    public ActivityBulletin() {
    }

    public ActivityBulletin(ExtraActivity extraActivityId) {
        this.extraActivityId = extraActivityId;
    }

    public ExtraActivity getExtraActivityId() {
        return extraActivityId;
    }

    public void setExtraActivityId(ExtraActivity extraActivityId) {
        this.extraActivityId = extraActivityId;
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
        if (!(object instanceof ActivityBulletin)) {
            return false;
        }
        ActivityBulletin other = (ActivityBulletin) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.nhm.pojo.ActivityBulletin[ id=" + id + " ]";
    }
    
    @Override
    public ExtraActivity getExtraActivity() {
        return this.getExtraActivityId();
    }
    
}