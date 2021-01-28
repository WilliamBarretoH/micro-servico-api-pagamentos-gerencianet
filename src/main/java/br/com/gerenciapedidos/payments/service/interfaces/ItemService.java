package br.com.gerenciapedidos.payments.service.interfaces;

import br.com.gerenciapedidos.payments.domain.entity.Item;
import org.json.JSONArray;

public interface ItemService {

    JSONArray buildItemToArray(Item item);
}
