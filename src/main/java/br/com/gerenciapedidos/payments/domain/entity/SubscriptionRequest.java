package br.com.gerenciapedidos.payments.domain.entity;

import java.util.List;

public class SubscriptionRequest {

    private Payment payment;
    private List<Item> items;
    private Plan plan;

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public Plan getPlan() {
        return plan;
    }

    public void setPlan(Plan plan) {
        this.plan = plan;
    }
}
