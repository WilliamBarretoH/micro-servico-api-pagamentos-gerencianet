package br.com.gerenciapedidos.payments.domain.entity;

import com.fasterxml.jackson.annotation.JsonRootName;
import io.swagger.annotations.ApiModelProperty;

@JsonRootName("credit_card")
public class CreditCard {

    @ApiModelProperty(value = "Numero de parcelas")
    private Integer installments;
    @ApiModelProperty(value = "Token gerado pela API para confirmar transacao")
    private String payment_token;
    @ApiModelProperty(value = "desconto do pagamento")
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
