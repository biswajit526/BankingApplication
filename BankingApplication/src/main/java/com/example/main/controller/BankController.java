package com.example.main.controller;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.main.entity.Account;
import com.example.main.repository.AccountRepository;
import com.example.main.service.MyUserDetailsService;

@Controller
public class BankController {
	
	@Autowired
	private MyUserDetailsService service;

	@GetMapping("/dashboard")
	public String getDashboard(Model model) {
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		Account account = service.findAccountByUsername(username);
		model.addAttribute("account", account);
		return "dashboard";
	}
	
	@GetMapping("/login")
	public String getLogin() {
		return "login";
	}
	
	
	@GetMapping("/register")
	public String showRegistrationForm() {
		return "register";
	}
	
	@PostMapping("/register")
	public String registerAccount(@RequestParam String username, @RequestParam String password, Model model) {
		try {
			service.registerAccount(username, password);
			return "redirect:/login";
		} catch (Exception e) {
			model.addAttribute("error", e.getMessage());
			return "/register";
		}
	}
	
	@PostMapping("/deposit")
	public String deposit(@RequestParam BigDecimal amount) {
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		Account account = service.findAccountByUsername(username);
		service.deposit(account, amount);
		return "redirect:/dashboard";
	}
	
	@PostMapping("/withdraw")
	public String withdraw(@RequestParam BigDecimal amount, Model model) {
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		Account account = service.findAccountByUsername(username);
		try {
			service.withdraw(account, amount);
			
		} catch (Exception e) {
			model.addAttribute("error", e.getMessage());
			model.addAttribute("account", account);
			return "dashboard";
		}
		return "redirect:/dashboard";
		
	}
	
	@PostMapping("/transfer")
	public String transerMoney(@RequestParam String toUsername, @RequestParam BigDecimal amount, Model model) {
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		Account fromAccount = service.findAccountByUsername(username);
		
		
		try {
			service.transferAmount(fromAccount, toUsername, amount);
		} catch (Exception e) {
			model.addAttribute("error", e.getMessage());
			model.addAttribute("account", fromAccount);
			return "dashboard";
		}
		return "redirect:/dashboard";
	}
	
	@GetMapping("/transections")
	public String transectionHistory(Model model) {
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		Account account = service.findAccountByUsername(username);
		model.addAttribute("transections", service.getTransectionHistory(account));
		return "transections";
	}
	
	
}
