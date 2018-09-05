package com.cg.dao;

import java.util.List;

import javax.management.Query;
import javax.persistence.EntityManager;

import com.cg.dto.Transactions;
import com.cg.dto.Wallet;
import com.cg.exceptions.WalletExceptions;

public class WalletDaoImpl implements WalletDao {
	private EntityManager entityManager;

	public WalletDaoImpl() {
		entityManager = JPAUtil.getEntityManager();
	}

	@Override
	public Wallet create(Wallet wallet) {
		entityManager.persist(wallet);
		return wallet;
	}

	@Override
	public double showBalance(String mobileno) throws WalletExceptions {
		Wallet wallet;
		if (exists(mobileno)) {
			wallet = entityManager.find(Wallet.class, mobileno);
		} else {
			throw new WalletExceptions("Wallet does not exist for " + mobileno + ". Please create one first.");
		}
		return wallet.getBalance();
	}

	@Override
	public double deposit(String mobileno, double amount) throws WalletExceptions {
		Wallet wallet;
		if (exists(mobileno)) {
			if (amount > 0) {
				entityManager.getTransaction().begin();
				wallet = entityManager.find(Wallet.class, mobileno);
				wallet.setBalance(amount + wallet.getBalance());
				entityManager.merge(wallet);
				entityManager.getTransaction().commit();
				entityManager.getTransaction().begin();
				String transactionQuery = "select max(transaction.id) from Transactions transaction";
				int last = (int) entityManager.createQuery(transactionQuery).getSingleResult();
				Transactions transaction = new Transactions();
				transaction.setId(++last);
				transaction.setMobileno(mobileno);
				transaction.setMessage("Rs. " + amount + " added.");
				entityManager.persist(transaction);
				entityManager.getTransaction().commit();
			} else {
				throw new WalletExceptions("Please enter a positive amount.");
			}
		} else {
			throw new WalletExceptions("Wallet does not exist for " + mobileno + ". Please create one first.");
		}

		return wallet.getBalance();
	}

	@Override
	public double withdraw(String mobileno, double amount) throws WalletExceptions {
		Wallet wallet;
		if (exists(mobileno)) {
			if (amount > 0) {
				entityManager.getTransaction().begin();
				wallet = entityManager.find(Wallet.class, mobileno);
				if (wallet.getBalance() - amount >= 0) {
					wallet.setBalance(wallet.getBalance() - amount);
					entityManager.merge(wallet);
					entityManager.getTransaction().commit();
					entityManager.getTransaction().begin();
					String transactionQuery = "select max(transaction.id) from Transactions transaction";
					int last = (int) entityManager.createQuery(transactionQuery).getSingleResult();
					Transactions transaction = new Transactions();
					transaction.setId(++last);
					transaction.setMobileno(mobileno);
					transaction.setMessage("Rs. " + amount + " withdrawn.");
					entityManager.persist(transaction);
					entityManager.getTransaction().commit();
				} else {
					throw new WalletExceptions("Insufficient balance for withdrawal");
				}
			} else {
				throw new WalletExceptions("Please enter a positive amount.");
			}
		} else {
			throw new WalletExceptions("Wallet does not exist for " + mobileno + ". Please create one first.");
		}

		return wallet.getBalance();
	}

	@Override
	public Wallet transfer(String mob_from, String mob_to, double amount) throws WalletExceptions {
		Wallet wallet_from = null, wallet_to = null;
		if (exists(mob_from)) {
			if (exists(mob_to)) {
				entityManager.getTransaction().begin();
				wallet_from = entityManager.find(Wallet.class, mob_from);
				wallet_to = entityManager.find(Wallet.class, mob_to);
				if (wallet_to.getBalance() - amount >= 0) {
					wallet_from.setBalance(wallet_from.getBalance() + amount);
					wallet_to.setBalance(wallet_to.getBalance() - amount);
					entityManager.merge(wallet_to);
					entityManager.merge(wallet_from);
					entityManager.getTransaction().commit();
					entityManager.getTransaction().begin();
					String transactionQuery = "select max(transaction.id) from Transactions transaction";
					int last = (int) entityManager.createQuery(transactionQuery).getSingleResult();
					Transactions transaction = new Transactions();
					transaction.setId(++last);
					transaction.setMobileno(mob_from);
					transaction.setMessage("Rs. " + amount + " sent to " + mob_to);
					entityManager.persist(transaction);
					entityManager.getTransaction().commit();
				} else {
					throw new WalletExceptions("Insufficient balance for withdrawal");
				}
			} else {
				throw new WalletExceptions("Wallet does not exist for " + mob_to + ". Please create one first.");
			}
		} else {
			throw new WalletExceptions("Wallet does not exist for " + mob_from + ". Please create one first.");
		}
		return wallet_from;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Transactions> printTransactions(String mobileno) throws WalletExceptions {
		List<Transactions> transaction;
		if (exists(mobileno)) {
			entityManager.getTransaction().begin();
			String transactionQuery = "select transaction from Transactions transaction where transaction.mobileno="
					+ mobileno;
			transaction = entityManager.createQuery(transactionQuery).getResultList();
			entityManager.getTransaction().commit();
		} else {
			throw new WalletExceptions("Wallet does not exist for " + mobileno + ". Please create one first.");
		}

		return transaction;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean exists(String mobileno) throws WalletExceptions {
		boolean temp = false;
		entityManager.getTransaction().begin();
		String select = "select wallet from  Wallet wallet ";
		List<Wallet> list = entityManager.createQuery(select).getResultList();
		entityManager.getTransaction().commit();
		for (Wallet wallet : list)
			if (wallet.getMobileno().equals(mobileno))
				temp = true;

		return temp;

	}
}