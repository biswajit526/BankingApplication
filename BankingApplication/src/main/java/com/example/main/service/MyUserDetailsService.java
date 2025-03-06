package com.example.main.service;



import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.main.config.CustomAccount;
import com.example.main.entity.Account;
import com.example.main.entity.Transection;
import com.example.main.repository.AccountRepository;
import com.example.main.repository.TransectionRepo;

@Service
public class MyUserDetailsService implements UserDetailsService {
	
	@Autowired
	private AccountRepository accountRepo;
	
	@Autowired
	private TransectionRepo transRepo;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	
	public Account findAccountByUsername(String username) {
		Optional<Account> optional = accountRepo.findByUsername(username);
		return optional.get();
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		Optional<Account> account = accountRepo.findByUsername(username);
		if (account.isEmpty()) {
			throw new UsernameNotFoundException("Inavlid Details!!!");
		}
		return new CustomAccount(account.get());
	}
	
	public Account registerAccount(String username, String password) {
		if(accountRepo.findByUsername(username).isPresent()) {
			throw new RuntimeException("Username already exists");
		}
		Account account = new Account(); 
		account.setUsername(username);
		account.setPassword(passwordEncoder.encode(password));
		account.setBalance(BigDecimal.ZERO);
		account.setRole("ROLE_USER");
		return accountRepo.save(account);
		
	}
	
	public void deposit(Account account, BigDecimal amount) {
		account.setBalance(account.getBalance().add(amount));
		accountRepo.save(account);
		
		Transection transection = new Transection(amount, "Deposit", LocalDateTime.now(), account);
		
		transRepo.save(transection);
		
		
	}
	
	public void withdraw(Account account, BigDecimal amount) {
		if(account.getBalance().compareTo(amount) < 0) {
			throw new RuntimeException("Insufficient funds");
		}
		
		account.setBalance(account.getBalance().subtract(amount));
		accountRepo.save(account);
		
		Transection transection = new Transection(amount, "Withdrawl", LocalDateTime.now(), account);
		transRepo.save(transection);
	}
	
	public void transferAmount(Account fromAccount, String toUsername, BigDecimal amount) {
		if(fromAccount.getBalance().compareTo(amount) < 0) {
			throw new RuntimeException("Insufficient funds");
		}
		
		Account toAccount = accountRepo.findByUsername(toUsername)
				.orElseThrow(() -> new RuntimeException("Recipient not found"));
		
		//Deduct
		fromAccount.setBalance(fromAccount.getBalance().subtract(amount));
		accountRepo.save(fromAccount);
		
		//Add
		toAccount.setBalance(toAccount.getBalance().add(amount));
		accountRepo.save(toAccount);
		
		//create transaction records
		Transection debitTransection = new Transection(amount, "Transfer Out to " + toAccount.getUsername(), 
				LocalDateTime.now(), fromAccount
				);
		transRepo.save(debitTransection);
		
		Transection creditTransection = new Transection(amount, "Transfer In to " + fromAccount.getUsername(), 
				LocalDateTime.now(), toAccount
				);
		transRepo.save(creditTransection);
	}
	
	public List<Transection> getTransectionHistory(Account account) {
		return transRepo.findByAccountId(account.getId());
	}

}
