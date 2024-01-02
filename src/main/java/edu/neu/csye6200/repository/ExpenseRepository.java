package edu.neu.csye6200.repository;

import edu.neu.csye6200.model.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Integer> {

    @Query("SELECT e from Expense e WHERE e.group.id = :groupId")
    List<Expense> findAllByGroupId(@Param("groupId") int groupId);

    @Transactional
    @Modifying
    @Query("DELETE FROM Expense e WHERE e.group.id = :groupId")
    void deleteByGroupId(@Param("groupId") int groupId);
}
