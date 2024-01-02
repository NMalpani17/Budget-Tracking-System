package edu.neu.csye6200.controller;

import edu.neu.csye6200.dto.ExpenseUnequal;
import edu.neu.csye6200.model.Expense;
import edu.neu.csye6200.service.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/expenses")
@CrossOrigin(origins = "http://localhost:3000")
public class ExpenseController {

    @Autowired
    private ExpenseService expenseService;

    @GetMapping("/{expenseId}")
    public Expense getExpenseById(@PathVariable int expenseId) {
        return expenseService.getExpenseById(expenseId);
    }

    @PostMapping
    public void createExpenseEqually(@RequestBody Expense expense) {
        expenseService.createExpenseEqually(expense);
    }

    @PostMapping("/unequal")
    public void createExpenseUnequally(@RequestBody ExpenseUnequal unequalExpense) {
        expenseService.createExpenseUnequally(unequalExpense);
    }

    @PutMapping("/{expenseId}")
    public Expense updateExpense(@PathVariable int expenseId, @RequestBody Expense expense) {
        return expenseService.updateExpense(expenseId, expense);
    }

    @DeleteMapping("/{expenseId}")
    public void deleteExpense(@PathVariable int expenseId) {
        expenseService.deleteExpense(expenseId);
    }

    @GetMapping("/groupId/{groupId}")
    public List<Expense> getAllExpensesByGroupId(@PathVariable int groupId){
        return expenseService.getAllExpensesByGroupId(groupId);
    }
}
