/*
 * Software: 
 * Module: GroupService class
 * Version: 0.1
 * Licence: GPL2
 * Owner: Kim Kristo
 * Date creation : 18.7.2017
 */
package oh3ebf.spring.security.oauth.user_interface.services;

import java.util.List;
import oh3ebf.spring.security.oauth.user_interface.model.GroupAuthorities;
import oh3ebf.spring.security.oauth.user_interface.model.GroupMembers;
import oh3ebf.spring.security.oauth.user_interface.model.Groups;
import oh3ebf.spring.security.oauth.user_interface.model.Users;
import oh3ebf.spring.security.oauth.user_interface.repository.GroupAuthoritiesRepository;
import oh3ebf.spring.security.oauth.user_interface.repository.GroupMembersRepository;
import oh3ebf.spring.security.oauth.user_interface.repository.GroupsRepository;
import oh3ebf.spring.security.oauth.user_interface.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GroupService implements GroupServiceIF {

    @Autowired
    GroupsRepository groupRepository;

    @Autowired
    GroupAuthoritiesRepository groupAuthoritiesRepository;

    @Autowired
    GroupMembersRepository groupMembersRepository;

    @Override
    public List<Groups> getAllGroups() {
        Iterable<Groups> groups = groupRepository.findAll();
        return Utils.IterableToList(groups);
    }

    @Override
    public Groups getGroupById(Long id) {
        return groupRepository.findOne(id);
    }

    @Override
    public Groups getGroupByName(String name) {
        return groupRepository.findByGroupName(name);
    }

    @Override
    public void saveGroup(Groups group) {

        groupRepository.save(group);
    }

    @Override
    public void deleteGroup(Long id) {
        groupRepository.delete(id);
    }

    public GroupAuthorities getGroupAuthorities(Long groupId) {
        return groupAuthoritiesRepository.findOne(groupId);
    }

    public void addUserToGroup(Users user, Groups group) {
        GroupMembers members = new GroupMembers();
        members.setGroupsId(group);
        members.setUsersId(user);
        //groupMembersRepository.save(members);        
    }

    public void deleteUserFromGroup(Users user, Groups group) {
        ///Groups g = groupRepository.findByGroupName("oauth2_users");
        /*
        List<GroupMembers> members = group.getGroupMembers();
        if (members.get(0).getUsersId().equals(user)) {
            int a = 0;
            //groupMembersRepository.delete(m);
        }
         */
    }

    public void p() {
        groupMembersRepository.findOne(1L);
    }
}
