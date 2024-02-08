package com.example.pamsbackend.entity;

import java.util.List;
import java.util.Set;

import com.example.pamsbackend.dao.BinaryDeserializer;
import com.example.pamsbackend.dao.BinarySerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.bson.types.Binary;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.authority.SimpleGrantedAuthority;


//@JsonInclude(JsonInclude.Include.NON_NULL)
@Document(collection = "users")
public class User {

    @Id
    private String id;
    //    @Indexed(unique = true, direction = IndexDirection.DESCENDING)
    private String email;
    //    @Indexed(unique = true, direction = IndexDirection.DESCENDING)
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String phone;
    private String dateOfBirth;
    private Address address;
    private PictureData profilePictureData;

    @JsonSerialize(using = BinarySerializer.class)
    @JsonDeserialize(using = BinaryDeserializer.class)
    private Binary profilePic;
    private Set<Role> roles;
    List<SimpleGrantedAuthority> forcedAuthVariable;

    public User() {
    }

    public User(String password, String username, List<SimpleGrantedAuthority> forcedAuthVariable) {
        this.password = password;
        this.username = username;
        this.forcedAuthVariable = forcedAuthVariable;
    }

    public String getId() {
        return id;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public List<SimpleGrantedAuthority> getForcedAuthVariable() {
        return forcedAuthVariable;
    }

    public void setForcedAuthVariable(List<SimpleGrantedAuthority> forcedAuthVariable) {
        this.forcedAuthVariable = forcedAuthVariable;
    }

    public PictureData getProfilePictureData() {
        return profilePictureData;
    }

    public void setProfilePictureData(PictureData profilePictureData) {
        this.profilePictureData = profilePictureData;
    }

    public Binary getProfilePic() {
        return profilePic;
    }

    //
    public void setProfilePic(Binary profilePic) {
        this.profilePic = profilePic;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", email='" + email + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", phone='" + phone + '\'' +
                ", dateOfBirth='" + dateOfBirth + '\'' +
                ", address=" + address +
                ", profilePictureData=" + profilePictureData +
                ", profilePic=" + profilePic +
                ", roles=" + roles +
                ", forcedAuthVariable=" + forcedAuthVariable +
                '}';
    }
}
