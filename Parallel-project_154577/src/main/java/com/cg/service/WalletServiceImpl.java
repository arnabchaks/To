package com.cg.service;

import java.util.List;

import com.cg.dao.WalletDao;
import com.cg.dao.WalletDaoImpl;
import com.cg.dto.Transactions;
import com.cg.dto.Wallet;
import com.cg.exceptions.WalletExceptions;

public class WalletServiceImpl implements WalletService {
	private WalletDao dao;

	public WalletServiceImpl() {
		dao = new WalletDaoImpl();
	}

	@Override
	public Wallet create(Wallet wallet) {

		return dao.create(wallet);
	}

	@Override
	public double showBalance(String mobileno) throws WalletExceptions {

		return dao.showBalance(mobileno);
	}

	@Override
	public double deposit(String mobileno, double amount) throws WalletExceptions {

		return dao.deposit(mobileno, amount);
	}

	@Override
	public double withdraw(String mobileno, double amount) throws WalletExceptions {

		return dao.withdraw(mobileno, amount);
	}

	@Override
	public Wallet transfer(String mob_from, String mob_to, double amount) throws WalletExceptions {

		return dao.transfer(mob_from, mob_to, amount);
	}

	@Override
	public List<Transactions> printTransactions(String mobileno) throws WalletExceptions {

		return dao.printTransactions(mobileno);
	}

	@Override
	public boolean validateWallet(Wallet wallet) throws WalletExceptions {
		if (!wallet.getName().matches("[A-Za-z ]{1,50}")) {
			throw new WalletExceptions(
					"ERROR: Your name should be alphabetical in nature and less than 50 characters long.");
		}
		if (!wallet.getMobileno().matches("[0-9]{10}")) {
			throw new WalletExceptions("ERROR: Your mobile number must be numerical and 10 digits long.");
		}
		if (wallet.getBalance() < 0) {
			throw new WalletExceptions("ERROR: Please enter a non-negative initial balance.");
		} else {
			return true;
		}
	}
}
