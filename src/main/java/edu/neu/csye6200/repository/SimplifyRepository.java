package edu.neu.csye6200.repository;

import edu.neu.csye6200.model.Simplify;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;

public interface SimplifyRepository extends JpaRepository<Simplify, Integer> {

    @Query("DELETE FROM Simplify s1 WHERE s1.id IN " +
            "(SELECT s2.id FROM Simplify s2 " +
            "WHERE s1.lender.id = s2.lender.id AND " +
            "s1.borrower.id = s2.borrower.id AND " +
            "s1.amount = s2.amount AND " +
            "s1.group.id = s2.group.id AND " +
            "s1.id > s2.id)")
    void deleteDuplicateEntries();

    @Transactional
    @Modifying
    @Query("DELETE FROM Simplify s WHERE s.group.id = :groupId")
    void deleteByGroupId(@Param("groupId") int groupId);
}
