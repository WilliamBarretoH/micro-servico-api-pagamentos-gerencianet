package br.com.gerenciapedidos.payments.api.resource;

import br.com.gerencianet.gnsdk.Gerencianet;
import br.com.gerencianet.gnsdk.exceptions.GerencianetException;
import br.com.gerenciapedidos.payments.domain.entity.Item;
import br.com.gerenciapedidos.payments.domain.entity.Payment;
import br.com.gerenciapedidos.payments.domain.entity.Plan;
import br.com.gerenciapedidos.payments.domain.entity.SubscriptionRequest;
import br.com.gerenciapedidos.payments.service.interfaces.CredentialsService;
import br.com.gerenciapedidos.payments.service.interfaces.ItemService;
import br.com.gerenciapedidos.payments.service.interfaces.JsonService;
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

@PropertySource("classpath:application.properties")
@RestController
@RequestMapping("/v1")
@CrossOrigin(origins = "*")
public class SubscriptionPaymentController {

    @Autowired
    CredentialsService credentialsService;

    @Autowired
    ItemService itemService;

    @Autowired
    JsonService jsonService;

    @Value("${app.id}")
    private String client_id;
    @Value("${app.secret}")
    private String client_secret;
    @Value("${app.sandbox}")
    private String sandbox;

    @ApiOperation(value = "realiza pagamento por assinatura")
    @PostMapping(value = "/planos/novo", produces = "application/json", consumes = "application/json")
    public ResponseEntity<?> criarPlano(@RequestBody Plan plan){

        try {

            JSONObject options = credentialsService.buildCredentials(client_id, client_secret, sandbox);

            Gerencianet gn = new Gerencianet(options);
            JSONObject body = new JSONObject();
            body.put("name", plan.getName());
            body.put("interval", plan.getInterval());
            body.put("repeats", plan.getRepeats());

            JSONObject response = gn.call("createPlan", new HashMap<String, String>(), body);
            System.out.println(response.toString());

            return new ResponseEntity(response.toString(), HttpStatus.CREATED);

        }catch (GerencianetException e){
            System.out.println(e.getCode());
            System.out.println(e.getError());
            System.out.println(e.getErrorDescription());
        } catch (Exception ex){
            System.out.println(ex.getMessage());

        }

        return new ResponseEntity(HttpStatus.OK);
    }

    @ApiOperation(value = "Lista todos os planos de assinatura")
    @GetMapping(value = "/planos", produces = "application/json")
    public ResponseEntity<?> listaPlano(){

        JSONObject options = credentialsService.buildCredentials(client_id, client_secret, sandbox);
        HashMap<String, String> params = new HashMap<String, String>();
        try {
            Gerencianet gn = new Gerencianet(options);
            JSONObject plans = gn.call("getPlans", params, new JSONObject());
            return new ResponseEntity(plans.toString(), HttpStatus.OK);
        }catch (GerencianetException e){
            System.out.println(e.getCode());
            System.out.println(e.getError());
            System.out.println(e.getErrorDescription());
        }catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return new ResponseEntity(HttpStatus.OK);
    }

    @ApiOperation(value = "Cria e paga assinatura por cartao de credito vinculando-a em um plano")
    @PostMapping(value = "/pagamento/assinatura", produces = "application/json", consumes = "application/json")
    public ResponseEntity<?> criarAssinatura(@RequestBody SubscriptionRequest subscriptionRequest){

        try {
            JSONObject options = credentialsService.buildCredentials(client_id, client_secret, sandbox);
            JSONArray itemsArray = itemService.buildItemToArray(subscriptionRequest.getItems());
            JSONObject body = new JSONObject();
            body.put("items", itemsArray);
            HashMap<String, String> plan_id = new HashMap<String, String>();
            plan_id.put("id", "7172");
            Gerencianet gn = new Gerencianet(options);
            JSONObject subscription = gn.call("createSubscription", plan_id, body);
            if(subscription != null){
                body = new JSONObject();
                body.put("payment", new JSONObject(subscriptionRequest.getPayment()));
                HashMap<String, String> subscription_id = new HashMap<String, String>();
                subscription_id.put("id", jsonService.getIdFromJsonResponse(subscription, "data", "subscription_id"));
                JSONObject payResponse = gn.call("paySubscription", subscription_id, body);
                return new ResponseEntity(payResponse.toString(), HttpStatus.CREATED);
            }else{
                return new ResponseEntity(HttpStatus.NO_CONTENT);
            }

        }catch (GerencianetException e){
            System.out.println(e.getCode());
            System.out.println(e.getError());
            System.out.println(e.getErrorDescription());
        }catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return new ResponseEntity(HttpStatus.OK);
    }

}
