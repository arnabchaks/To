package com.cg.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.cg.dto.Wallet;
import com.cg.dao.WalletDao;

@Controller
@RequestMapping("/controller")
public class WalletController {
	private static final String ACTION_KEY = "action";
	private static final String VIEW_MENU_ACTION = "goToMenu";
	private static final String SHOW_BALANCE_ACTION = "showBalance";
	private static final String CREATE_WALLET_ACTION = "addWallet";
	private static final String ADD_BALANCE_ACTION = "addBalance";
	private static final String WITHDRAW_ACTION = "withdraw";
	private static final String TRANSFER_ACTION = "transfer";
	private static final String VIEW_TRANSACTIONS_ACTION = "viewTransactions";
	private static final String EDIT_WALLET_ACTION = "editWallet";
	private static final String SAVE_WALLET_ACTION = "saveWallet";
	private static final String ERROR_KEY = "errorMessage";
	@Autowired
	private WalletDao walletDao;
	{
		System.out.println("In controller");
	}

	@RequestMapping(method = RequestMethod.GET)
	protected String processViewAddRequest(ModelMap map, @RequestParam(ACTION_KEY) String actionName) {
		if ((VIEW_MENU_ACTION.equals(actionName))) {

			System.out.println("In view menu");
			return "menu";

		} else if (CREATE_WALLET_ACTION.equals(actionName)) {
			return "saveForm";

		}
		else if (VIEW_TRANSACTIONS_ACTION.equals(actionName)) {
			return "transactionsList";

		}else {
			String errorMessage = "[" + actionName + "] is not a valid action.";
			map.addAttribute(ERROR_KEY, errorMessage);
			return null;
		}
	}

	@RequestMapping(method = RequestMethod.GET, params = "id")
	protected String processSearchRequest(ModelMap map, @RequestParam(ACTION_KEY) String actionName,
			@RequestParam("id") int id) {
		if ((SHOW_BALANCE_ACTION.equals(actionName))) {

			System.out.println("In view menu");
			return "menu";

		} else if (ADD_BALANCE_ACTION.equals(actionName)) {
			return "updateForm";

		} else if (WITHDRAW_ACTION.equals(actionName)) {
			return "updateForm";

		} else if (TRANSFER_ACTION.equals(actionName)) {
			return "updateForm";

		} else {
			String errorMessage = "[" + actionName + "] is not a valid action.";
			map.addAttribute(ERROR_KEY, errorMessage);
			return null;
		}
	}

	@RequestMapping(method = RequestMethod.POST, params = { "id" })
	protected String processDeleteRequest(ModelMap map, @RequestParam(ACTION_KEY) String actionName,
			@RequestParam(value = "id", required = false) String[] ids, @ModelAttribute("wallet") Wallet wallet) {

		if (SAVE_WALLET_ACTION.equals(actionName)) {

			//if (Wallet.getId() == -1)
				walletDao.create(wallet);
			//else
			//	carDAO.update(carDTO);
			return "menu";

		}else if (EDIT_WALLET_ACTION.equals(actionName)) {
			return "menu";

		} 
		else {
			String errorMessage = "[" + actionName + "] is not a valid action.";
			map.addAttribute(ERROR_KEY, errorMessage);
			return null;
		}

	}

	@ModelAttribute("wallet")
	protected Wallet makeWallet() {
		Wallet wallet = new Wallet();
		return wallet;

	}
}