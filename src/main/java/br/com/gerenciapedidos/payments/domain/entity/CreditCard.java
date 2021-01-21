package br.com.gerenciapedidos.payments.domain.entity;

public class CreditCard {

    private Integer installments;
    private String payment_token;
    private Discount discount;
    private Billing_address billing_address;
    private Customer customer;

    public Integer getInstallments() {
        return installments;
    }

    public void setInstallments(Integer installments) {
        this.installments = installments;
    }

    public String getPayment_token() {
        return payment_token;
    }

    public void setPayment_token(String payment_token) {
        this.payment_token = payment_token;
    }

    public Discount getDiscount() {
        return discount;
    }

    public void setDiscount(Discount discount) {
        this.discount = discount;
    }

    public Billing_address getBilling_address() {
        return billing_address;
    }

    public void setBilling_address(Billing_address billing_address) {
        this.billing_address = billing_address;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}
