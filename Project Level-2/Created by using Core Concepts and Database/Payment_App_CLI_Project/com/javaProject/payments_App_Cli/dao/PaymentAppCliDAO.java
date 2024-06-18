package com.javaProject.payments_App_Cli.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.javaProject.payments_App_Cli.entity.BankAccount;
import com.javaProject.payments_App_Cli.entity.TxnType;
import com.javaProject.payments_App_Cli.entity.User;

public class PaymentAppCliDAO {

	public static void storeUserDetails(User u) throws SQLException {

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/Payments_App_CLI", "root",
					"root");
			Statement st = con.createStatement();
			String storeUserDetailsQuery = "insert into User_info(First_Name,Last_Name,Phone_Number,Date_Of_Birth,Address,Password) "
					+ "values('" + u.getFirstName() + "','" + u.getLastName() + "','" + u.getPhoneNumber() + "','"
					+ u.getDateOfBirth() + "','" + u.getAddress() + "','" + u.getPassword() + "')";

			int rs = st.executeUpdate(storeUserDetailsQuery);
			System.out.println(rs + "row/s effected.\n");

			con.close();

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public static void printUserDetails() throws SQLException {
		User u = new User();
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/Payments_App_CLI", "root",
					"root");
			Statement st = con.createStatement();
			String printUserDetailsQuery = "select * from User_info";
			ResultSet rs = st.executeQuery(printUserDetailsQuery);
			while (rs.next()) {
				System.out.println(rs.getInt("Id") + " : " + rs.getString("First_Name") + " : "
						+ rs.getString("Last_Name") + " : " + rs.getLong("Phone_Number") + " : "
						+ rs.getString("Date_Of_Birth") + " : " + rs.getString("Address") + " : "
						+ rs.getString("Password") + " : " + rs.getDouble("Wallet_Balance"));
			}
			con.close();

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public static boolean verifyUserLogin(int uId, String pswd) throws SQLException {
		User u = new User();
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/Payments_App_CLI", "root",
					"root");
			Statement st = con.createStatement();

			String verifyUserLoginQuery = "SELECT Id,Password FROM User_info WHERE Id = " + uId + " AND Password = '"
					+ pswd + "' ";

