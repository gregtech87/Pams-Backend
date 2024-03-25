package com.example.pamsbackend.entity;

import java.util.*;

import com.example.pamsbackend.PdfUserInfo.PdfUser;
import com.example.pamsbackend.dao.BinaryDeserializer;
import com.example.pamsbackend.dao.BinarySerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.bson.types.Binary;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


@Document(collection = "users")
public class User implements UserDetails {

    @Id
    private String id;
    private String email;
    @JsonSerialize(using = BinarySerializer.class)
    @JsonDeserialize(using = BinaryDeserializer.class)
    private Binary profilePic;
    private PictureData profilePictureData;
    private String firstName;
    private String lastName;
    private String phone;
    private String dateOfBirth;
    private Address address;
    private boolean customLocation = false;
    private float customLat;
    private float customLong;
    private List<String> notes = new ArrayList<>();
    private List<String> items = new ArrayList<>();
    private List<String> personalFiles = new ArrayList<>();
    private String username;
    private String password;
    private String role;
    private Boolean locked = false;
    private Boolean enabled = false;
    private ConfirmationToken confirmationToken;
    private PdfUser pdfUser;
    private long mbOfStorage = 200;
    private long usedStorage;


    public User() {
    }

    public User(String email, String firstName, String lastName, String password, String role) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        SimpleGrantedAuthority authority =
                new SimpleGrantedAuthority(role);
        return Collections.singletonList(authority);
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !locked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
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

    public Binary getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(Binary profilePic) {
        this.profilePic = profilePic;
    }

    public PictureData getProfilePictureData() {
        return profilePictureData;
    }

    public void setProfilePictureData(PictureData profilePictureData) {
        this.profilePictureData = profilePictureData;
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

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Boolean getLocked() {
        return locked;
    }

    public void setLocked(Boolean locked) {
        this.locked = locked;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public ConfirmationToken getConfirmationToken() {
        return confirmationToken;
    }

    public void setConfirmationToken(ConfirmationToken confirmationToken) {
        this.confirmationToken = confirmationToken;
    }

    public List<String> getNotes() {
        return notes;
    }

    public void setNotes(List<String> notes) {
        this.notes = notes;
    }

    public boolean isCustomLocation() {
        return customLocation;
    }

    public void setCustomLocation(boolean customLocation) {
        this.customLocation = customLocation;
    }

    public float getCustomLat() {
        return customLat;
    }

    public void setCustomLat(float customLat) {
        this.customLat = customLat;
    }

    public float getCustomLong() {
        return customLong;
    }

    public void setCustomLong(float customLong) {
        this.customLong = customLong;
    }

    public PdfUser getPdfUser() {
        return pdfUser;
    }

    public void setPdfUser(PdfUser pdfUser) {
        this.pdfUser = pdfUser;
    }

    public long getMbOfStorage() {
        return mbOfStorage;
    }

    public void setMbOfStorage(long mbOfStorage) {
        this.mbOfStorage = mbOfStorage;
    }

    public List<String> getPersonalFiles() {
        return personalFiles;
    }

    public void setPersonalFiles(List<String> personalFiles) {
        this.personalFiles = personalFiles;
    }

    public long getUsedStorage() {
        return usedStorage;
    }

    public void setUsedStorage(long usedStorage) {
        this.usedStorage = usedStorage;
    }

    public List<String> getItems() {
        return items;
    }

    public void setItems(List<String> items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", email='" + email + '\'' +
                ", profilePic=" + profilePic +
                ", profilePictureData=" + profilePictureData +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", phone='" + phone + '\'' +
                ", dateOfBirth='" + dateOfBirth + '\'' +
                ", address=" + address +
                ", customLocation=" + customLocation +
                ", customLat=" + customLat +
                ", customLong=" + customLong +
                ", notes=" + notes +
                ", items=" + items +
                ", personalFiles=" + personalFiles +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", role='" + role + '\'' +
                ", locked=" + locked +
                ", enabled=" + enabled +
                ", confirmationToken=" + confirmationToken +
                ", pdfUser=" + pdfUser +
                ", mbOfStorage=" + mbOfStorage +
                ", usedStorage=" + usedStorage +
                '}';
    }
}