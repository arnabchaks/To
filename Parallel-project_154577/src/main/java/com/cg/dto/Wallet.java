package com.cg.dto;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="Wallet")
public class Wallet implements Serializable {
	/**
	 * @author arnchakr
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name = "MOBILENO")
	private String mobileno;
	@Column(name = "NAME")
	private String name;
	@Column(name = "WALLET")
	private double balance;
	public Wallet() {
		balance = 0.0;
		mobileno = name = "";
	}
	
	public String getMobileno() {
		return mobileno;
	}
	public void setMobileno(String mobileno) {
		this.mobileno = mobileno;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setBalance(double balance) {
		this.balance = balance;
	}
	public double getBalance() {
		return balance;
	}
	@Override
	public String toString() {
		return "Mobileno=" + mobileno + ", Name=" + name + ", Balance=" + balance;
	}
	
}
