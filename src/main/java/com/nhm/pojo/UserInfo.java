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
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;
import jakarta.persistence.InheritanceType;

/**
 *
 * @author GIGABYTE
 */
@Inheritance(strategy = InheritanceType.JOINED)
@Entity
@Table(name = "user_info")
@NamedQueries({
    @NamedQuery(name = "UserInfo.findAll", query = "SELECT u FROM UserInfo u"),
    @NamedQuery(name = "UserInfo.findById", query = "SELECT u FROM UserInfo u WHERE u.id = :id"),
    @NamedQuery(name = "UserInfo.findByActive", query = "SELECT u FROM UserInfo u WHERE u.active = :active"),
    @NamedQuery(name = "UserInfo.findByCreatedDate", query = "SELECT u FROM UserInfo u WHERE u.createdDate = :createdDate"),
    @NamedQuery(name = "UserInfo.findByUpdatedDate", query = "SELECT u FROM UserInfo u WHERE u.updatedDate = :updatedDate"),
    @NamedQuery(name = "UserInfo.findByFirstName", query = "SELECT u FROM UserInfo u WHERE u.firstName = :firstName"),
    @NamedQuery(name = "UserInfo.findByLastName", query = "SELECT u FROM UserInfo u WHERE u.lastName = :lastName"),
    @NamedQuery(name = "UserInfo.findByUsername", query = "SELECT u FROM UserInfo u WHERE u.username = :username"),
    @NamedQuery(name = "UserInfo.findByPassword", query = "SELECT u FROM UserInfo u WHERE u.password = :password"),
    @NamedQuery(name = "UserInfo.findByAvatar", query = "SELECT u FROM UserInfo u WHERE u.avatar = :avatar"),
    @NamedQuery(name = "UserInfo.findByEmail", query = "SELECT u FROM UserInfo u WHERE u.email = :email"),
    @NamedQuery(name = "UserInfo.findByPhone", query = "SELECT u FROM UserInfo u WHERE u.phone = :phone"),
    @NamedQuery(name = "UserInfo.findByUserRole", query = "SELECT u FROM UserInfo u WHERE u.userRole = :userRole")})
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "userType",
        defaultImpl = UserInfo.class
)
@JsonSubTypes(
        value = {
            @JsonSubTypes.Type(value = Student.class, name = "student"),
            @JsonSubTypes.Type(value = StudentAssistant.class, name = "studentAsisstant"),
            @JsonSubTypes.Type(value = StudentAffairsOfficer.class, name = "studentAffairsOfficer"),
        }
)
public class UserInfo extends BaseModel implements Serializable {

    protected static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    @JsonView(DisplayView.Public.class)
    protected Long id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "first_name")
    @JsonView(DisplayView.Public.class)
    protected String firstName;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "last_name")
    @JsonView(DisplayView.Public.class)
    protected String lastName;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 60)
    @Column(name = "username")
    @JsonView(DisplayView.Internal.class)
    protected String username;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 124)
    @Column(name = "password")
    @JsonView(DisplayView.Internal.class)
    private String password;
    @Basic(optional = false)
    @Size(min = 1, max = 124)
    @Column(name = "avatar")
    @JsonView(DisplayView.Public.class)
    protected String avatar;
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 124)
    @Column(name = "email")
    @JsonView(DisplayView.Internal.class)
    protected String email;
    // @Pattern(regexp="^\\(?(\\d{3})\\)?[- ]?(\\d{3})[- ]?(\\d{4})$", message="Invalid phone/fax format, should be as xxx-xxx-xxxx")//if the field contains phone or fax number consider using this annotation to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 11)
    @Column(name = "phone")
    @JsonView(DisplayView.Internal.class)
    protected String phone;
    @Basic(optional = false)
    @Size(min = 1, max = 50)
    @Column(name = "user_role")
    @JsonView(DisplayView.Public.class)
    private String userRole;

    public UserInfo() {
    }

    public UserInfo(Long id) {
        this.id = id;
    }

    public UserInfo(Long id, boolean active, Date createdDate, Date updatedDate, String firstName, String lastName, String username, String password, String avatar, String email, String phone) {
        this.id = id;
        this.active = active;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.avatar = avatar;
        this.email = email;
        this.phone = phone;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
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
        if (!(object instanceof UserInfo)) {
            return false;
        }
        UserInfo other = (UserInfo) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.nhm.pojo.UserInfo[ id=" + id + " ]";
    }
    
    public static enum UserRole{
        ROLE_STUDENT,
        ROLE_ASSISTANT,
        ROLE_AFFAIRS
    }
    
}