package br.com.payments.api.resource;

import br.com.gerencianet.gnsdk.Gerencianet;
import br.com.gerencianet.gnsdk.exceptions.GerencianetException;
import br.com.payments.domain.entity.CardRequest;
import br.com.payments.service.interfaces.CredentialsService;
import br.com.payments.service.interfaces.JsonService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
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
    public static void main(String[] args) {
        /* *********  Set credentials parameters ******** */
        JSONObject options = new JSONObject();
        options.put("client_id", "Client_Id_6e719da62a14c266dc61cb39d13269e8c82d7db9");
        options.put("client_secret", "Client_Id_6e719da62a14c266dc61cb39d13269e8c82d7db9");
        options.put("sandbox", true);

        /* ************************************************* */

        String paymentToken = "32add25a335ff3f588e42055486b3156253b7b75";

        // items
        JSONArray items = new JSONArray();
        JSONObject item1 = new JSONObject();
        item1.put("name", "Item 1");
        item1.put("amount", 1);
        item1.put("value", 600);
        JSONObject item2 = new JSONObject("{\"name\":\"Item 2\", \"amount\":1, \"value\":1000}");
        items.put(item1);
        items.put(item2);

        //customer
        JSONObject customer = new JSONObject();
        customer.put("name", "Gorbadoc Oldbuck");
        customer.put("cpf", "94271564656");
        customer.put("phone_number", "5144916523");
        customer.put("email", "gorbadoc.oldbuck@gerencianet.com.br");
        customer.put("birth", "1990-05-04");

        // endereço do comprador
        JSONObject billingAddress = new JSONObject();
        billingAddress.put("street", "Av. JK");
        billingAddress.put("number", 909);
        billingAddress.put("neighborhood", "Bauxita");
        billingAddress.put("zipcode", "35400000");
        billingAddress.put("city", "Ouro Preto");
        billingAddress.put("state", "MG");

        //URL de notificações
        JSONObject metadata = new JSONObject();
        metadata.put("notification_url", "https://SuaUrl/16rpx6y1");
        metadata.put("custom_id", "Id_0007");

        //desconto
        JSONObject discount = new JSONObject();
        discount.put("type","currency");
        discount.put("value",599);


        JSONObject creditCard = new JSONObject();
        creditCard.put("installments", 1);
        creditCard.put("billing_address", billingAddress);
        creditCard.put("payment_token", paymentToken);
        creditCard.put("customer", customer);
        creditCard.put("discount", discount);

        JSONObject payment = new JSONObject();
        payment.put("credit_card", creditCard);

        JSONObject body = new JSONObject();
        body.put("payment", payment);
        body.put("items", items);
        body.put("metadata", metadata);

        try {
            Gerencianet gn = new Gerencianet(options);
            JSONObject response = gn.call("oneStep", new HashMap<>(), body);
            System.out.println(response);
        }catch (GerencianetException e){
            System.out.println(e.getCode());
            System.out.println(e.getError());
            System.out.println(e.getErrorDescription());
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
