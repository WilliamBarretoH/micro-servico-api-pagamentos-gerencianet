package br.com.payments.domain.entity;

import org.springframework.beans.factory.annotation.Value;

public class Options {

    private String client_id = "Client_Id_6e719da62a14c266dc61cb39d13269e8c82d7db9";
    private String client_secret = "Client_Id_6e719da62a14c266dc61cb39d13269e8c82d7db9";
    private boolean sandbox = true;

    public String getClient_id() {
        return client_id;
    }

    public void setClient_id(String client_id) {
        this.client_id = client_id;
    }

    public String getClient_secret() {
        return client_secret;
    }

    public void setClient_secret(String client_secret) {
        this.client_secret = client_secret;
    }

    public boolean isSandbox() {
        return sandbox;
    }

    public void setSandbox(boolean sandbox) {
        this.sandbox = sandbox;
    }
}
