package br.com.gerenciapedidos.payments.service.interfaces;

import org.json.JSONObject;

public interface JsonService {

    String getIdFromJsonResponse(JSONObject response, String jsonObject, String idVariable);

}
