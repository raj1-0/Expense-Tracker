package com.example.expensetracker.service;

import com.example.expensetracker.model.Expense;
import com.example.expensetracker.repository.ExpenseRepository;
import com.example.expensetracker.utility.EncryptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.crypto.SecretKey;
import java.security.SecureRandom;
import java.util.Base64;

import java.util.List;

@Service
public class ExpenseService {

    @Autowired
    private ExpenseRepository expenseRepository;

    @Autowired
    private EncryptionService encryptionService;

    public List<Expense> getAllExpenses() {
        return expenseRepository.findAll();
    }

    public void saveExpense(Expense expense) {
//        expenseRepository.save(expense);
        try {
            // Generate AES key
            SecretKey aesKey = encryptionService.generateAESKey();

            // Generate IV (Initialization Vector)
            byte[] iv = new byte[12];
            new SecureRandom().nextBytes(iv);  // Random IV for each encryption

            // Encrypt patient data using AES
            String patientData = expense.toString();  // assuming patient has overridden toString()
            byte[] encryptedData = encryptionService.encryptData(patientData, aesKey, iv);

            // Encrypt AES key using RSA
//            byte[] encryptedAESKey = encryptionService.encryptAESKeyWithRSA(aesKey);

            // Encode data to store in database
            expense.setEncryptedData(Base64.getEncoder().encodeToString(encryptedData));
//            expense.setEncryptedAESKey(Base64.getEncoder().encodeToString(encryptedAESKey));
            expense.setIv(Base64.getEncoder().encodeToString(iv));

            // Save the encrypted patient to the database
            expenseRepository.save(expense);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void backExpense(Expense expense) {expenseRepository.save(expense);}

    public Expense getExpenseById(Long id) {
        return expenseRepository.findById(id).orElse(null);
    }

    public void deleteExpenseById(Long id) {
        expenseRepository.deleteById(id);
    }

}
