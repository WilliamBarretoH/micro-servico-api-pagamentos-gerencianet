package br.com.payments.service.providers;

import br.com.payments.service.interfaces.CredentialsService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class CredentialsServiceProvider implements CredentialsService {

    @Value("${app.id}")
    private String client_id;
    @Value("${app.secret}")
    private String client_secret;
    @Value("${app.sandbox}")
    private String sandbox;

    @Override
    public JSONObject getCredentials() {
        JSONObject options = new JSONObject();
        options.put("client_id",client_id);
        options.put("client_secret", client_secret);
        options.put("sandbox", sandbox);
        return options;
    }
}
