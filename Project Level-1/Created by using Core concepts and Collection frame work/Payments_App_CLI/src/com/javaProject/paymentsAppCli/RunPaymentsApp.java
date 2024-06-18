package com.javaProject.paymentsAppCli;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import com.javaProject.paymentsAppCli.entity.AcctType;
import com.javaProject.paymentsAppCli.entity.BankAccount;
import com.javaProject.paymentsAppCli.entity.Transaction;
import com.javaProject.paymentsAppCli.entity.TxnType;
import com.javaProject.paymentsAppCli.entity.User;
import com.javaProject.paymentsAppCli.entity.Wallet;

public class RunPaymentsApp {

	public static List<User> userList = new ArrayList<User>();
	public static int currUserId = -1;
	public static List<BankAccount> baAcctList = new ArrayList<BankAccount>();
	public static Map<Integer, Wallet> walletList = new HashMap<Integer, Wallet>();

	public static void main(String[] args) {

		int SelectedOption = 0;

		while (true) {
			System.out.println("\nPlease choose an option from below: ");
			System.out.println("1.Registration");
			System.out.println("2.Login");
			System.out.println("3.Add bank account");
			System.out.println("4.Wallet");
			System.out.println("5.List of users");
			System.out.println("6.Current user");
			System.out.println("7.List of users bank accounts");
			System.out.println("8.Delete bank account");
			System.out.println("9.Do Transaction");
			System.out.println("10.Log Out");
			System.out.println("-1.Exit");

			Scanner sc = new Scanner(System.in);
			System.out.println("Enter an option: ");
			String op = sc.next();

			try {
				SelectedOption = Integer.parseInt(op);
			} catch (NumberFormatException ne) {
				// ne.printStackTrace();
				System.out.println("This is number format exception");
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("This is unknown exception");
			} finally {
				System.out.println();
			}

			UserOperations uop = new UserOperations();

			if (op.equalsIgnoreCase("1")) {
				System.out.println("User selected registartion");
				Registration();
			} else if (op.equalsIgnoreCase("2")) {
				System.out.println("Login");
				Login();
			} else if (op.equalsIgnoreCase("3")) {
				if (validateCurrUser()) {
					System.out.println("Please Enter bank account details: ");
					AddBankAccount();
				}
			} else if (op.equalsIgnoreCase("4")) {
				if (validateCurrUser()) {
					System.out.println("Wallet");
					WalletOperation();
				}
			} else if (op.equalsIgnoreCase("5")) {
				System.out.println("List of users");
				uop.printUsersList(userList);
			} else if (op.equalsIgnoreCase("6")) {
				if (currUserId != -1) {
					System.out.println("Current users");
					uop.printCurrUserDetails(currUserId);
				}
			} else if (op.equalsIgnoreCase("7")) {
				if (currUserId != -1) {
					System.out.println("List of users bank accounts");
					printUserBankAccounts();
				}
			} else if (op.equalsIgnoreCase("8")) {
				if (currUserId != -1) {
					System.out.println("Delete bank account");
					System.out.println("Enter Bank Account Number: ");
					String accNum = sc.next();
					deleteUserBankAccount(currUserId, accNum, userList);
				} else {
					System.out.println("please login to delete bank accounts");
				}
			} else if (op.equalsIgnoreCase("9")) {
				if (currUserId != -1) {
					System.out.println("DO TRANSACTION");
					transactionOperation();
				}
			} else if (op.equalsIgnoreCase("10")) {
				if (currUserId != -1) {
					System.out.println("Succuessfully Logged Out !! \n");
					logout();
				}
			} else if (op.equalsIgnoreCase("-1")) {
				System.out.println("Exit");
				break;
			} else {
				System.out.println("Enter a valid Option!!! \n");

			}
		}
	}

