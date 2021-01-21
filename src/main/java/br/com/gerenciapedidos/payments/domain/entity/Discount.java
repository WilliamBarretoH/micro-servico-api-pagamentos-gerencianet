package br.com.gerenciapedidos.payments.domain.entity;

public class Discount {

    private String type;
    private Integer value;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }
}
