package com.example.main.entity;



import java.math.BigDecimal;


import java.util.List;



import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;


@Entity
public class Account {

	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String username;
	private String password;
	private BigDecimal balance;
	private String role;
	
	@OneToMany(mappedBy = "account")
	private List<Transection> transactions;
	
	
	
	public Account() {
		
	}
	
	



	public Account(long id, String username, String password, BigDecimal balance, String role,
			List<Transection> transactions) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.balance = balance;
		this.role = role;
		this.transactions = transactions;
	}





	public long getId() {
		return id;
	}



	public void setId(long id) {
		this.id = id;
	}



	public String getUsername() {
		return username;
	}



	public void setUsername(String username) {
		this.username = username;
	}



	public String getPassword() {
		return password;
	}



	public void setPassword(String password) {
		this.password = password;
	}



	public BigDecimal getBalance() {
		return balance;
	}



	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}



	public String getRole() {
		return role;
	}



	public void setRole(String role) {
		this.role = role;
	}



	public List<Transection> getTransactions() {
		return transactions;
	}



	public void setTransactions(List<Transection> transactions) {
		this.transactions = transactions;
	}

	
	
	
	
	
}

