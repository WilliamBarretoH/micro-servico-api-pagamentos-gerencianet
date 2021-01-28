package br.com.gerenciapedidos.payments.service.providers;

import br.com.gerenciapedidos.payments.domain.entity.Item;
import br.com.gerenciapedidos.payments.service.interfaces.ItemService;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

@Service
public class ItemServiceProvider implements ItemService {
    @Override
    public JSONArray buildItemToArray(Item item) {
        JSONArray itemsArray = new JSONArray();

        JSONObject item1 = new JSONObject();
        item1.put("name", item.getName());
        item1.put("amount", item.getAmount());
        item1.put("value", item.getValue());

        itemsArray.put(item1);
        return itemsArray;
    }
}
