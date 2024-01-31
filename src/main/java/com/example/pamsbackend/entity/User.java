package com.example.pamsbackend.entity;

import java.util.List;
import java.util.Set;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.IndexDirection;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

/**
 *
 * @author didin
 */
@Document(collection = "users")
public class User {

    @Id
    private String id;
    @Indexed(unique = true, direction = IndexDirection.DESCENDING)
    private String email;
    private String password;
    private String username;
    private String fullname;
    private String authority;
    private boolean enabled;
    @DBRef
    private Set<Role> roles;
    List<SimpleGrantedAuthority> dummyAuthorityForExample;

    public User(String password, String username, List<SimpleGrantedAuthority> dummyAuthorityForExample) {
        this.password = password;
        this.username = username;
        this.dummyAuthorityForExample = dummyAuthorityForExample;
    }

    public String getId() {
        return id;
    }

    public String getUserName() {
        return username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<SimpleGrantedAuthority> getDummyAuthorityForExample() {
        return dummyAuthorityForExample;
    }

    public void setDummyAuthorityForExample(List<SimpleGrantedAuthority> dummyAuthorityForExample) {
        this.dummyAuthorityForExample = dummyAuthorityForExample;
    }

    public void setUserName(String userName) {
        this.username = userName;
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", username='" + username + '\'' +
                ", fullname='" + fullname + '\'' +
                ", authority='" + authority + '\'' +
                ", enabled=" + enabled +
                ", roles=" + roles +
                ", dummyAuthorityForExample=" + dummyAuthorityForExample +
                '}';
    }
}
