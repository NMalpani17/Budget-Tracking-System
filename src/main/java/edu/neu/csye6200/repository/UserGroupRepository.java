package edu.neu.csye6200.repository;

import edu.neu.csye6200.model.UserGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface UserGroupRepository extends JpaRepository<UserGroup, UserGroup.UserGroupId> {

    @Modifying
    @Query("DELETE FROM UserGroup ug WHERE ug.userGroupId.userId = :userId AND ug.userGroupId.groupId = :groupId")
    void deleteByUserIdAndGroupId(int userId, int groupId);

    @Query("SELECT ug FROM UserGroup ug WHERE ug.userGroupId.groupId = :groupId")
    List<UserGroup> getByGroupId(@Param("groupId") int groupId);

    @Transactional
    @Modifying
    @Query("DELETE FROM UserGroup ug WHERE ug.userGroupId.groupId = :groupId")
    void deleteByGroupId(@Param("groupId") int groupId);
}
