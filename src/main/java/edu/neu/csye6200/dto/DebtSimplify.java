package edu.neu.csye6200.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DebtSimplify {
    private int borrowerId;
    private int lenderId;
    private double amount;
}
