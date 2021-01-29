package br.com.gerenciapedidos.payments.api.resource;

import br.com.gerencianet.gnsdk.Gerencianet;
import br.com.gerencianet.gnsdk.exceptions.GerencianetException;
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
import java.util.logging.Level;
import java.util.logging.Logger;

@PropertySource("classpath:application.properties")
@RestController
@RequestMapping("/v1")
@CrossOrigin(origins = "*")
public class AssinaturaController {

    private static Logger LOG = Logger.getLogger(AssinaturaController.class.getName());

    @Autowired
    CredentialsService credentialsService;

    @Autowired
    ItemService itemService;

    @Autowired
    JsonService jsonService;


    @ApiOperation(value = "Cria novo plano de assinatura")
    @PostMapping(value = "/planos/novo", produces = "application/json", consumes = "application/json")
    public ResponseEntity<?> criarPlano(@RequestBody Plan plan){

        try {

            JSONObject options = credentialsService.getCredentials();

            Gerencianet gn = new Gerencianet(options);
            JSONObject body = new JSONObject();
            body.put("name", plan.getName());
            body.put("interval", plan.getInterval());
            body.put("repeats", plan.getRepeats());

            JSONObject response = gn.call("createPlan", new HashMap<String, String>(), body);

            return new ResponseEntity(response.toString(), HttpStatus.CREATED);

        }catch (GerencianetException e){
            LOG.log(Level.SEVERE, e.getMessage());
            return new ResponseEntity(e.getErrorDescription(),HttpStatus.INTERNAL_SERVER_ERROR);        } catch (Exception ex) {
            LOG.log(Level.SEVERE, ex.getMessage());
        }

        return new ResponseEntity(HttpStatus.OK);
    }

    @ApiOperation(value = "Lista todos os planos de assinatura")
    @GetMapping(value = "/planos", produces = "application/json")
    public ResponseEntity<?> listaPlano(){

        JSONObject options = credentialsService.getCredentials();
        HashMap<String, String> params = new HashMap<String, String>();
        try {
            Gerencianet gn = new Gerencianet(options);
            JSONObject plans = gn.call("getPlans", params, new JSONObject());
            return new ResponseEntity(plans.toString(), HttpStatus.OK);
        }catch (GerencianetException e){
            LOG.log(Level.SEVERE, e.getMessage());
            return new ResponseEntity(e.getErrorDescription(),HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, ex.getMessage());
        }

        return new ResponseEntity(HttpStatus.OK);
    }

    @ApiOperation(value = "Cria e paga assinatura por cartao de credito vinculando-a em um plano")
    @PostMapping(value = "/pagamento/{plan_id}/assinatura", produces = "application/json", consumes = "application/json")
    public ResponseEntity<?> criarAssinatura(@RequestBody SubscriptionRequest subscriptionRequest,
                                             @PathVariable(value = "plan_id") String plan_id){

        try {
            JSONObject options = credentialsService.getCredentials();
            JSONArray itemsArray = itemService.buildItemToArray(subscriptionRequest.getItems());
            JSONObject body = new JSONObject();
            body.put("items", itemsArray);
            HashMap<String, String> plan_id_hash = new HashMap<String, String>();
            plan_id_hash.put("id", plan_id);
            Gerencianet gn = new Gerencianet(options);
            JSONObject subscription = gn.call("createSubscription", plan_id_hash, body);
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
            LOG.log(Level.SEVERE, e.getMessage());
            return new ResponseEntity(e.getErrorDescription(),HttpStatus.INTERNAL_SERVER_ERROR);

        } catch (Exception ex) {
            LOG.log(Level.SEVERE, ex.getMessage());
        }

        return new ResponseEntity(HttpStatus.OK);
    }

    @ApiOperation(value = "Rota para deletar um plano passando seu id na URL")
    @DeleteMapping(value = "/plano/deletar/{plan_id}")
    public ResponseEntity deletarPlano(@PathVariable(value = "plan_id") String plan_id){

        JSONObject options = credentialsService.getCredentials();

        HashMap<String, String> params = new HashMap<String, String>();
        params.put("id", plan_id);

        try {
            Gerencianet gn = new Gerencianet(options);
            JSONObject plan = gn.call("deletePlan", params, new JSONObject());

            return new ResponseEntity(plan.toString(),HttpStatus.OK);
        }catch (GerencianetException e){
            LOG.log(Level.SEVERE, e.getMessage());
            return new ResponseEntity(e.getErrorDescription(),HttpStatus.INTERNAL_SERVER_ERROR);

        } catch (Exception ex) {
            LOG.log(Level.SEVERE, ex.getMessage());
        }

        return new ResponseEntity(HttpStatus.OK);
    }

}
