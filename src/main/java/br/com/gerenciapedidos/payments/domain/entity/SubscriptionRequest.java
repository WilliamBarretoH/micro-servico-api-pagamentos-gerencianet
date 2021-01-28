package br.com.gerenciapedidos.payments.domain.entity;

import java.util.List;

public class SubscriptionRequest {

    private PaymentSubscription payment;
    private Item items;

    public PaymentSubscription getPayment() {
        return payment;
    }

    public void setPayment(PaymentSubscription payment) {
        this.payment = payment;
    }

    public Item getItems() {
        return items;
    }

    public void setItems(Item items) {
        this.items = items;
    }
}
