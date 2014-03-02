package org.home.blackjack.wallet.infrastructure.rest;


public class TransactionResponse {

	private final String id;
	private final String originalAmount;
	private final String updatedAmount;

	public TransactionResponse(String id, String originalAmount, String updatedAmount) {
		this.id = id;
		this.originalAmount = originalAmount;
		this.updatedAmount = updatedAmount;
	}
}
