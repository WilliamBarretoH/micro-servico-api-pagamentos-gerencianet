package br.com.gerenciapedidos.payments.domain.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class Payment {

    private CreditCard credit_card;

    @JsonProperty("credit_card")
    public CreditCard getCredit_card() {
        return credit_card;
    }

    public void setCredit_card(CreditCard credit_card) {
        this.credit_card = credit_card;
    }

    @Override
    public String toString() {
        return "Payment{" +
                "creditCard=" + credit_card +
                '}';
    }
}
