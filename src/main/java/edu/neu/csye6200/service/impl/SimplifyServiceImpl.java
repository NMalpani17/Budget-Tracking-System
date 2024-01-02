package edu.neu.csye6200.service.impl;

import edu.neu.csye6200.dto.DebtSimplify;
import edu.neu.csye6200.model.Debt;
import edu.neu.csye6200.model.Group;
import edu.neu.csye6200.model.Simplify;
import edu.neu.csye6200.model.User;
import edu.neu.csye6200.repository.DebtRepository;
import edu.neu.csye6200.repository.SimplifyRepository;
import edu.neu.csye6200.service.GroupService;
import edu.neu.csye6200.service.SimplifyService;
import edu.neu.csye6200.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SimplifyServiceImpl implements SimplifyService {


    @Autowired
    private GroupService groupService;
    @Autowired
    private SimplifyRepository simplifyRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private DebtRepository debtRepository;

    @Override
    public List<Simplify> simplify(int groupId) {
        List<Debt> debtList = debtRepository.findAllByGroupId(groupId);
        List<DebtSimplify> debtSimplifies = new ArrayList<>();
        List<Simplify> simplifyList = new ArrayList<>();

        for (Debt debt : debtList) {
            boolean debtAdded = false;

            for (DebtSimplify debtSimplify : debtSimplifies) {
                if ((debtSimplify.getLenderId() == debt.getLender().getId() &&
                        debtSimplify.getBorrowerId() == debt.getBorrower().getId()) ||
                        (debtSimplify.getLenderId() == debt.getBorrower().getId() &&
                                debtSimplify.getBorrowerId() == debt.getLender().getId())) {
                    double amount = debtSimplify.getAmount() + (debtSimplify.getLenderId() == debt.getLender().getId() ? debt.getAmount() : -debt.getAmount());
                    debtSimplify.setAmount(amount);
                    debtAdded = true;
                    break;
                }
            }

            if (!debtAdded) {
                DebtSimplify debtSimplifyObject = DebtSimplify.builder()
                        .lenderId(debt.getLender().getId())
                        .borrowerId(debt.getBorrower().getId())
                        .amount(debt.getAmount())
                        .build();
                debtSimplifies.add(debtSimplifyObject);
            }
        }

        // convert debtSimplify to Simplify and store to repo
        debtSimplifies.forEach(debtSimplify -> {
            User lender = userService.getUserById(debtSimplify.getLenderId());
            User borrower = userService.getUserById(debtSimplify.getBorrowerId());
            double amount = debtSimplify.getAmount();
            Group group = groupService.getGroupById(groupId);
            Simplify simplify = Simplify.builder()
                    .lender(lender)
                    .borrower(borrower)
                    .amount(amount)
                    .group(group)
                    .build();
            simplifyList.add(simplify);
        });

        // swap the borrower and lender if the amount is negative
        simplifyList.forEach(simplify -> {
            if (simplify.getAmount() < 0) {
                User borrower_old = simplify.getBorrower();
                simplify.setBorrower(simplify.getLender());
                simplify.setLender(borrower_old);
                simplify.setAmount(Math.abs(simplify.getAmount()));
            }
        });
        // save the details to repository
        List<Simplify> simplifieAll = simplifyRepository.saveAll(simplifyList);
        return simplifieAll;
    }

//    public void asdf() {
//        Map<Integer, List<Map<Integer, Double>>> integerMapMap = new HashMap<>();
//        List<Debt> debtList = debtRepository.findAll();
//        List<DebtSimplify> debtSimplifies = new ArrayList<>();
//        debtList.forEach(debt -> {
//
//            if (debtSimplifies.isEmpty()) {
//                DebtSimplify debtSimplifyObject = DebtSimplify.builder()
//                        .lenderId(debt.getLender().getId())
//                        .borrowerId(debt.getBorrower().getId())
//                        .amount(debt.getAmount())
//                        .build();
//                debtSimplifies.add(debtSimplifyObject);
//            } else {
//                debtSimplifies.forEach(debtSimplify -> {
//                    if (debtSimplify.getLenderId() == debt.getLender().getId() && debtSimplify.getBorrowerId() == debt.getBorrower().getId()) {
//                        double amount = debtSimplify.getAmount() + debt.getAmount();
//                        debtSimplify.setAmount(amount);
//                    } else if (debtSimplify.getLenderId() == debt.getBorrower().getId() && debtSimplify.getBorrowerId() == debt.getLender().getId()) {
//                        double amount = debtSimplify.getAmount() - debt.getAmount();
//                        debtSimplify.setAmount(amount);
//
//                    } else {
//                        DebtSimplify debtSimplifyObject = DebtSimplify.builder()
//                                .lenderId(debt.getLender().getId())
//                                .borrowerId(debt.getBorrower().getId())
//                                .amount(debt.getAmount())
//                                .build();
//                        debtSimplifies.add(debtSimplifyObject);
//                    }
//                });
//            }
//        });
//    }
}

