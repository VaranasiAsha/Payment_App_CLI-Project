package com.javaProject.payments_App_Cli.entity;

public class BankAccount {

	private String bankAcctNumber;
	private String bankAcctIFSC;
	private String bankAcctBankName;
	private String bankAcctType;
	private String bankAcctPin;
	private double bankBalance;

	public double getBankBalance() {
		return bankBalance;
	}

	public void setBankBalance(double bankBalance) {
		this.bankBalance = bankBalance;
	}


	private int UserId;

	public String getBankAcctNumber() {
		return bankAcctNumber;
	}

	public void setBankAcctNumber(String bankAcctNumber) {
		this.bankAcctNumber = bankAcctNumber;
	}

	public String getBankAcctIFSC() {
		return bankAcctIFSC;
	}

	public void setBankAcctIFSC(String bankAcctIFSC) {
		this.bankAcctIFSC = bankAcctIFSC;
	}

	public String getBankAcctBankName() {
		return bankAcctBankName;
	}

	public void setBankAcctBankName(String bankAcctBankName) {
		this.bankAcctBankName = bankAcctBankName;
	}

	public String getBankAcctType() {
		return bankAcctType;
	}

	public void setBankAcctType(String bankAcctType) {
		this.bankAcctType = bankAcctType;
	}

	public String getBankAcctPin() {
		return bankAcctPin;
	}

	public void setBankAcctPin(String bankAcctPin) {
		this.bankAcctPin = bankAcctPin;
	}

	public int getUserId() {
		return UserId;
	}

	public void setUserId(int userId) {
		UserId = userId;
	}

	public String printBankAcctDetails() {
		return "[" + this.bankAcctNumber + "," + this.bankAcctIFSC + "," + this.bankAcctBankName + ","
				+ this.bankAcctType + "," + this.bankAcctPin + "]";
	}
}
