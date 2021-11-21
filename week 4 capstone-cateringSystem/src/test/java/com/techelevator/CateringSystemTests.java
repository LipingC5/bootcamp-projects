package com.techelevator;

import com.techelevator.items.CateringItem;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class CateringSystemTests {

    private CateringSystem cateringSystem;

    @Before
    public void setup() {
        cateringSystem = new CateringSystem();
    }

    @Test
    public void is_inventory_list_correct() {
        CateringItem testLine = new CateringItem("Beverages B1 Sparkling Water 1.35");
        List<CateringItem> expectedResult = new ArrayList<CateringItem>();
        expectedResult.add(testLine);

        List<CateringItem> actualResult = cateringSystem.getInventoryItems();

        Assert.assertEquals(expectedResult.get(0), actualResult.get(0));
    }

    @Test
    public void is_item_available() {
        Account testAccount = new Account();
        String testProductCode = "B1";
        int testProductQuantity = 26;
        String expectedResult = "Product is sold out.\n";
        //Assert.assertEquals(expectedResult, cateringSystem.purchaseItem(testProductCode, testProductQuantity, testAccount));
    }

    @Test
    public void is_purchase_money_difference_correct() {
        double testMoneyDifference = 12.5;
        double actualMoneyDifference = cateringSystem.purchaseMoneyDifference("A1", 5);
        Assert.assertEquals(testMoneyDifference, actualMoneyDifference, 0.05);
    }
}
