/*
 * Software: SpringOauth2Server REST client for user interface
 * Module: GroupService class
 * Version: 0.1
 * Licence: GPL2
 * Owner: Kim Kristo
 * Date creation : 18.7.2017
 */
package oh3ebf.spring.security.oauth.user_interface.services;

import com.sun.javafx.scene.control.skin.VirtualFlow;
import java.util.ArrayList;
import java.util.List;

import oh3ebf.spring.security.oauth.user_interface.model.GroupMembers;
import oh3ebf.spring.security.oauth.user_interface.model.Groups;
import oh3ebf.spring.security.oauth.user_interface.model.Users;

import oh3ebf.spring.security.oauth.user_interface.repository.GroupMembersRepository;
import oh3ebf.spring.security.oauth.user_interface.repository.GroupsRepository;
import oh3ebf.spring.security.oauth.user_interface.utils.Utils;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GroupService implements GroupServiceIF {

    @Autowired
    GroupsRepository groupRepository;

    @Autowired
    GroupMembersRepository groupMembersRepository;

    /**
     * Function return all groups
     * 
     * @return groups as list
     */
    @Override
    public List<Groups> getAllGroups() {
        Iterable<Groups> groups = groupRepository.findAll();
        return Utils.IterableToList(groups);
    }

    /**
     * Function return specified group
     * 
     * @param id of group
     * @return group
     */
    @Override
    public Groups getGroupById(Long id) {
        return groupRepository.findOne(id);
    }

    /**
     * Function return specified group
     * 
     * @param name of group
     * @return group
     */
    @Override
    public Groups getGroupByName(String name) {
        return groupRepository.findByGroupName(name);
    }

    /**
     * Function save group
     * 
     * @param group to store
     * @return stored group object
     */
    @Override
    public Groups saveGroup(Groups group) {
        return groupRepository.save(group);
    }

    /**
     * Function remove specified group
     * 
     * @param id of group to delete
     */
    @Override
    public void deleteGroup(Long id) {
        groupRepository.delete(id);
    }

    /**
     * Function return users belonging to group
     * 
     * @param id of group
     * @return users as list
     */
    @Override
    public List<Users> getGroupUsers(Long id) {
        List<Users> users;

        Groups g = groupRepository.findOne(id);

        try {
            Hibernate.initialize(users = g.getUsers());
        } catch (NullPointerException ex) {
            users = new ArrayList<>();
        }

        return users;
    }

    /**
     * Function adds new user to group
     * 
     * @param user to add
     * @param group to add
     */
    @Override
    public void addUserToGroup(Users user, Groups group) {
        GroupMembers members = new GroupMembers();
        members.setGroupsId(group);
        members.setUsersId(user);
        
        groupMembersRepository.save(members);        
    }

    /**
     * Function remove user from group
     * 
     * @param user to remove
     * @param group where user is removed
     */
    @Override
    public void deleteUserFromGroup(Users user, Groups group) {
        GroupMembers member = groupMembersRepository.findByGroupsIdAndUsersId(group, user);
        
        groupMembersRepository.delete(member);
    }
}
