package br.com.gerenciapedidos.payments.service.providers;

import br.com.gerenciapedidos.payments.service.interfaces.JsonService;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

@Service
public class JsonServiceProvider implements JsonService {
    @Override
    public String getIdFromJsonResponse(JSONObject response, String jsonObject, String idVariable) {
        return String.valueOf(response.getJSONObject(jsonObject).getInt(idVariable));
    }
}
