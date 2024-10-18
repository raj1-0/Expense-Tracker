package com.example.expensetracker.controller;


//import ch.qos.logback.core.model.Model;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.ui.Model;
import com.example.expensetracker.model.Expense;
import com.example.expensetracker.service.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Controller
public class ExpenseController {

    @Autowired
    private ExpenseService expenseService;

    @GetMapping("/")
    public String viewHomePage(Model model) {
        List<Expense> expenses = expenseService.getAllExpenses();
        BigDecimal totalAmount = expenses.stream().map(Expense::getAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
        model.addAttribute("expenses", expenses);
        model.addAttribute("totalAmount", totalAmount);
        return "index";
    }

    @GetMapping("/addExpense")
    public String showAddExpensePage(Model model) {
        Expense expense = new Expense();
        model.addAttribute("expense", expense);
        return "add-expense";
    }

    @PostMapping("/saveExpense")
    public String saveExpense(@ModelAttribute("expense") Expense expense, Model model) {
        expenseService.saveExpense(expense);
        return "redirect:/";
    }

    @GetMapping("/equalSplit")
    public String showEqualSplitPage(Model model) {
        List<Expense> expenses = expenseService.getAllExpenses();

        BigDecimal totalAmount = expenses.stream().map(Expense::getAmount).reduce(BigDecimal.ZERO, BigDecimal::add);

        int totalId=expenses.size();

        BigDecimal perIdAmount = BigDecimal.ZERO;
        if (totalId > 0) {
            perIdAmount = totalAmount.divide(new BigDecimal(totalId), 2, BigDecimal.ROUND_HALF_UP);
        }

        model.addAttribute("totalAmount", totalAmount);
        model.addAttribute("totalId", totalId);
        model.addAttribute("perIdAmount", perIdAmount);
        return "equal-split";
    }

    @GetMapping("/exactSplit")
    public String showExactSplitPage(Model model) {
        List<Expense> expenses = expenseService.getAllExpenses();
        model.addAttribute("expenses", expenses);
        return "exact-split";
    }

    @GetMapping("/percentageSplit")
    public String showPercentageSplitPage(Model model) {
        List<Expense> expenses = expenseService.getAllExpenses();
        BigDecimal totalAmount = expenses.stream().map(Expense::getAmount).reduce(BigDecimal.ZERO, BigDecimal::add);

        for (Expense expense : expenses) {
            BigDecimal percentage = BigDecimal.ZERO;
            if (totalAmount.compareTo(BigDecimal.ZERO) > 0) {
                percentage = (expense.getAmount().divide(totalAmount, 4, BigDecimal.ROUND_HALF_UP)).multiply(new BigDecimal(100));
            }
            expense.setPercentage(percentage);
        }
            model.addAttribute("expenses", expenses);
            return "percentage-split";
    }

    @GetMapping("/balanceSheet")
    public String showBalanceSheetPage(Model model) {
        List<Expense> expenses = expenseService.getAllExpenses();
        BigDecimal totalAmount = expenses.stream().map(Expense::getAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
        model.addAttribute("expenses", expenses);
        model.addAttribute("totalAmount", totalAmount);
        return "balance-sheet";
    }

    @GetMapping("/downloadBalanceSheet")
    public void downloadBalanceSheetPage(HttpServletResponse response) throws IOException {
    List<Expense> expenses = expenseService.getAllExpenses();
    response.setContentType("text/csv");
    response.setHeader("Content-Disposition", "attachment; filename=\"balance_sheet.csv\"");
    PrintWriter writer = response.getWriter();
    writer.println("Name,Amount");
    for (Expense expense : expenses) {
        writer.println(expense.getName() + "," + expense.getAmount());
    }
    writer.println("Total,"+expenses.stream().map(Expense::getAmount).reduce(BigDecimal.ZERO, BigDecimal::add));
    writer.flush();
    }
    //@PostMapping("/backExpense")
    //public String backExpense(@ModelAttribute("expense") Expense expense, Model model) {
     //   expenseService.backExpense(expense);
    //    return "redirect:/";
    //}

    @GetMapping("editExpense/{id}")
    public String showUpdateExpensePage(@PathVariable("id") long id, Model model) {
        Expense expense = expenseService.getExpenseById(id);
        model.addAttribute("expense", expense);
        return "update-expense";
    }

    @PostMapping("/updateExpense/{id}")
    public String updateExpense(@PathVariable("id") long id, @ModelAttribute("expense") Expense expense) {
        Expense existingExpense = expenseService.getExpenseById(id);
        existingExpense.setName(expense.getName());
        existingExpense.setName(expense.getEmail());
        existingExpense.setAmount(expense.getAmount());
        expenseService.saveExpense(existingExpense);
        return "redirect:/";
    }

    @GetMapping("/deleteExpense/{id}")
    public String deleteExpense(@PathVariable("id") long id) {
        expenseService.deleteExpenseById(id);
        return "redirect:/";
    }
}
