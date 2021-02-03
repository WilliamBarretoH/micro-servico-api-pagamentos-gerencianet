package br.com.gerenciapedidos.payments.domain.entity;

public class BankingBillet {

    private String expire_at;
    private Customer customer;

    public String getExpire_at() {
        return expire_at;
    }

    public void setExpire_at(String expire_at) {
        this.expire_at = expire_at;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}
