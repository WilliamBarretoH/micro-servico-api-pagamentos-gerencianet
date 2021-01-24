package br.com.gerenciapedidos.payments.api.resource;

import br.com.gerencianet.gnsdk.Gerencianet;
import br.com.gerencianet.gnsdk.exceptions.GerencianetException;
import br.com.gerenciapedidos.payments.domain.entity.CardRequest;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@PropertySource("classpath:application.properties")
@RestController
@RequestMapping("v1")
public class PaymentController {

    //variaveis de ambiente - application.properties
    @Value("${app.id}")
    private String client_id;
    @Value("${app.secret}")
    private String client_secret;
    @Value("${app.sandbox}")
    private String sandbox;

    @PostMapping(value = "/charge", produces = "application/json")
    public ResponseEntity<?> createCharge(@RequestBody CardRequest cardRequest){


        try {
            //autenticacao
            JSONObject options = new JSONObject();
            options.put("client_id", client_id);
            options.put("client_secret", client_secret);
            options.put("sandbox", sandbox);

            //realiza pagamento da transacao
            Gerencianet gn = new Gerencianet(options);
            JSONObject body = new JSONObject();
            body.put("items", new JSONArray(cardRequest.getItems()));
            //endpoint para criacao da transacao
            JSONObject response = gn.call("createCharge", new HashMap<String, String>(), body);
            if(response != null) {
                System.out.println("entrou na rota");
                System.out.println(response);
                //acessando o charge_id do response da API
                System.out.println(response.getJSONObject("data").getInt("charge_id"));
                System.out.println("Items: "+cardRequest.getItems().toString());
                System.out.println("Payment: "+cardRequest.getPayment().toString());
                body = new JSONObject();
                body.put("payment", new JSONObject(cardRequest.getPayment()));
                HashMap<String, String> params = new HashMap<String, String>();
                params.put("id", String.valueOf(response.getJSONObject("data").getInt("charge_id")));
                System.out.println(params.toString());
                //endpoint para pagamento da transacao
                JSONObject responsePayCharge = gn.call("payCharge", params, body);


                return new ResponseEntity(responsePayCharge.toString(), HttpStatus.CREATED);
            }else {
                return new ResponseEntity(HttpStatus.NO_CONTENT);
            }

        } catch(GerencianetException e) {
            System.out.println(e.getCode());
            System.out.println(e.getError());
            System.out.println(e.getErrorDescription());
        } catch(Exception ex) {
            System.out.println(ex.getMessage());
        }
            return new ResponseEntity(HttpStatus.OK);
    }
}
