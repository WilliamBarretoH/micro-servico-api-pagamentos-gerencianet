package br.com.gerenciapedidos.payments.api.resource;

import br.com.gerencianet.gnsdk.Gerencianet;
import br.com.gerencianet.gnsdk.exceptions.GerencianetException;
import br.com.gerenciapedidos.payments.domain.entity.CardRequest;
import br.com.gerenciapedidos.payments.service.interfaces.CredentialsService;
import br.com.gerenciapedidos.payments.service.interfaces.JsonService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

@PropertySource("classpath:application.properties")
@RestController
@RequestMapping("/v1")
@Api(value = "")
@CrossOrigin(origins = "*")
public class CartaoDeCreditoController {

    private static Logger LOG = Logger.getLogger(CartaoDeCreditoController.class.getName());

    @Autowired(required = true)
    CredentialsService credentialsService;

    @Autowired
    JsonService jsonService;

    @ApiOperation(value = "Realiza pagamento por cartao de credito")
    @PostMapping(value = "/pagamento/cartao", produces = "application/json", consumes = "application/json")
    public ResponseEntity<?> pagarTransacaoComCartao(@RequestBody CardRequest cardRequest){

        try {
            JSONObject options = credentialsService.getCredentials();
            Gerencianet gn = new Gerencianet(options);
            JSONObject body = new JSONObject();
            body.put("items", new JSONArray(cardRequest.getItems()));
            JSONObject response = gn.call("createCharge", new HashMap<String, String>(), body);
            if(response != null) {
                body = new JSONObject();
                body.put("payment", new JSONObject(cardRequest.getPayment()));
                HashMap<String, String> charge_id = new HashMap<String, String>();
                charge_id.put("id", String.valueOf(response.getJSONObject("data").getInt("charge_id")));
                charge_id.put("id", jsonService.getIdFromJsonResponse(response, "data", "charge_id"));
                JSONObject responsePayCharge = gn.call("payCharge", charge_id, body);

                return new ResponseEntity(responsePayCharge.toString(), HttpStatus.CREATED);
            }else {
                return new ResponseEntity(HttpStatus.NO_CONTENT);
            }

        } catch(GerencianetException e) {
            LOG.log(Level.SEVERE, e.getMessage());
            return new ResponseEntity(e.getErrorDescription(),HttpStatus.INTERNAL_SERVER_ERROR);


        } catch(Exception ex) {
            LOG.log(Level.SEVERE, ex.getMessage());
        }
            return new ResponseEntity(HttpStatus.OK);
    }

}
