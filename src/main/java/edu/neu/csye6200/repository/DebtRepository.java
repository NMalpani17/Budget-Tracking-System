package edu.neu.csye6200.repository;

import edu.neu.csye6200.model.Debt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface DebtRepository extends JpaRepository<Debt, Integer> {

    @Query("SELECT d from Debt d WHERE d.group.id = :groupId")
    List<Debt> findAllByGroupId(@Param("groupId") int groupId);

    @Transactional
    @Modifying
    @Query("DELETE FROM Debt d WHERE d.group.id = :groupId")
    void deleteByGroupId(@Param("groupId") int groupId);

/*    @Query(value = "SELECT new edu.neu.csye6200.fbta.dto.DebtSimplify(d.borrower.id, d.lender.id, SUM(d.amount) AS total_amount) FROM Debt d where d.group.id = :groupId GROUP BY d.borrower.id, d.lender.id")
    List<DebtSimplify> findAllDebtGroupByLenderAndBorrowerById(@Param("groupId") int groupId);*/
}
