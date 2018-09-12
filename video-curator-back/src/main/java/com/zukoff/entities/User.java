package com.zukoff.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "users")
public class User implements UserDetails {
    private int id;
    private int version;
    private String username;
    private String password;
    private boolean enabled;
    private List<Role> roles;
    private Date created;
    private Date modified;
    private List<Video> videos;
    private List<Comment> comments;

    public User() {
        System.out.println("USER DEFAULT CONSTRUCTOR");
    }

    public User(String username) {
        System.out.println("USER CONSTRUCTOR");
        this.username = username;
    }

    @Id
    @GeneratedValue
    public int getId() {
        System.out.println("USER GET ID");
        return id;
    }
    public void setId(int id) {
        System.out.println("USER SET ID");
        this.id = id;
    }

    @Version
    public int getVersion() {
        return version;
    }
    public void setVersion(int version) {
        this.version = version;
    }

    @Size(min = 5)
    @NotNull
    @Column(unique=true)
    public String getUsername() {
        System.out.println("USER GET USERNAME");
        return username;
    }
    public void setUsername(String username) {
        System.out.println("USER SET USERNAME");
        this.username = username;
    }

    @Column(nullable = false)
    @JsonIgnore
    public String getPassword() {
        System.out.println("USER GET PASSWORD");
        return password;
    }
    public void setPassword(String password) {
        System.out.println("USER SET PASSWORD");
        this.password = password;
    }

    @CreationTimestamp
    public Date getCreated() {
        return created;
    }
    public void setCreated(Date created) {
        this.created = created;
    }

    @UpdateTimestamp
    public Date getModified() {
        return modified;
    }
    public void setModified(Date modified) {
        this.modified = modified;
    }

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "roles_users",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    public List<Role> getRoles() {
        System.out.println("USER GET ROLES");
        return roles;
    }
    public void setRoles(List<Role> roles) {
        System.out.println("USER SET ROLES");
        this.roles = roles;
    }

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    @JsonIgnore
    public List<Video> getVideos() {
        return videos;
    }
    public void setVideos(List<Video> videos) {
        this.videos = videos;
    }

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    @JsonIgnore
    public List<Comment> getComments() {
        return comments;
    }
    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    @Override
    @Transient
    public Collection<? extends GrantedAuthority> getAuthorities() {
        System.out.println("USER GET AUTHORITIES");
        return this.getRoles();
    }

    @Override
    @Transient
    public boolean isAccountNonExpired() {
        System.out.println("USER IS ACCOUNT NON EXPIRED");
        return this.isEnabled();
    }

    @Override
    @Transient
    public boolean isAccountNonLocked() {
        System.out.println("USER IS ACCOUNT NON LOCKED");
        return this.isEnabled();
    }

    @Override
    @Transient
    public boolean isCredentialsNonExpired() {
        System.out.println("USER IS CREDENTIALS NON EXPIRED");
        return this.isEnabled();
    }

    @Override
    public boolean isEnabled() {
        System.out.println("USER IS ENABLED");
        return this.enabled;
    }
    public void setEnabled(boolean enabled) {
        System.out.println("USER SET ENABLED");
        this.enabled = enabled;
    }


}
