package br.com.payments.api.resource;

import br.com.gerencianet.gnsdk.Gerencianet;
import br.com.gerencianet.gnsdk.exceptions.GerencianetException;
import br.com.payments.domain.entity.BilletRequest;
import br.com.payments.domain.response.ResponseBillet;
import br.com.payments.service.interfaces.CredentialsService;
import br.com.payments.service.interfaces.JsonService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
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
@CrossOrigin(origins = "*")
public class BoletoController {

    private static Logger LOG = Logger.getLogger(BoletoController.class.getName());

    @Autowired
    CredentialsService credentialsService;

    @Autowired
    JsonService jsonService;

    @ApiOperation(value = "Cria transacao de boleto para pagamento", response = ResponseBillet.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Boleto criado com sucesso")
    }
    )
    @PostMapping(value = "/pagamento/boleto", produces = "application/json", consumes = "application/json")
    public ResponseEntity<ResponseBillet> pagamentoBoleto(@RequestBody BilletRequest billetRequest){

        try{
            JSONObject options = credentialsService.getCredentials();
            Gerencianet gn = new Gerencianet(options);
            JSONObject body = new JSONObject();
            body.put("items", new JSONArray(billetRequest.getItems()));
            JSONObject response = gn.call("createCharge", new HashMap<String, String>(), body);
            LOG.info(response.toString());
            if(response != null){
                body = new JSONObject();
                body.put("payment", new JSONObject(billetRequest.getPayment()));
                HashMap<String, String> charge_id = new HashMap<String, String>();
                charge_id.put("id", String.valueOf(response.getJSONObject("data").getInt("charge_id")));
                charge_id.put("id", jsonService.getIdFromJsonResponse(response, "data", "charge_id"));
                JSONObject responsePayCharge = gn.call("payCharge", charge_id, body);
                return new ResponseEntity(responsePayCharge.toString(),HttpStatus.OK);
            }else {
                return new ResponseEntity(HttpStatus.NO_CONTENT);
            }

        }catch(GerencianetException e) {
            LOG.log(Level.SEVERE, e.getMessage());
            return new ResponseEntity(e.getErrorDescription(),HttpStatus.INTERNAL_SERVER_ERROR);


        } catch(Exception ex) {
            LOG.log(Level.SEVERE, ex.getMessage());
        }


        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @ApiOperation(value = "Atualiza data de vencimento de um boleto")
    @PutMapping(value = "/boleto/atualizar/{billet_id}", produces = "application/json", consumes = "application/json")
    public ResponseEntity<?> atualizaDataDeVencimentoBoleto(@PathVariable(value = "billet_id") String billet_id,
                                                            @RequestBody String expire_at){

        try{

            JSONObject options = credentialsService.getCredentials();
            Gerencianet gn = new Gerencianet(options);
            JSONObject body = new JSONObject();
            HashMap<String, String> billet_id_hash = new HashMap<String, String>();
            billet_id_hash.put("id", billet_id);
            body.put("expire_at", new JSONObject(expire_at).getString("expire_at"));
            JSONObject response = gn.call("updateBillet", billet_id_hash, body);
            return new ResponseEntity(response.toString(),HttpStatus.OK);

        }
        catch(GerencianetException e) {
            LOG.log(Level.SEVERE, e.getMessage());
            return new ResponseEntity(e.getErrorDescription().toString(),HttpStatus.INTERNAL_SERVER_ERROR);


        } catch(Exception ex) {
            LOG.log(Level.SEVERE, ex.getMessage());
        }

        return new ResponseEntity(HttpStatus.OK);
    }
}
