package com.zukoff.entities;

import com.zukoff.enums.RoleEnum;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "roles")
public class Role implements GrantedAuthority {
    private int id;
    private int version;
    private RoleEnum role;
    private Date created;
    private Date modified;

    public Role() {
    }

    public Role(RoleEnum role) {
        this.role = role;
    }

    @Id
    @GeneratedValue
    public int getId() {return id;}
    public void setId(int id) {this.id = id;}

    @Version
    public int getVersion() {return version;}
    public void setVersion(int version) {this.version = version;}

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, columnDefinition = "ENUM('USER', 'ADMIN')")
    public RoleEnum getRole() {return role;}
    public void setRole(RoleEnum role) {this.role = role;}

    @CreationTimestamp
    public Date getCreated() {return created;}
    public void setCreated(Date created) {this.created = created;}

    @UpdateTimestamp
    public Date getModified() {return modified;}
    public void setModified(Date modified) {this.modified = modified;}

    @Override
    @Transient
    public String getAuthority() {return this.role.toString();}
    public void setAuthority(String role) {this.role = RoleEnum.valueOf(role);}


}