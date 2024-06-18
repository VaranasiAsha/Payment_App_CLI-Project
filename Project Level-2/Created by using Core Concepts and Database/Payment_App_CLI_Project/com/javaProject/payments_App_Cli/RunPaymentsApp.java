package com.javaProject.payments_App_Cli;

import java.sql.SQLException;
import java.util.Date;
import java.util.Scanner;

import com.javaProject.payments_App_Cli.dao.PaymentAppCliDAO;
import com.javaProject.payments_App_Cli.entity.AcctType;
import com.javaProject.payments_App_Cli.entity.BankAccount;
import com.javaProject.payments_App_Cli.entity.Transaction;
import com.javaProject.payments_App_Cli.entity.TxnType;
import com.javaProject.payments_App_Cli.entity.User;
import com.javaProject.payments_App_Cli.entity.UserOperations;
import com.javaProject.payments_App_Cli.entity.Wallet;

public class RunPaymentsApp {

	public static int currUserId = -1;

	public static void main(String[] args) throws SQLException {
		// TODO Auto-generated method stub

		int SelectedOption = 0;

		while (true) {
			System.out.println("Please choose an option from below: ");
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
			System.out.println("Enter an option");
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
			PaymentAppCliDAO dao = new PaymentAppCliDAO();

			if (op.equalsIgnoreCase("1")) {
				System.out.println("User selected registartion");
				Registration();
			} else if (op.equalsIgnoreCase("2")) {
				System.out.println("Login");
				try {
					login();
				} catch (SQLException e) {
					e.printStackTrace();
				}
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
				System.out.println("List of users: ");
				dao.printUserDetails();

			} else if (op.equalsIgnoreCase("6")) {
				if (currUserId != -1) {
					System.out.println("Current User: ");
					dao.printCurrUserDetails(currUserId);
				}
			} else if (op.equalsIgnoreCase("7")) {
				if (currUserId != -1) {
					System.out.println("List of users bank accounts: ");
					dao.printUserBankAcctDetails();
				}
			} else if (op.equalsIgnoreCase("8")) {
				if (currUserId != -1) {
					System.out.println("Delete bank account");
					System.out.println("Enter Bank Account Number: ");
					int accNum = sc.nextInt();
					dao.deleteUserBankAccount(accNum);
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
		u = uop.doUserRegistration(fname, lname, phnum, dob, addr, pswd);
		PaymentAppCliDAO dao = new PaymentAppCliDAO();

		try {
			dao.storeUserDetails(u);
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public static boolean login() throws SQLException {
		Scanner sc = new Scanner(System.in);

		System.out.println("Enter Your User ID: ");
		int uId = sc.nextInt();
		System.out.println("Enter Your Password: ");
		String pswd = sc.next();
		PaymentAppCliDAO dao = new PaymentAppCliDAO();

		if (dao.verifyUserLogin(uId, pswd)) {
			currUserId = uId;
			return true;
		}
		return false;
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

		System.out.println("Bank Account Pin: ");
		String baAcctPin = sc.next();

		BankAccount ba = new BankAccount();
		ba.setBankAcctNumber(baAcctNum);
		ba.setBankAcctIFSC(baIFSCCode);
		ba.setBankAcctBankName(baName);
		ba.setBankAcctType(baAcctType);
		ba.setBankAcctPin(baAcctPin);
		ba.setUserId(currUserId);

		PaymentAppCliDAO dao = new PaymentAppCliDAO();
		try {
			dao.storeUserBankAcctDetails(ba);
		} catch (SQLException e) {
			e.printStackTrace();
		}
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
					Scanner scan = new Scanner(System.in);
					System.out.println("Enter an amount: ");
					double amount = scan.nextDouble();
					PaymentAppCliDAO dao = new PaymentAppCliDAO();
					
					try {
						dao.addMoneyToWallet(amount, currUserId);
					} catch (SQLException e) {
						e.printStackTrace();
					}
					
					System.out.println();
				} else {
					System.out.println("Please check whether user is login or not!!");
				}

				break;

			case 2:
				if (currUserId != -1) {
					PaymentAppCliDAO dao = new PaymentAppCliDAO();
					try {
						dao.checkCurrWalletbalance(currUserId);
					} catch (SQLException e) {
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

	public static void transactionOperation() throws SQLException {
		if (currUserId != -1) {
			Scanner sc = new Scanner(System.in);
			Transaction transaction = new Transaction();
			Date date = new Date();
			PaymentAppCliDAO dao = new PaymentAppCliDAO();
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
				System.out.println("Enter an amount to transfer: ");
				double txnAmount = sc.nextDouble();
				transaction.setTxnAmount(txnAmount);
				transaction.setTxnDate(date);
				transaction.setTxnId(date.getTime());

				switch (transferType) {
				case 1:
					int sender = currUserId;
					transaction.setSrcWallet(sender);
					System.out.println("Enter receivers UserId : ");
					int receiver = sc.nextInt();
					transaction.setDestWallet(receiver);

					if (dao.verifyWalletBalance(currUserId, txnAmount)) {
						
						result = dao.doTxnWalletToWallet(sender, receiver, transaction.getTransactionType(), txnAmount);

						if (result) {
							System.out.println("Transaction Successfull !! \n");
						} else {
							System.out.println("Transaction Failed \n");
						}
					} else {
						System.out.println("Insufficient Balance!! \n");
					}
					break;

				case 2:
					System.out.println("Enter senders bankaccount number : ");
					String senderAcctNum = sc.next();

					if (dao.verifyBankAcctNum(senderAcctNum)) {
						System.out.println("Enter receivers bankaccount number : ");
						String receiverAcctNum = sc.next();

						if (dao.verifyBankAcctNum(receiverAcctNum)) {

							if (dao.verifyBankBalance(senderAcctNum, txnAmount)) {

								result = dao.doTxnBankToBank(senderAcctNum, receiverAcctNum,
										transaction.getTransactionType(), txnAmount);

								if (result) {
									System.out.println("Transaction Successfull !! \n");
								} else {
									System.out.println("Transaction Failed \n");
								}
							} else {
								System.out.println("Insufficient Balance!! \n");
							}

						} else {
							System.out.println("Enter a valid Account number!! \n");
						}

					} else {
						System.out.println("Enter a valid Account number!! \n");
					}
					break;

				case 3:
					System.out.println("Enter senders bankaccount number : ");
					String senderActNum = sc.next();

					if (dao.verifyBankAcctNum(senderActNum)) {
						System.out.println("Enter receivers User ID : ");
						int receiverUserId = sc.nextInt();

						if (dao.verifyUserId(receiverUserId)) {

							if (dao.verifyBankBalance(senderActNum, txnAmount)) {

								result = dao.doTxnBankToWallet(senderActNum, receiverUserId,
										transaction.getTransactionType(), txnAmount);

								if (result) {
									System.out.println("Transaction Successfull !! \n");
								} else {
									System.out.println("Transaction Failed \n");
								}
							} else {
								System.out.println("Insufficient Balance!! \n");
							}

						} else {
							System.out.println("Enter a valid User Id !! \n");
						}

					} else {
						System.out.println("Enter a valid Account number!! \n");
					}
					break;

				case 4:
					int senderUserId = currUserId;
					transaction.setSrcWallet(senderUserId);

					System.out.println("Enter receivers bankaccount number : ");
					String receiverAcctNum = sc.next();

					if (dao.verifyBankAcctNum(receiverAcctNum)) {

						if (dao.verifyWalletBalance(currUserId, txnAmount)) {

							result = dao.doTxnWalletToBank(senderUserId, receiverAcctNum,
									transaction.getTransactionType(), txnAmount);

							if (result) {
								System.out.println("Transaction Successfull !! \n");
							} else {
								System.out.println("Transaction Failed \n");
							}
						} else {
							System.out.println("Insufficient Balance!! \n");
						}

					} else {
						System.out.println("Enter a valid Account number!! \n");
					}
					break;

				default:
					System.out.println("Please enter correct option\n");
				}

			} else if (option == 2) {
				transaction.setTransactionType(TxnType.CREDIT);
				int userId = currUserId;
				boolean result;
				if (userId == currUserId) {
					System.out.println("Enter an account number to credit amount: ");
					String acctNum = sc.next();
					if (dao.verifyBankAcctNum(acctNum)) {
						System.out.println("Enter an amount to credit : ");
						double amount = sc.nextDouble();

						result = dao.creditAmountToAccount(acctNum, transaction.getTransactionType(), amount);

						if (result) {
							System.out.println("Transaction Successfull !! \n");
						} else {
							System.out.println("Transaction Failed \n");
						}
					}
				}

			}

		}
	}

	public static void logout() {
		currUserId = -1;
	}

}