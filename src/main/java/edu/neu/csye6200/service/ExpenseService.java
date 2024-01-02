package edu.neu.csye6200.service;

import edu.neu.csye6200.dto.ExpenseUnequal;
import edu.neu.csye6200.model.Expense;

import java.util.List;

public interface ExpenseService {
    Expense getExpenseById(int expenseId);

    void createExpenseEqually(Expense expense);

    void createExpenseUnequally(ExpenseUnequal expenseUnequal);

    Expense updateExpense(int expenseId, Expense expense);

    void deleteExpense(int expenseId);

    List<Expense> getAllExpensesByGroupId(int groupId);
}
