package br.com.payments.domain.entity;

public class PaymentBillet {

    private BankingBillet banking_billet;

    public BankingBillet getBanking_billet() {
        return banking_billet;
    }

    public void setBanking_billet(BankingBillet banking_billet) {
        this.banking_billet = banking_billet;
    }
}
