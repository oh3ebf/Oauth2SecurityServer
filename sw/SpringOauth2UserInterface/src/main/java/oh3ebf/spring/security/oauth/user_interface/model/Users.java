/**
 * Software: SpringOauth2Server REST client for user interface
 * Module: Users class
 * Version: 0.1
 * Licence: GPL2
 * Owner: Kim Kristo
 * Date creation : 27.9.2017
 */
package oh3ebf.spring.security.oauth.user_interface.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "users")
public class Users implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "username")
    private String username;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "password")
    private String password;
    @Basic(optional = false)
    @NotNull
    @Column(name = "enabled")
    private short enabled;
    @Basic(optional = false)
    @NotNull
    @Column(name = "account_non_expired")
    private short accountNonExpired;
    @Basic(optional = false)
    @NotNull
    @Column(name = "account_non_locked")
    private short accountNonLocked;
    @Basic(optional = false)
    @NotNull
    @Column(name = "credentials_non_expired")
    private short credentialsNonExpired;
    @Size(max = 45)
    @Column(name = "first_name")
    private String firstName;
    @Size(max = 45)
    @Column(name = "last_name")
    private String lastName;
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    @Size(max = 45)
    @Column(name = "email")
    private String email;
    // @Pattern(regexp="^\\(?(\\d{3})\\)?[- ]?(\\d{3})[- ]?(\\d{4})$", message="Invalid phone/fax format, should be as xxx-xxx-xxxx")//if the field contains phone or fax number consider using this annotation to enforce field validation
    @Size(max = 45)
    @Column(name = "phone")
    private String phone;
    @Size(max = 255)
    @Column(name = "image")
    private String image;
    @Size(max = 255)
    @Column(name = "avatar_image")
    private String avatarImage;

    @JsonBackReference(value = "group-members")
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "group_members", joinColumns = @JoinColumn(name = "users_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "groups_id", referencedColumnName = "id"))
    private List<Groups> groups;

    @JsonBackReference(value = "user-attempts")
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "usersId", fetch = FetchType.LAZY)
    private List<UserAttempts> userAttempts;
    @JsonBackReference(value = "user-authorities")
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "usersId", fetch = FetchType.LAZY)
    private List<UserAuthorities> userAuthorities;

    public Users() {
    }

    public Users(Long id) {
        this.id = id;
    }

    public Users(Long id, String username, String password, short enabled, short accountNonExpired, short accountNonLocked, short credentialsNonExpired) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.enabled = enabled;
        this.accountNonExpired = accountNonExpired;
        this.accountNonLocked = accountNonLocked;
        this.credentialsNonExpired = credentialsNonExpired;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public short getEnabled() {
        return enabled;
    }

    public void setEnabled(short enabled) {
        this.enabled = enabled;
    }

    public short getAccountNonExpired() {
        return accountNonExpired;
    }

    public void setAccountNonExpired(short accountNonExpired) {
        this.accountNonExpired = accountNonExpired;
    }

    public short getAccountNonLocked() {
        return accountNonLocked;
    }

    public void setAccountNonLocked(short accountNonLocked) {
        this.accountNonLocked = accountNonLocked;
    }

    public short getCredentialsNonExpired() {
        return credentialsNonExpired;
    }

    public void setCredentialsNonExpired(short credentialsNonExpired) {
        this.credentialsNonExpired = credentialsNonExpired;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getAvatarImage() {
        return avatarImage;
    }

    public void setAvatarImage(String avatarImage) {
        this.avatarImage = avatarImage;
    }

    /*
    public List<GroupMembers> getGroupMembers() {
        return groupMembers;
    }

    public void setGroupMembers(List<GroupMembers> groupMembers) {
        this.groupMembers = groupMembers;
    }

    public List<UserAttempts> getUserAttempts() {
        return userAttempts;
    }

    public void setUserAttempts(List<UserAttempts> userAttempts) {
        this.userAttempts = userAttempts;
    }   

    public List<UserAuthorities> getUserAuthorities() {
        return userAuthorities;
    }

    public void setUserAuthorities(List<UserAuthorities> userAuthorities) {
        this.userAuthorities = userAuthorities;
    }
     */
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Users)) {
            return false;
        }
        Users other = (Users) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "oh3ebf.spring.security.oauth.user_interface.model.Users[ id=" + id + " ]";
    }

    /**
     * @return the groups
     */
    public List<Groups> getGroups() {
        return groups;
    }

    /**
     * @param groups the groups to set
     */
    public void setGroups(List<Groups> groups) {
        this.groups = groups;
    }

    /**
     * @return the userAttempts
     */
    public List<UserAttempts> getUserAttempts() {
        return userAttempts;
    }

    /**
     * @param userAttempts the userAttempts to set
     */
    public void setUserAttempts(List<UserAttempts> userAttempts) {
        this.userAttempts = userAttempts;
    }

    /**
     * @return the userAuthorities
     */
    public List<UserAuthorities> getUserAuthorities() {
        return userAuthorities;
    }

    /**
     * @param userAuthorities the userAuthorities to set
     */
    public void setUserAuthorities(List<UserAuthorities> userAuthorities) {
        this.userAuthorities = userAuthorities;
    }

}
