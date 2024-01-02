package edu.neu.csye6200.dto;

import edu.neu.csye6200.model.Group;
import edu.neu.csye6200.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ExpenseUnequal {

    private String description;
    private double amount;
    private User payer;
    private List<BorrowerAmount> borrower;
    private Group group;
    private Date date;
}
