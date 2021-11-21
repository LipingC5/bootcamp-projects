package com.techelevator;

import com.techelevator.filereader.LogFileWriter;
import com.techelevator.items.CateringItem;
import com.techelevator.view.Menu;

import java.util.List;

/*
 * This class should control the workflow of the application, but not do any other work
 * 
 * The menu class should communicate with the user, but do no other work
 * 
 * This class should control the logical workflow of the application, but it should do no other
 * work.  It should communicate with the user (System.in and System.out) using the Menu class and ask
 * the CateringSystem class to do any work and pass the results between those 2 classes.
 */
public class CateringSystemCLI {

	/*
	 * The menu class is instantiated in the main() method at the bottom of this file.  
	 * It is the only class instantiated in the starter code.  
	 * You will need to instantiate all other classes using the new keyword before you can use them.
	 * 
	 * Remember every class and data structure is a data types and can be passed as arguments to methods or constructors.
	 */
	private Menu menu;
	private CateringSystem cateringSystem = new CateringSystem();

	public CateringSystemCLI(Menu menu) {
		this.menu = menu;
	}
	/*
	 * Your application starts here
	 */
	public void run() {
		menu.showWelcomeMessage();
		Account account = new Account();
		boolean isRunning = true;
		while (isRunning) {
			/*
			Display the Starting Menu and get the users choice.
			Remember all uses of System.out and System.in should be in the menu
			
			IF the User Choice is Display Vending Machine Items, 
				THEN display vending machine items
			ELSE IF the User's Choice is Purchase,
				THEN go to the purchase menu
			*/

			menu.showMainMenu();
			String userMenuChoice = menu.getMainMenuChoiceFromUser();
			if (userMenuChoice.equals("1")) {
				List<CateringItem> item = cateringSystem.getInventoryItems();
				menu.displayInventory(item);
				continue;
			}
			boolean isOrdering = true;
			LogFileWriter logFileWriter = new LogFileWriter();
			while (isOrdering) {
				if (userMenuChoice.equals("2")) {
					menu.showPurchaseMenu(account);
					String userPurchaseMenuChoice = menu.getPurchaseMenuChoiceFromUser();

					if (userPurchaseMenuChoice.equals("1")) {
						double userInputAmount = menu.getMoneyInputFromUser();
						account.addMoneyToAccountBalance(userInputAmount);
						logFileWriter.getLogFile("Add Money: ", userInputAmount, account.getAccountBalance());
						continue;
					}
					if (userPurchaseMenuChoice.equals("2")) {
						String userInputProductCode = menu.getProductCodeFromUser();
						int userInputQuantity = menu.getProductQuantityFromUser();
                        cateringSystem.purchaseItem(userInputProductCode, userInputQuantity, account);
                        logFileWriter.getLogFile(cateringSystem.purchaseAction(userInputProductCode, userInputQuantity),
								cateringSystem.purchaseMoneyDifference(userInputProductCode, userInputQuantity), account.getAccountBalance()   );
						continue;
					}
					if (userPurchaseMenuChoice.equals("3")) {
						menu.showProductsReport(cateringSystem.getUserReportOfCompletedTransaction());
						menu.showUserChangeDetail(cateringSystem.giveChangeToUser(account));
						logFileWriter.getLogFile("Give Change: ", account.getAccountBalance(), 0.0);
						account.setAccountBalance(0.00);
						isOrdering = false;
					}
				}
				if (userMenuChoice.equals("3")) {
					menu.showQuitMessage();
					isRunning = false;
					isOrdering = false;
				}
			}
		}
	}

	/*
	 * This starts the application, but you shouldn't need to change it.  
	 */
	public static void main(String[] args) {
		Menu menu = new Menu();
		CateringSystemCLI cli = new CateringSystemCLI(menu);
		cli.run();
	}
}
