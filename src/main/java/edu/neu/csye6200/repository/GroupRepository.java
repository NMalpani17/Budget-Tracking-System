package edu.neu.csye6200.repository;

import edu.neu.csye6200.model.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupRepository extends JpaRepository<Group, Integer> {

    @Query(value = "SELECT g FROM Group g WHERE g.name = :groupName")
    Group findByGroupName(@Param("groupName") String groupName);
}
