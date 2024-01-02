package edu.neu.csye6200.service;

import edu.neu.csye6200.model.Debt;

import java.util.List;

public interface DebtService {
    Debt getDebtById(int debtId);

    Debt createDebt(Debt debt);

    Debt updateDebt(int debtId, Debt debt);

    void deleteDebt(int debtId);

    List<Debt> getAllByGroupId(int groupId);

//    List<DebtSimplify> findAllDebtGroupByLenderAndBorrowerById(int groupId);
}
