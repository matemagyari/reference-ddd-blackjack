package org.home.blackjack.wallet.infrastructure.rest;

public class TransactionRequest {

	private String amount;
	private String transactionId;
	private String transactionType;
	private String walletId;

	public TransactionRequest(String transactionId, String walletId, String transactionType, String amount) {
		this.transactionId = transactionId;
		this.walletId = walletId;
		this.transactionType = transactionType;
		this.amount = amount;
	}

	public String amount() {
		return amount;
	}

	public String transactionId() {
		return transactionId;
	}

	public String transactionType() {
		return transactionType;
	}
	
	public String walletId() {
		return walletId;
	}
}
