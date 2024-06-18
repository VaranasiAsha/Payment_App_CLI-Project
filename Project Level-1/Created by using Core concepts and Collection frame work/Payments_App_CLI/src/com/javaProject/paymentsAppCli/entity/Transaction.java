package com.javaProject.paymentsAppCli.entity;

import java.time.LocalDate;
import java.util.Date;

public class Transaction {

	private long txnId;
	private Date txnDate;
	private String Limit;
	private BankAccount txnSource;
	private BankAccount txnDestination;
	private TxnType transactionType;
	private double txnAmount;
	private String statement;
	
	private int userId;
	
	Wallet srcWallet;
	Wallet destWallet;
	public long getTxnId() {
		return txnId;
	}
	public void setTxnId(long l) {
		this.txnId = l;
	}
	public Date getTxnDate() {
		return txnDate;
	}
	public void setTxnDate(Date date) {
		this.txnDate = date;
	}
	public String getLimit() {
		return Limit;
	}
	public void setLimit(String limit) {
		Limit = limit;
	}
	public BankAccount getTxnSource() {
		return txnSource;
	}
	public void setTxnSource(BankAccount source2) {
		this.txnSource = source2;
	}
	public BankAccount getTxnDestination() {
		return txnDestination;
	}
	public void setTxnDestination(BankAccount txnDestination) {
		this.txnDestination = txnDestination;
	}
	public TxnType getTransactionType() {
		return transactionType;
	}
	public void setTransactionType(TxnType transactionType) {
		this.transactionType = transactionType;
	}
	public double getTxnAmount() {
		return txnAmount;
	}
	public void setTxnAmount(double txnAmount) {
		this.txnAmount = txnAmount;
	}
	public String getStatement() {
		return statement;
	}
	public void setStatement(String statement) {
		this.statement = statement;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public Wallet getSrcWallet() {
		return srcWallet;
	}
	public void setSrcWallet(Wallet srcWallet) {
		this.srcWallet = srcWallet;
	}
	public Wallet getDestWallet() {
		return destWallet;
	}
	public void setDestWallet(Wallet destWallet) {
		this.destWallet = destWallet;
	}

	public String toString(){
		
		return "---" +": Transaction ID: " + txnId + " Date: " + txnDate 
				+ " Amount: "+txnAmount + " Source Type: " + txnSource
				+" Amount sent to UserId: "+ userId + " Destination Type: "
				+ txnDestination ;
	}
}




