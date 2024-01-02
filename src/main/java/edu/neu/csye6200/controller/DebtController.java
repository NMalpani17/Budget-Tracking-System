package edu.neu.csye6200.controller;

import edu.neu.csye6200.model.Debt;
import edu.neu.csye6200.service.DebtService;
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
@RequestMapping("/debts")
@CrossOrigin(origins = "http://localhost:3000")
public class DebtController {
    @Autowired
    private DebtService debtService;

    @GetMapping("/{debtId}")
    public Debt getDebtById(@PathVariable int debtId) {
        return debtService.getDebtById(debtId);
    }

    @PostMapping
    public Debt createDebt(@RequestBody Debt debt) {
        return debtService.createDebt(debt);
    }

    @PutMapping("/{debtId}")
    public Debt updateDebt(@PathVariable int debtId, @RequestBody Debt debt) {
        return debtService.updateDebt(debtId, debt);
    }

    @DeleteMapping("/{debtId}")
    public void deleteDebt(@PathVariable int debtId) {
        debtService.deleteDebt(debtId);
    }

    @GetMapping("/groupId/{groupId}")
    public List<Debt> getAllDebtsByGroupId(@PathVariable int groupId){
        return debtService.getAllByGroupId(groupId);
    }
}
