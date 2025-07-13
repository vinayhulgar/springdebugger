package org.example.springdebugger.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.example.springdebugger.service.customer.CustomerService;
import org.example.springdebugger.service.displayname.DisplayNameResolver;
import org.example.springdebugger.service.customer.TransactionLevel1Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.UUID;

@Controller
public class HomeController {

    private final DisplayNameResolver displayNameResolver;
    private final CustomerService customerService;
    private final TransactionLevel1Service transactionLevel1Service;

    @Value("${developer.name:Default Developer}")
    private String developerName;

    @Autowired
    public HomeController(DisplayNameResolver displayNameResolver, CustomerService customerService, TransactionLevel1Service transactionLevel1Service) {
        this.displayNameResolver = displayNameResolver;
        this.customerService = customerService;
        this.transactionLevel1Service = transactionLevel1Service;
    }

    @GetMapping("/")
    public String home(Model model, HttpServletRequest request) {
        // Hello world message
        String message = "Welcome to my next AI Startup!";

        // My name (read from properties)
        String accountName = developerName;

        // Get client's IP address using the service
        String displayName = displayNameResolver.resolveDisplayName(developerName);

        // Get all customers
        var customers = customerService.getAllCustomers();

        // Add attributes to the model
        model.addAttribute("message", message);
        model.addAttribute("name", accountName);
        model.addAttribute("displayName", displayName);
        model.addAttribute("customers", customers);

        // Return the view name (index.html)
        return "index";
    }

     @PostMapping("/add-random-customer")
    public String addRandomCustomer() {
        transactionLevel1Service.startTransactionChain(UUID.randomUUID().toString().substring(0, 10));
        return "redirect:/";
    }



}