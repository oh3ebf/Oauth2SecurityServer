/**
 * Software: SpringOauth2Server REST client for user interface
 * Module: Groups class
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
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "groups")
public class Groups implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "group_name")
    private String groupName;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "description")
    private String description;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "authority")
    private String authority;

    @ManyToMany(mappedBy = "groups", fetch = FetchType.LAZY)
    @JsonBackReference(value = "groups")
    private List<Users> users;

    public Groups() {
    }

    public Groups(Long id) {
        this.id = id;
    }

    public Groups(Long id, String groupName) {
        this.id = id;
        this.groupName = groupName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /*
    public List<GroupMembers> getGroupMembers() {
        return groupMembers;
    }

    public void setGroupMembers(List<GroupMembers> groupMembersCollection) {
        this.groupMembers = groupMembersCollection;
    }

    public List<GroupAuthorities> getGroupAuthorities() {
        return groupAuthorities;
    }

    public void setGroupAuthorities(List<GroupAuthorities> groupAuthoritiesCollection) {
        this.groupAuthorities = groupAuthoritiesCollection;
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
        if (!(object instanceof Groups)) {
            return false;
        }
        Groups other = (Groups) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "oh3ebf.spring.security.oauth.user_interface.model.Groups[ id=" + id + " ]";
    }

    /**
     * @return the users
     */
    public List<Users> getUsers() {
        return users;
    }

    /**
     * @param users the users to set
     */
    public void setUsers(List<Users> users) {
        this.users = users;
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }
}
