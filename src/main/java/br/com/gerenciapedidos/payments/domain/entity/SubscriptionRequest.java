package br.com.gerenciapedidos.payments.domain.entity;

import java.util.List;

public class SubscriptionRequest {

    private Payment payment;
    private List<Items> items;
    private Plan plan;

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    public List<Items> getItems() {
        return items;
    }

    public void setItems(List<Items> items) {
        this.items = items;
    }

    public Plan getPlan() {
        return plan;
    }

    public void setPlan(Plan plan) {
        this.plan = plan;
    }
}
