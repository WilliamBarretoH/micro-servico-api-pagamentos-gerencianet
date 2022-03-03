package br.com.payments.domain.entity;

import java.util.List;

public class BilletRequest {

    private List<Item> items;
    private PaymentBillet payment;

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public PaymentBillet getPayment() {
        return payment;
    }

    public void setPayment(PaymentBillet payment) {
        this.payment = payment;
    }
}
