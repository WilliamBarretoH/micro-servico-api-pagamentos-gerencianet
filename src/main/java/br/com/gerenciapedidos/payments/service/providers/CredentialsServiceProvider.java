package br.com.gerenciapedidos.payments.service.providers;

import br.com.gerenciapedidos.payments.service.interfaces.CredentialsService;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

@Service
public class CredentialsServiceProvider implements CredentialsService {
    @Override
    public JSONObject buildCredentials(String client_id, String client_secret, String sandbox) {
        JSONObject options = new JSONObject();
        options.put("client_id",client_id);
        options.put("client_secret", client_secret);
        options.put("sandbox", sandbox);
        return options;
    }
}
