/**
 * Software:
 * Module: GroupAuthorities class
 * Version: 0.1
 * Licence: GPL2
 * Owner: Kim Kristo
 * Date creation : 4.9.2017
 */
package oh3ebf.spring.security.oauth.user_interface.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


@Entity
@Table(name = "group_authorities")
public class GroupAuthorities implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "authority")
    private String authority;
    @JsonBackReference
    @JoinColumn(name = "groups_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_group_authorities_groups1"))
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Groups groupsId;

    public GroupAuthorities() {
    }

    public GroupAuthorities(Long id) {
        this.id = id;
    }

    public GroupAuthorities(Long id, String authority) {
        this.id = id;
        this.authority = authority;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    public Groups getGroupsId() {
        return groupsId;
    }

    public void setGroupsId(Groups groupsId) {
        this.groupsId = groupsId;
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
        if (!(object instanceof GroupAuthorities)) {
            return false;
        }
        GroupAuthorities other = (GroupAuthorities) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "oh3ebf.spring.security.oauth.user_interface.model.GroupAuthorities[ id=" + id + " ]";
    }
    
}
