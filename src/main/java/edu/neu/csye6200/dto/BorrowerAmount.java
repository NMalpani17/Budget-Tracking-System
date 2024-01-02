package edu.neu.csye6200.dto;

import edu.neu.csye6200.model.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BorrowerAmount {

    User user;
    double amount;
}
