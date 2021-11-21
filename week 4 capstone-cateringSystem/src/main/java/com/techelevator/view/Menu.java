package com.techelevator.view;

import com.techelevator.Account;
import com.techelevator.items.CateringItem;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/*
 * This is the only class that should have any usage of System.out or System.in
 *
 * Usage of System.in or System.out should not appear ANYWHERE else in your code outside of this class.
 *
 * Work to get input from the user or display output to the user should be done in this class, however, it
 * should include no "work" that is the job of the catering system.
 */
public class Menu {
	
	private static final Scanner in = new Scanner(System.in);

	public void showWelcomeMessage() {
		System.out.println("*************************");
		System.out.println("**     Weyland Corp.   **");
		System.out.println("**      Catering       **");
		System.out.println("*************************");
		System.out.println();
	}

	public void showMainMenu() {
		System.out.println("(1) Display Catering Items");
		System.out.println("(2) Order");
		System.out.println("(3) Quit\n");
	}

	public String getMainMenuChoiceFromUser(){
		System.out.print("Please select a number from the main menu: ");
		return in.nextLine();
	}

	public void displayInventory(List<CateringItem> cateringItem){
		for(CateringItem currentItem : cateringItem){
			System.out.printf("%-10s %-10s %-10s \n",currentItem.getProductCode(),
					currentItem.getProductPrice(), currentItem.isItemAvailable());
		}
		System.out.println();
	}

	public void showPurchaseMenu(Account account){
		System.out.println("(1) Add Money");
		System.out.println("(2) Select Products");
		System.out.println("(3) Complete Transaction\n");
		System.out.println("Current Account Balance: " + account.getAccountBalance() + "\n");
	}

	public String getPurchaseMenuChoiceFromUser(){
		System.out.print("Please select a number from the purchase menu: ");
		return in.nextLine();
	}

	public int getMoneyInputFromUser(){
		System.out.print("Please enter a whole dollar amount: ");
		String moneyInput = in.nextLine();
		System.out.println();
		return Integer.parseInt(moneyInput);
	}

	public String getProductCodeFromUser() {
		System.out.print("Please enter the item's product code: ");
		return in.nextLine();
	}

	public int getProductQuantityFromUser() {
		System.out.print("Please enter the quantity needed: ");
		String quantityInput = in.nextLine();
		System.out.println();
		return Integer.parseInt(quantityInput);
	}

	public void showProductsReport(List<String> userReport){
		for(String itemForReport : userReport){
			System.out.println(itemForReport);
		}
	}

	public void showUserChangeDetail(Map<Integer, String> giveChangeToUser) {
		System.out.print("Your change is:  ");
		for(Map.Entry<Integer, String> userChangeDetail : giveChangeToUser.entrySet()){
			if(userChangeDetail.getKey() > 0){
				System.out.print(userChangeDetail.getKey() + userChangeDetail.getValue() + " ");
			}
		}
		System.out.println("\n\n");
	}

	public void showProductCodeErrorMessage(){
		System.out.println("Product Code not found.\n");
	}

	public void showProductSoldOutMessage(){
		System.out.println("Product is sold out.\n");
	}

	public void showProductInsufficientStockMessage(){
		System.out.println("Product quantity requested is not available.\n");
	}

	public void showQuitMessage(){
		System.out.println("See you next time.\n");
	}

	public void showErrorMessage(){
		System.out.println("File is not found.\n");
	}
}
