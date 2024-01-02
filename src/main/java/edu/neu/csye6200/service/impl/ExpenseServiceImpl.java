package edu.neu.csye6200.service.impl;

import edu.neu.csye6200.dto.ExpenseUnequal;
import edu.neu.csye6200.model.Debt;
import edu.neu.csye6200.model.Expense;
import edu.neu.csye6200.model.User;
import edu.neu.csye6200.repository.DebtRepository;
import edu.neu.csye6200.repository.ExpenseRepository;
import edu.neu.csye6200.repository.UserGroupRepository;
import edu.neu.csye6200.repository.UserRepository;
import edu.neu.csye6200.service.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ExpenseServiceImpl implements ExpenseService {

    @Autowired
    private ExpenseRepository expenseRepository;

    @Autowired
    private UserGroupRepository userGroupRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DebtRepository debtRepository;

    @Override
    public Expense getExpenseById(int expenseId) {
        return expenseRepository.findById(expenseId).orElse(null);
    }

    @Override
    public void createExpenseEqually(Expense expense) {
        // Implement validation or additional logic as needed
        // make the changes to add the expense to the users in the group and split the expense among the group members
        int payerId = expense.getPayer().getId();
        List<Integer> userIds = userGroupRepository.getByGroupId(expense.getGroup().getId())
                .stream().map(group -> group.getUserGroupId().getUserId())
                .collect(Collectors.toList());
        int group_size = userIds.size();

        userIds.removeIf(x -> x.equals(payerId));

        // fetch all users in the group
        List<User> users = userRepository.findAllById(userIds);

        // get the individual amount for all users in the expense
        double ind_amount = expense.getAmount() / group_size;

        Expense expense1 = expenseRepository.save(expense);

        List<Debt> debtList = new ArrayList<>();
        Optional<User> lender = userRepository.findById(payerId);
        users.forEach(user -> {
            Optional<User> borrower = userRepository.findById(user.getId());

            Debt debt = Debt.builder()
                    .lender(lender.get())
                    .borrower(borrower.get())
                    .amount(ind_amount)
                    .group(expense1.getGroup())
                    .expense(expense1)
                    .build();
            debtList.add(debt);
        });

        // update the debt repository
        debtRepository.saveAll(debtList);
    }

    @Override
    public void createExpenseUnequally(ExpenseUnequal expense) {
        int payerId = expense.getPayer().getId();
        List<Integer> userIds = userGroupRepository.getByGroupId(expense.getGroup().getId())
                .stream().map(group -> group.getUserGroupId().getUserId())
                .collect(Collectors.toList());

        userIds.removeIf(x -> x.equals(payerId));

        // fetch all users in the group
        List<User> users = userRepository.findAllById(userIds);

        // map user email by userId
        Map<String, Integer> userIdByUserEmailMap = users.stream()
                .collect(Collectors.toMap(User::getEmail, User::getId));

        Map<Integer, Double> amountByUserIdMap = new HashMap<>();
        Optional<User> lender = userRepository.findById(payerId);

        // update the map with amount and userId
        expense.getBorrower().forEach(borrowerAmount ->
                amountByUserIdMap.put(
                        userIdByUserEmailMap.get(borrowerAmount.getUser().getEmail()),
                        borrowerAmount.getAmount())
        );
        Expense expense1 = Expense.builder()
                .description(expense.getDescription())
                .amount(expense.getAmount())
                .payer(expense.getPayer())
                .group(expense.getGroup())
                .date(expense.getDate())
                .build();
        expenseRepository.save(expense1);


        List<Debt> debtList = new ArrayList<>();
        users.forEach(user -> {
            Optional<User> borrower = userRepository.findById(user.getId());
            Debt debt = Debt.builder()
                    .lender(lender.get())
                    .borrower(borrower.get())
                    .amount(amountByUserIdMap.get(borrower.get().getId()))
                    .group(expense1.getGroup())
                    .expense(expense1)
                    .build();
            debtList.add(debt);
        });

        // update the debt repository
        debtRepository.saveAll(debtList);
    }

    @Override
    public Expense updateExpense(int expenseId, Expense expense) {
        // Implement validation or additional logic as needed
        expense.setId(expenseId);
        return expenseRepository.save(expense);
    }

    @Override
    public void deleteExpense(int expenseId) {
        expenseRepository.deleteById(expenseId);
    }

    @Override
    public List<Expense> getAllExpensesByGroupId(int groupId) {
        return expenseRepository.findAllByGroupId(groupId);
    }
}
