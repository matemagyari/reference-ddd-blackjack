package org.home.blackjack.wallet.infrastructure.rest.transaction;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class TransactionResponse {

	private String id;
	private String originalAmount;
	private String updatedAmount;

	public TransactionResponse() {}
	
	public TransactionResponse(String id, String originalAmount, String updatedAmount) {
		this.id = id;
		this.originalAmount = originalAmount;
		this.updatedAmount = updatedAmount;
	}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOriginalAmount() {
        return originalAmount;
    }

    public void setOriginalAmount(String originalAmount) {
        this.originalAmount = originalAmount;
    }

    public String getUpdatedAmount() {
        return updatedAmount;
    }

    public void setUpdatedAmount(String updatedAmount) {
        this.updatedAmount = updatedAmount;
    }
	
	
}
