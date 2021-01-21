package br.com.gerenciapedidos.payments.domain.entity;

import java.util.List;

public class CardRequest {

    private Payment payment;
    private List<Items> items;
    private Metadata metadata;

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

    public Metadata getMetadata() {
        return metadata;
    }

    public void setMetadata(Metadata metadata) {
        this.metadata = metadata;
    }
}