	public static void Registration() {
		Scanner sc = new Scanner(System.in);
		UserOperations uop = new UserOperations();
		System.out.println("First Name: ");
		String fname = sc.next();
		System.out.println("Last Name: ");
		String lname = sc.next();
		System.out.println("Phone Number: ");
		long phnum = sc.nextLong();
		System.out.println("Date of Birth: ");
		String dob = sc.next();
		System.out.println("Address: ");
		String addr = sc.next();
		System.out.println("Password: ");
		String pswd = sc.next();

		User u;
//		try {
		u = uop.doUserRegistration(fname, lname, phnum, dob, addr, pswd);
		userList.add(u);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}

		Wallet wallet = new Wallet();
		int userId = u.getUserId();
		walletList.put(userId, wallet);

	}

	public static boolean Login() {
		Scanner sc = new Scanner(System.in);
		UserOperations uop = new UserOperations();

		System.out.println("Enter Your User ID: ");
		String uId = sc.next();
		System.out.println("Enter Your Password: ");
		String pswd = sc.next();

		if (uop.verifyUserLogin(uId, pswd)) {
			currUserId = Integer.parseInt(uId);
			System.out.println("Login Successfull !! \n");
			return true;
		} else {
			System.out.println("Login Failed, please try again!!");
			return false;
		}

	}

	public static boolean validateCurrUser() {
		if (currUserId != -1) {
			return true;
		} else {
			return false;
		}
	}

	public static void AddBankAccount() {
		AcctType selectedAcctType = null;
		Scanner sc = new Scanner(System.in);
		UserOperations uop = new UserOperations();

		System.out.println("Bank Account Number: ");
		String baAcctNum = sc.next();
		System.out.println("Bank IFSC Code: ");
		String baIFSCCode = sc.next();
		System.out.println("Bank Name: ");
		String baName = sc.next();
		System.out.println("Bank Account Type: ");
		System.out.println("SA: SAVINGS");
		System.out.println("CA: CURRENT");
		System.out.println("LA: LOAN");
		System.out.println("SL: SALARY");
		String baAcctType = sc.next();

		if (baAcctType.equalsIgnoreCase("SA")) {
			// System.out.println(AcctType.SAVINGS);
			selectedAcctType = AcctType.SAVINGS;

		} else if (baAcctType.equalsIgnoreCase("CA")) {
			// System.out.println(AcctType.CURRENT);
			selectedAcctType = AcctType.CURRENT;
		} else if (baAcctType.equalsIgnoreCase("LA")) {
			// System.out.println(AcctType.LOAN);
			selectedAcctType = AcctType.LOAN;
		} else if (baAcctType.equalsIgnoreCase("SL")) {
			// System.out.println(AcctType.SALARY);
			selectedAcctType = AcctType.SALARY;
		} else {
			System.out.println("Enter Valid Account Type Option");
		}

		System.out.println("Bank Account Pin: ");
		String baAcctPin = sc.next();

		BankAccount ba = new BankAccount();
		ba.setBankAcctNumber(baAcctNum);
		ba.setBankAcctIFSC(baIFSCCode);
		ba.setBankAcctBankName(baName);
		ba.setBankAcctType(selectedAcctType);
		ba.setBankAcctPin(baAcctPin);
		ba.setUserId(currUserId);

		for (User u : userList) {
			if (u.getUserId() == currUserId) {
				u.getBaList().add(ba);
			}
		}
		baAcctList.add(ba);
	}

	public static void printUserBankAccounts() {
		UserOperations uop = new UserOperations();
		Map<User, List<BankAccount>> mapItems = uop.getUsersBankAccount();

		for (User u : mapItems.keySet()) {
			List<BankAccount> baList = mapItems.get(u);
			System.out.println(u);
			if (baList != null) {
				for (BankAccount ba : baList) {
					System.out.println("--" + ba.printBankAcctDetails());
				}
			}

		}
	}

	public static void deleteUserBankAccount(int UserId, String accNum, List<User> userlist) {
		for (User u : userlist) {
			if (u.getUserId() == UserId) {
				List<BankAccount> baAcctList = u.getBaList();
				Iterator<BankAccount> iterator = baAcctList.iterator();
				while (iterator.hasNext()) {
					BankAccount acct = iterator.next();
					if (acct.getBankAcctNumber().equals(accNum)) {
						iterator.remove();
						System.out.println("BankAccount deleted successfully");
						return;
					}
				}
			}
		}
		System.out.println("Bank Account has Not matched");
	}

	public static void WalletOperation() {
		while (true) {
			System.out.println("1. Add money to Wallet");
			System.out.println("2. Check wallet balance");
			System.out.println("3. Back");

			Scanner sc = new Scanner(System.in);
			System.out.println("Enter an Option: ");
			int opt = sc.nextInt();

			switch (opt) {
			case 1:
				System.out.println("Add money to Wallet");
				if (currUserId != -1) {
					Wallet wa = new Wallet();
					// System.out.println("Wallet Balance: " + wa.getBalance());

					Scanner scan = new Scanner(System.in);
					System.out.println("Enter an amount: ");
					double amount = scan.nextDouble();
					wa.setBalance(wa.getBalance() + amount);
					if (amount <= wa.getWalletAmountLimit()) {
						UserOperations uop = new UserOperations();
						uop.addMoneyToWallet(amount);
					} else {
						System.out.println("Out of the Wallet Limit!!");
					}
					// System.out.println("Current Wallet Balance: " + wa.getBalance());
					System.out.println();
				} else {
					System.out.println("Please check whether user is login or not!!");
				}

				break;

			case 2:
				if (currUserId != -1) {
					UserOperations uop = new UserOperations();
					try {
						System.out.println("Current Wallet Balance: " + uop.checkWalletBalance());
					} catch (NullPointerException ne) {
						ne.printStackTrace();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				break;

			case 3:
				System.out.println("Thanks You!! \n");
				break;

			default:
				System.out.println("Enter valid Option!!");
			}
			if (opt == 3) {
				break;
			}
		}

	}

	public static void transactionOperation() {
		if (currUserId != -1) {
			Scanner sc = new Scanner(System.in);
			Transaction transaction = new Transaction();
			Date date = new Date();
			UserOperations ops = new UserOperations();
			int i = 1;
			for (TxnType transactionType : TxnType.values()) {
				System.out.println(i + " " + transactionType);
				i++;
			}
			System.out.println("select an option to perform : ");
			int option = sc.nextInt();
			if (option == 1) {
				transaction.setTransactionType(TxnType.DEBIT);
				System.out.println("Select which type of transfer you want to perform: ");
				System.out.println("1.Wallet to Wallet");
				System.out.println("2.Bank to Bank");
				System.out.println("3.Bank to Wallet");
				System.out.println("4.Wallet to Bank");
				boolean result;

				int transferType = sc.nextInt();
				System.out.println("Enter Transaction Amount : ");
				double tAmount = sc.nextDouble();
				transaction.setTxnAmount(tAmount);
				transaction.setTxnDate(date);
				transaction.setTxnId(date.getTime());

				switch (transferType) {
				case 1:
					Wallet source = walletList.get(currUserId);
					transaction.setSrcWallet(source);
					System.out.println("enter receiver userId : ");
					int receiver = sc.nextInt();
					Wallet destination = walletList.get(receiver);
					transaction.setDestWallet(destination);
					result = ops.transaction(source, destination, transaction.getTransactionType(), tAmount);
					if (result) {
						System.out.println("Transaction Successful \n");
					} else {
						System.out.println("Transaction Failed \n");
					}

					break;

				case 2:
					System.out.println("Enter sender bankaccount number : ");
					String senderAcctNum = sc.next();
					BankAccount source2 = null;
					List<BankAccount> userAccountList = new ArrayList<BankAccount>();
					Map<User, List<BankAccount>> mapItems = ops.getUsersBankAccount();
					for (User u : mapItems.keySet()) {
						if (u.getUserId() == currUserId) {
							userAccountList = mapItems.get(u);
						}
					}
					for (BankAccount b : userAccountList) {
						if (b.getBankAcctNumber().equals(senderAcctNum)) {
							source2 = b;
						}
					}
					if (source2 != null) {
						System.out.println("Enter receiver bankaccount number : ");
						String recieverAcctNum = sc.next();
						BankAccount destination2 = null;
						for (BankAccount b : baAcctList) {
							if (b.getBankAcctNumber().equals(recieverAcctNum)) {
								destination2 = b;
							}
						}

						transaction.setTxnSource(source2);
						transaction.setTxnDestination(destination2);
						result = ops.transaction(source2, destination2, transaction.getTransactionType(), tAmount);
						if (result) {
							System.out.println("Transaction Successful \n");
						} else {
							System.out.println("Transaction Failed \n");
						}
					}
					break;
				case 3:
					System.out.println("Enter sender bankaccount number : ");
					String senderAcctNumBankToWallet = sc.next();
					BankAccount sourceBankToWallet = null;

					List<BankAccount> userAccountListBankToWallet = new ArrayList<BankAccount>();
					Map<User, List<BankAccount>> mapItemsBankToWallet = ops.getUsersBankAccount();
					for (User u : mapItemsBankToWallet.keySet()) {
						if (u.getUserId() == currUserId) {
							userAccountListBankToWallet = mapItemsBankToWallet.get(u);
						}
					}
					for (BankAccount b : userAccountListBankToWallet) {
						if (b.getBankAcctNumber().equals(senderAcctNumBankToWallet)) {
							sourceBankToWallet = b;
							transaction.setTxnSource(sourceBankToWallet);
						}
					}

					System.out.println("enter receiver userId : ");
					int receiverId = sc.nextInt();
					Wallet destinationWallet = walletList.get(receiverId);
					transaction.setDestWallet(destinationWallet);
					result = ops.transaction(sourceBankToWallet, destinationWallet, transaction.getTransactionType(),
							tAmount);
					if (result) {
						System.out.println("Transaction Successful \n");
					} else {
						System.out.println("Transaction Failed \n");
					}
					break;
				case 4:
					Wallet sourceWallet = walletList.get(currUserId);
					transaction.setSrcWallet(sourceWallet);

					System.out.println("Enter receiver bankaccount number : ");
					String recieverAcctNum = sc.next();
					BankAccount destinationAccount = null;
					try {
					for (BankAccount b : baAcctList) {
						if (b.getBankAcctNumber().equals(recieverAcctNum)) {
							destinationAccount = b;
						}

					}
					}catch(Exception e) {
						e.printStackTrace();
					}
					transaction.setTxnDestination(destinationAccount);
					result = ops.transaction(sourceWallet, destinationAccount, transaction.getTransactionType(),
							tAmount);
					if (result) {
						System.out.println("Transaction Successful \n");
					} else {
						System.out.println("Transaction Failed \n");
					}
					break;

				default:
					System.out.println("Please enter correct option\n");
				}

			}

			else if (option == 2) {
				transaction.setTransactionType(TxnType.CREDIT);
				transaction.setTxnDate(date);
				transaction.setTxnId(date.getTime());
				System.out.println("Enter Account Number : ");
				String targetAcctNum = sc.next();
				System.out.println("Enter Transaction Amount : ");
				double tAmount = sc.nextDouble();
				BankAccount source = null;
				List<BankAccount> userAccountList = new ArrayList<BankAccount>();
				Map<User, List<BankAccount>> mapItems = ops.getUsersBankAccount();
				for (User u : mapItems.keySet()) {
					if (u.getUserId() == currUserId) {
						userAccountList = mapItems.get(u);
					}
				}
				for (BankAccount b : userAccountList) {
					if (b.getBankAcctNumber().equals(targetAcctNum)) {
						source = b;
					}
				}
				transaction.setTxnSource(source);
				ops.creditAmountToAccount(source, tAmount);
			}
		}
	}

	public static void logout() {
		currUserId = -1;
	}
}
