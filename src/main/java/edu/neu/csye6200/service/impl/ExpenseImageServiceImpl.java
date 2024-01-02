package edu.neu.csye6200.service.impl;

import edu.neu.csye6200.model.ExpenseImage;
import edu.neu.csye6200.repository.ExpenseImageRepository;
import edu.neu.csye6200.service.ExpenseImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ExpenseImageServiceImpl implements ExpenseImageService {

    @Autowired
    private ExpenseImageRepository expenseImageRepository;

    public void saveImage(ExpenseImage image) {
        expenseImageRepository.save(image);
    }
}
