/*
 * Software: 
 * Module: UserRepository class
 * Version: 0.1
 * Licence: GPL2
 * Owner: Kim Kristo
 * Date creation : 14.7.2017
 */
package oh3ebf.spring.security.oauth.user_interface.repository;

import oh3ebf.spring.security.oauth.user_interface.model.GroupMembers;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface GroupMembersRepository extends CrudRepository<GroupMembers, Long> {

}