			ResultSet rs = st.executeQuery(verifyUserLoginQuery);
			while (rs.next()) {
				System.out.println("Login Successfull \n");

				con.close();
				return true;
			}
			return false;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		return false;
	}

	public static void storeUserBankAcctDetails(BankAccount ba) throws SQLException {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/Payments_App_CLI", "root",
					"root");
			Statement st = con.createStatement();
			String storeUserBankAcctDetailsQuery = "insert into BankAccount_Details(Account_Number,Acct_IFSC,Bank_Name,Bank_Acct_Pin,Acct_Type,User_Id,BanK_Balance) "
					+ "values('" + ba.getBankAcctNumber() + "','" + ba.getBankAcctIFSC() + "','"
					+ ba.getBankAcctBankName() + "','" + ba.getBankAcctPin() + "','" + ba.getBankAcctType() + "','"
					+ ba.getUserId() + "','" + ba.getBankBalance() + "')";

			int rs = st.executeUpdate(storeUserBankAcctDetailsQuery);
			System.out.println(rs + " row/s effected. \n");

			con.close();

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

	}

	public static void printUserBankAcctDetails() throws SQLException {
		BankAccount ba = new BankAccount();
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/Payments_App_CLI", "root",
					"root");
			Statement st = con.createStatement();
			String printUserBankAcctDetailsQuery = "SELECT * FROM BankAccount_Details";
			ResultSet rs = st.executeQuery(printUserBankAcctDetailsQuery);

			while (rs.next()) {
				System.out.println(rs.getInt("Id") + " : " + rs.getString("Account_Number") + " : "
						+ rs.getString("Acct_IFSC") + " : " + rs.getString("Bank_Name") + " : "
						+ rs.getString("Bank_Acct_Pin") + " : " + rs.getString("Acct_Type") + " : "
						+ rs.getString("User_Id") + " : " + rs.getDouble("BanK_Balance"));
			}
			con.close();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

	}

	public static void deleteUserBankAccount(int accNum) throws SQLException {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/Payments_App_CLI", "root",
					"root");
			String deleteUserBankAcctQuery = "DELETE FROM BankAccount_Details WHERE Account_Number = ? ";
			PreparedStatement preSt = con.prepareStatement(deleteUserBankAcctQuery);
			preSt.setInt(1, accNum);
			int result = preSt.executeUpdate();
			System.out.println(result + " record deleted.\n");
			con.close();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

	}

	public static void printCurrUserDetails(int userId) throws SQLException {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/Payments_App_CLI", "root",
					"root");
			Statement st = con.createStatement();
			String printCurrUserDetailsQuery = "SELECT * FROM User_info WHERE Id = " + userId + " ";
			ResultSet rs = st.executeQuery(printCurrUserDetailsQuery);

			while (rs.next()) {
				System.out.println(rs.getInt("Id") + " : " + rs.getString("First_Name") + " : "
						+ rs.getString("Last_Name") + " : " + rs.getLong("Phone_Number") + " : "
						+ rs.getString("Date_Of_Birth") + " : " + rs.getString("Address") + " : "
						+ rs.getString("Password") + " : " + rs.getDouble("Wallet_Balance") + " \n ");
			}
			con.close();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public static void addMoneyToWallet(double amount, int userId) throws SQLException {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/Payments_App_CLI", "root",
					"root");
			Statement st = con.createStatement();
			String addMoneyToWalletQuery = "UPDATE User_info SET Wallet_balance = Wallet_Balance + " + amount
					+ " WHERE Id=" + userId + " ";
			int result = st.executeUpdate(addMoneyToWalletQuery);
			System.out.println(result + " row/s effected. \n");
			con.close();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public static void checkCurrWalletbalance(int userId) throws SQLException {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/Payments_App_CLI", "root",
					"root");
			Statement st = con.createStatement();
			String checkCurrWalletbalanceQuery = "SELECT Id, First_Name, Wallet_Balance FROM User_info WHERE Id = "
					+ userId + " ";
			ResultSet rs = st.executeQuery(checkCurrWalletbalanceQuery);
			rs.next();
			System.out.println(rs.getInt("Id") + " : " + rs.getString("First_Name") + " : "
					+ rs.getDouble("Wallet_Balance") + "\n");
			con.close();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public static boolean verifyWalletBalance(int userId, double amount) throws SQLException {

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/Payments_App_CLI", "root",
					"root");
			Statement st = con.createStatement();
			String verifyWalletBalanceQuery = "SELECT Wallet_Balance FROM User_info WHERE Id = " + userId + " ";
			ResultSet rs = st.executeQuery(verifyWalletBalanceQuery);
			rs.next();
			double walletBalance = rs.getDouble("Wallet_Balance");
			if (amount < walletBalance) {
				return true;
			}
			con.close();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		return false;
	}

	public static boolean verifyBankBalance(String senderAcctNum, double amount) throws SQLException {

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/Payments_App_CLI", "root",
					"root");
			Statement st = con.createStatement();
			String verifyBankBalanceQuery = "SELECT BanK_Balance FROM BankAccount_Details WHERE Account_Number = " + senderAcctNum
					+ " ";
			ResultSet rs = st.executeQuery(verifyBankBalanceQuery);
			rs.next();
			double bankBalance = rs.getDouble("BanK_Balance");
			if (amount < bankBalance) {
				return true;
			}
			con.close();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		return false;
	}

	public static boolean doTxnWalletToWallet(int sender, int receiver, TxnType type, double amount)
			throws SQLException {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/Payments_App_CLI", "root",
					"root");
			Statement st = con.createStatement();

			String senderQuery = "UPDATE User_info SET Wallet_balance = Wallet_Balance - " + amount + " WHERE Id="
					+ sender + " ";
			String receiverQuery = "UPDATE User_info SET Wallet_balance = Wallet_Balance + " + amount + " WHERE Id="
					+ receiver + " ";

			int senderRs = st.executeUpdate(senderQuery);
			int receiverRs = st.executeUpdate(receiverQuery);
			
			con.close();

			return true;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return false;
	}

	public static boolean verifyBankAcctNum(String acctNum) throws SQLException {
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/Payments_App_CLI", "root",
					"root");
			Statement st = con.createStatement();
			String verifyBankAcctNumQuery = "SELECT Account_Number FROM BankAccount_Details WHERE Account_Number ="+acctNum+" ";
			ResultSet rs = st.executeQuery(verifyBankAcctNumQuery);
			rs.next();
			con.close();
			return true;
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
	public static boolean doTxnBankToBank(String senderAcctNum, String receiverAcctNum, TxnType txnType, double amount) throws SQLException {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/Payments_App_CLI", "root",
					"root");
			Statement st = con.createStatement();

			String senderQuery = "UPDATE BankAccount_Details SET BanK_Balance = BanK_Balance - " + amount + " WHERE Account_Number="
					+ senderAcctNum + " ";
			String receiverQuery = "UPDATE BankAccount_Details SET BanK_Balance = BanK_Balance + " + amount + " WHERE Account_Number="
					+ receiverAcctNum + " ";

			int senderRs = st.executeUpdate(senderQuery);
			int receiverRs = st.executeUpdate(receiverQuery);
			con.close();

			return true;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return false;
	}

	public static boolean verifyUserId(int userId) throws SQLException {
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/Payments_App_CLI", "root",
					"root");
			Statement st = con.createStatement();
			String verifyUserIdQuery = "SELECT Id FROM User_info WHERE Id ="+userId+" ";
			ResultSet rs = st.executeQuery(verifyUserIdQuery);
			rs.next();
			con.close();
			return true;
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		
		return false;
	}
	
	public static boolean doTxnBankToWallet(String senderAcctNum, int receiverUserId, TxnType txnType, double amount) throws SQLException {
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/Payments_App_CLI", "root",
					"root");
			Statement st = con.createStatement();

			String senderQuery = "UPDATE BankAccount_Details SET BanK_Balance = BanK_Balance - " + amount + " WHERE Account_Number="
					+ senderAcctNum + " ";
			String receiverQuery = "UPDATE User_info SET Wallet_Balance = Wallet_Balance + " + amount + " WHERE Id="
					+ receiverUserId + " ";

			int senderRs = st.executeUpdate(senderQuery);
			int receiverRs = st.executeUpdate(receiverQuery);
			con.close();

			return true;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return false;
	}

	public static boolean doTxnWalletToBank(int senderUserId, String receiverAcctNum, TxnType txnType, double amount) throws SQLException {
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/Payments_App_CLI", "root",
					"root");
			Statement st = con.createStatement();

			String senderQuery = "UPDATE User_info SET Wallet_Balance = Wallet_Balance - " + amount + " WHERE Id="
					+ senderUserId + " ";
			String receiverQuery = "UPDATE BankAccount_Details SET BanK_Balance = BanK_Balance + " + amount + " WHERE Account_Number="
					+ receiverAcctNum + " ";

			int senderRs = st.executeUpdate(senderQuery);
			int receiverRs = st.executeUpdate(receiverQuery);
			con.close();

			return true;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return false;
	}

	public static boolean creditAmountToAccount(String acctNum, TxnType transactionType, double amount)  throws SQLException{
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/Payments_App_CLI", "root",
					"root");
			Statement st = con.createStatement();
			String creditAmountToAccountQuery = "UPDATE BankAccount_Details SET BanK_Balance = BanK_Balance + " + amount + " WHERE Account_Number="
					+ acctNum + " ";

			int rs = st.executeUpdate(creditAmountToAccountQuery);
			con.close();

			return true;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return false;
	}

}
