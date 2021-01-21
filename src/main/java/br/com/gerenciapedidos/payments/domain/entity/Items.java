package br.com.gerenciapedidos.payments.domain.entity;

public class Items {

    private Integer amount;
    private String name;
    private Integer value;

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Items{" +
                "amount=" + amount +
                ", name='" + name + '\'' +
                ", value=" + value +
                '}';
    }
}
