package io.odabas.ppmtool.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.Collection;
import java.util.Date;

@Entity
@Getter
@Setter
public class User  implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id ;
    @Email(message = "username needs to be an email")
    @NotBlank(message =  "Username is required")
    @Column(unique=true)
    private  String username;
    @NotBlank(message = "Please enter your fullname")
    private  String fullName;
    @NotBlank(message = "Please enter your password")
    private String password;
    @Transient
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String confirmPassword;
    private Date created_At;
    private Date updated_At;

    @PrePersist
    protected  void onCreate() {this.created_At = new Date();}
    @PreUpdate
    protected  void onUpdate() {this.updated_At = new Date();}


    //OnetoMany with project

    public User (){

    }



/*
* UserDetails Interface Method
* */

    @Override
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isCredentialsNonExpired() {
        return true;
    }


    @Override
    @JsonIgnore
    public boolean isEnabled() {
        return true;
    }
}