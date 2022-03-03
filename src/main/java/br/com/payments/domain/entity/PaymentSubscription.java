package br.com.payments.domain.entity;

public class PaymentSubscription {

    private CreditCardSubscription credit_card;

    public CreditCardSubscription getCredit_card() {
        return credit_card;
    }

    public void setCredit_card(CreditCardSubscription credit_card) {
        this.credit_card = credit_card;
    }
}
