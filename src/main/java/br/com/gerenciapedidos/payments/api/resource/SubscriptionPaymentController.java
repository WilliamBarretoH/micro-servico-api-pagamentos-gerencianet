package br.com.gerenciapedidos.payments.api.resource;

import br.com.gerencianet.gnsdk.Gerencianet;
import br.com.gerencianet.gnsdk.exceptions.GerencianetException;
import br.com.gerenciapedidos.payments.domain.entity.Item;
import br.com.gerenciapedidos.payments.domain.entity.Plan;
import io.swagger.annotations.ApiOperation;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@PropertySource("classpath:application.properties")
@RestController
@RequestMapping("/v1")
public class SubscriptionPaymentController {

    @Value("${app.id}")
    private String client_id;
    @Value("${app.secret}")
    private String client_secret;
    @Value("${app.sandbox}")
    private String sandbox;

    @ApiOperation(value = "realiza pagamento por assinatura")
    @PostMapping(value = "/createPlan", produces = "application/json", consumes = "application/json")
    public ResponseEntity<?> createPlan(@RequestBody Plan plan){

        try {

            JSONObject options = new JSONObject();
            options.put("client_id", client_id);
            options.put("client_secret", client_secret);
            options.put("sandbox", sandbox);

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
    @GetMapping(value = "/listplans", produces = "application/json")
    public ResponseEntity<?> listPlans(){

        JSONObject options = new JSONObject();
        options.put("client_id", client_id);
        options.put("client_secret", client_secret);
        options.put("sandbox", sandbox);

        /* ************************************************* */

        HashMap<String, String> params = new HashMap<String, String>();

        try {
            Gerencianet gn = new Gerencianet(options);
            JSONObject plans = gn.call("getPlans", params, new JSONObject());
            System.out.println(plans);

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

    @ApiOperation(value = "Cria assinatura vinculando-a em um plano")
    @PostMapping(value = "/subscription", produces = "application/json", consumes = "application/json")
    public ResponseEntity<?> subscription(@RequestBody Item item){

        try {
            JSONObject options = new JSONObject();
            options.put("client_id", client_id);
            options.put("client_secret", client_secret);
            options.put("sandbox", sandbox);

            JSONArray itemsArray = new JSONArray();

            JSONObject item1 = new JSONObject();
            item1.put("name", item.getName());
            item1.put("amount", item.getAmount());
            item1.put("value", item.getValue());

            itemsArray.put(item1);

            System.out.println(item);
            JSONObject jsonObject = new JSONObject(item);
            System.out.println(jsonObject);
            JSONObject body = new JSONObject();
            body.put("items", itemsArray);

            System.out.println(body);


            HashMap<String, String> params = new HashMap<String, String>();
            params.put("id", "7172");


            Gerencianet gn = new Gerencianet(options);
            JSONObject subscription = gn.call("createSubscription", params, body);
            System.out.println(subscription);

            return new ResponseEntity(subscription.toString(), HttpStatus.CREATED);
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
