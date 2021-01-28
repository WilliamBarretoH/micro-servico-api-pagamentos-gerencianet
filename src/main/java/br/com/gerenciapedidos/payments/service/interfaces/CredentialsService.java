package br.com.gerenciapedidos.payments.service.interfaces;

import org.json.JSONObject;

public interface CredentialsService {

    JSONObject buildCredentials(String client_id, String client_secret, String sandbox);
}
