package com.techelevator;

import com.techelevator.items.CateringItem;
import java.util.*;

public class ShoppingCart {

    private Map<CateringItem, Integer> cateringItemMap = new LinkedHashMap<CateringItem, Integer>();

    public void addToShoppingCart(CateringItem item, int quantity) {
        cateringItemMap.put(item, quantity);
    }

    public Map<CateringItem, Integer> getCateringItemMap() {
        return cateringItemMap;
    }
}
