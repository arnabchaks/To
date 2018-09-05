package com.cg.service;

import java.util.List;

import com.cg.dto.Transactions;
import com.cg.dto.Wallet;
import com.cg.exceptions.WalletExceptions;

public interface WalletService {
	Wallet create(Wallet wallet);

	double showBalance(String mobileno) throws WalletExceptions;

	double deposit(String mobileno, double amount) throws WalletExceptions;

	double withdraw(String mobileno, double amount) throws WalletExceptions;

	Wallet transfer(String mob_from, String mob_to, double amount) throws WalletExceptions;

	List<Transactions> printTransactions(String mobileno) throws WalletExceptions;

	boolean validateWallet(Wallet wallet) throws WalletExceptions;

}
