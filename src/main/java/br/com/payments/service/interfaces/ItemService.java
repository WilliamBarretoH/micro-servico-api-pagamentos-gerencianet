package br.com.payments.service.interfaces;

import br.com.payments.domain.entity.Item;
import org.json.JSONArray;

public interface ItemService {

    JSONArray buildItemToArray(Item item);
}
