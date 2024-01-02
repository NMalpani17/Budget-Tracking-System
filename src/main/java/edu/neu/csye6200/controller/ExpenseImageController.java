package edu.neu.csye6200.controller;

import edu.neu.csye6200.exception.CustomException;
import edu.neu.csye6200.model.Expense;
import edu.neu.csye6200.model.ExpenseImage;
import edu.neu.csye6200.repository.ExpenseImageRepository;
import edu.neu.csye6200.service.ExpenseImageService;
import edu.neu.csye6200.service.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@RestController
@RequestMapping("/expenses/image")
@CrossOrigin(origins = "http://localhost:3000")
public class ExpenseImageController {

    @Autowired
    private ExpenseImageService expenseImageService;

    @Autowired
    private ExpenseService expenseService;

    @Autowired
    private ExpenseImageRepository expenseImageRepository;

    @PostMapping
    public String uploadImage(@RequestParam("file") MultipartFile file,
                              @RequestParam("expenseId") int expenseId) {
        try {
            // check if there is a image tied to the expenseImage already
            Optional<ExpenseImage> expenseImageOptional = expenseImageRepository.findByExpenseId(expenseId);
            if (expenseImageOptional.isPresent()) {
                // Handle the case when the entity is found
                throw new CustomException("An image already exists for this transaction, cannot upload multiple");
            }

            // if no records found, proceed with uploading the image
            ExpenseImage expenseImage = new ExpenseImage();
            Expense expense = expenseService.getExpenseById(expenseId);
            if (expense != null) {
                expenseImage.setName(file.getOriginalFilename());
                expenseImage.setImage(file.getBytes());
                expenseImage.setFileType(file.getContentType());
                expenseImage.setExpense(expense);
            }

            expenseImageService.saveImage(expenseImage);

            return "Image uploaded successfully!";
        } catch (Exception e) {
            return "Failed to upload image: " + e.getMessage();
        }
    }

    @GetMapping("/download/{expenseId}")
    public ResponseEntity<byte[]> downloadFile(@PathVariable Integer expenseId) {
        // Fetch the entity from the database by expenseId
        ExpenseImage expenseImage = expenseImageRepository.findByExpenseId(expenseId).orElse(null);

        if (expenseImage == null) {
            // Handle the case when the entity is not found
            return ResponseEntity.notFound().build();
        }

        // Set response headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(expenseImage.getFileType()));
        headers.setContentDispositionFormData("attachment", expenseImage.getName());

        return new ResponseEntity<>(expenseImage.getImage(), headers, HttpStatus.OK);
    }

    @DeleteMapping("/{expenseId}")
    public void deleteImage(@PathVariable Integer expenseId) {
        // Fetch the entity from the database by expenseId
        ExpenseImage expenseImage = expenseImageRepository.findByExpenseId(expenseId).orElse(null);
        if (expenseImage == null) {
            // Handle the case when the entity is not found
            throw new CustomException("No Image found for this transaction");
        }

        // proceed to delete
        expenseImageRepository.deleteById(expenseImage.getId());
    }
}
