package edu.neu.csye6200.service.impl;

import edu.neu.csye6200.model.Expense;
import edu.neu.csye6200.repository.GroupRepository;
import edu.neu.csye6200.repository.UserRepository;
import edu.neu.csye6200.service.ExpenseCSVService;
import edu.neu.csye6200.service.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Service
public class ExpenseCSVServiceImpl implements ExpenseCSVService {

    @Autowired
    private ExpenseService expenseService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GroupRepository groupRepository;

    @Override
    public void addCSVExpenseEqual(List<String> dataList) {

        List<Expense> expenses = new ArrayList<>();

        dataList.forEach(data -> {
            String[] details = data.split(",");
            Expense expense = new Expense();
            expense.setDescription(details[0]);
            expense.setAmount(Double.parseDouble(details[1]));
            expense.setPayer(userRepository.findByName(details[2]));
            expense.setGroup(groupRepository.findByGroupName(details[3]));
            expense.setDate(Date.from(Instant.now()));

            expenses.add(expense);
        });

        // load all the expenses from the list
        expenses.forEach(expense -> expenseService.createExpenseEqually(expense));
    }
}
