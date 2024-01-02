package edu.neu.csye6200.repository;

import edu.neu.csye6200.model.ExpenseImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ExpenseImageRepository extends JpaRepository<ExpenseImage, Integer> {

    @Query(value = "SELECT ei FROM ExpenseImage ei WHERE ei.expense.id = :expense_id")
    Optional<ExpenseImage> findByExpenseId(@Param("expense_id") int expenseId);
}
