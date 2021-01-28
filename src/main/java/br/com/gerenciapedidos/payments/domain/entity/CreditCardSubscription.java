package br.com.gerenciapedidos.payments.domain.entity;

public class CreditCardSubscription {

    private String payment_token;
    private Customer customer;
    private Billing_address billing_address;

    public String getPayment_token() {
        return payment_token;
    }

    public void setPayment_token(String payment_token) {
        this.payment_token = payment_token;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Billing_address getBilling_address() {
        return billing_address;
    }

    public void setBilling_address(Billing_address billing_address) {
        this.billing_address = billing_address;
    }
}
