package com.techelevator;

import com.techelevator.filereader.InventoryFileReader;
import com.techelevator.items.CateringItem;
import com.techelevator.view.Menu;
import java.io.FileNotFoundException;
import java.util.*;
/*
    This class should encapsulate all the functionality of the Catering system,
     meaning it should
    contain all the "work"
 */
public class CateringSystem {

    private Menu menu = new Menu();
    private ShoppingCart shoppingCart = new ShoppingCart();

    public List<CateringItem> getInventoryItems() {

        InventoryFileReader inventoryFileReader = new InventoryFileReader();

        List<CateringItem> inventory = new ArrayList<CateringItem>();
        try {
            inventory = inventoryFileReader.read();
        } catch (FileNotFoundException e) {
            menu.showErrorMessage();
        }
        return inventory;
    }

    public void purchaseItem(String productCode, int quantity, Account account) {

        List<CateringItem> list = getInventoryItems();

        for (CateringItem item : list) {
            try {
                if (item.getProductCode().equalsIgnoreCase(productCode)) {
                    if (item.getProductQuantity() > quantity) {
                        account.subtractMoneyFromAccountBalance(item.getProductPrice(), quantity);
                        shoppingCart.addToShoppingCart(item, quantity);
                        item.setProductQuantity(quantity);
                        break;
                    } else if (item.getProductQuantity() < quantity) {
                        menu.showProductInsufficientStockMessage();
                        break;
                    } else if (item.getProductQuantity() == 0) {
                        menu.showProductSoldOutMessage();
                        break;
                    }
                }
            } catch (Exception e) {
                menu.showProductCodeErrorMessage();
            }
        }
    }

    public List<String> getUserReportOfCompletedTransaction() {
        List<String> userReport = new ArrayList<String>();
        double totalPriceForReport = 0.0;

        Map<CateringItem, Integer> reportMapFromShoppingCart = shoppingCart.getCateringItemMap();
        for (Map.Entry<CateringItem, Integer> itemForReport : reportMapFromShoppingCart.entrySet()) {
            String userReportQuantity = String.valueOf(itemForReport.getValue());
            String userReportProductType = itemForReport.getKey().getProductType();
            String userReportProductName = itemForReport.getKey().getProductName();
            String userReportProductPrice = String.valueOf(itemForReport.getKey().getProductPrice());
            double userReportSumPrice = itemForReport.getValue() * itemForReport.getKey().getProductPrice();
            String userReportSumPriceAsString = String.valueOf(userReportSumPrice);
            String fullUserReport = userReportQuantity + "   " + userReportProductType + "      " + userReportProductName
                    + "     $" + userReportProductPrice + "     $" + userReportSumPriceAsString;
            userReport.add(fullUserReport);
            totalPriceForReport += userReportSumPrice;
        }
        userReport.add("Total: " + totalPriceForReport);
        return userReport;
    }

    public Map<Integer, String> giveChangeToUser(Account account) {

        Map<Integer, String> userChangeMap = new LinkedHashMap<Integer, String>();

        double accountBalanceInPennies = account.getAccountBalance() * 100;

        int[] currencyAsPennies = {2000, 1000, 500, 100, 25, 10, 5, 1};

        if (accountBalanceInPennies >= currencyAsPennies[0]) {
            int numberOf20s = (int) accountBalanceInPennies / currencyAsPennies[0];
            accountBalanceInPennies %= currencyAsPennies[0];
            userChangeMap.put(numberOf20s, " twenties");
        }
        if (accountBalanceInPennies >= currencyAsPennies[1]) {
            int numberOf10s = (int) accountBalanceInPennies / currencyAsPennies[1];
            accountBalanceInPennies %= currencyAsPennies[1];
            userChangeMap.put(numberOf10s, " tens");
        }
        if (accountBalanceInPennies >= currencyAsPennies[2]) {
            int numberOf5s = (int) accountBalanceInPennies / currencyAsPennies[2];
            accountBalanceInPennies %= currencyAsPennies[2];
            userChangeMap.put(numberOf5s, " fives");
        }
        if (accountBalanceInPennies >= currencyAsPennies[3]) {
            int numberOf1s = (int) accountBalanceInPennies / currencyAsPennies[3];
            accountBalanceInPennies %= currencyAsPennies[3];
            userChangeMap.put(numberOf1s, " ones");
        }
        if (accountBalanceInPennies >= currencyAsPennies[4]) {
            int numberOfQuarters = (int) accountBalanceInPennies / currencyAsPennies[4];
            accountBalanceInPennies %= currencyAsPennies[4];
            userChangeMap.put(numberOfQuarters, " quarters");
        }
        if (accountBalanceInPennies >= currencyAsPennies[5]) {
            int numberOfDimes = (int) accountBalanceInPennies / currencyAsPennies[5];
            accountBalanceInPennies %= currencyAsPennies[5];
            userChangeMap.put(numberOfDimes, " dimes");
        }
        if (accountBalanceInPennies >= currencyAsPennies[6]) {
            int numberOfNickels = (int) accountBalanceInPennies / currencyAsPennies[6];
            accountBalanceInPennies %= currencyAsPennies[6];
            userChangeMap.put(numberOfNickels, " nickels");
        }
        if (accountBalanceInPennies >= currencyAsPennies[7]) {
            int numberOfPennies = (int) accountBalanceInPennies;
            userChangeMap.put(numberOfPennies, " pennies");
        }
        return userChangeMap;
    }

    public String purchaseAction(String userInputProductCode, int userInputQuantity){
        List<CateringItem> list = getInventoryItems();
        String productName = "";
        for(CateringItem item : list){
            if(userInputProductCode.equalsIgnoreCase(item.getProductCode())){
                productName = item.getProductName();
            }
        }
        return userInputQuantity + " " + productName + " " + userInputProductCode.toUpperCase();
    }

    public double purchaseMoneyDifference(String userInputProductCode, int userInputQuantity){
        List<CateringItem> list = getInventoryItems();
        double productSumPrice = 0;
        for(CateringItem item : list){
            if(userInputProductCode.equalsIgnoreCase(item.getProductCode())){
                productSumPrice = item.getProductPrice() * userInputQuantity;
            }
        }
        return productSumPrice;
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public String toString() {
        return super.toString();
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}




