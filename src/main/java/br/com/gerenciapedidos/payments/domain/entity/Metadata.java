package br.com.gerenciapedidos.payments.domain.entity;

public class Metadata {

    private String custom_id;
    private String notification_url;

    public String getCustom_id() {
        return custom_id;
    }

    public void setCustom_id(String custom_id) {
        this.custom_id = custom_id;
    }

    public String getNotification_url() {
        return notification_url;
    }

    public void setNotification_url(String notification_url) {
        this.notification_url = notification_url;
    }

    @Override
    public String toString() {
        return "Metadata{" +
                "custom_id='" + custom_id + '\'' +
                ", notification_url='" + notification_url + '\'' +
                '}';
    }
}
