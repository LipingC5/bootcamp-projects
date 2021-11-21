package com.techelevator.tenmo.services;

import com.techelevator.tenmo.models.Account;
import com.techelevator.tenmo.models.Transfer;
import com.techelevator.tenmo.models.User;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.List;
import java.util.Scanner;

public class ConsoleService {

	private PrintWriter out;
	private Scanner in;

	public ConsoleService(InputStream input, OutputStream output) {
		this.out = new PrintWriter(output, true);
		this.in = new Scanner(input);
	}

	public void showWelcomeBanner() {
		out.println("*********************");
		out.println("* Welcome to TEnmo! *");
		out.println("*********************");
		out.flush();
	}

	public void displayMessage(String message) {
		out.println(message);
		out.flush();
	}

	public void showRegistrationFailed(String message) {
		out.println("REGISTRATION ERROR: " + message);
		out.println("Please attempt to register again.");
		out.flush();
	}

	public void showLoginFailed(String message) {
		out.println("LOGIN ERROR: " + message);
		out.println("Please attempt to login again.");
		out.flush();
	}

	public Object getChoiceFromOptions(Object[] options) {
		Object choice = null;
		while (choice == null) {
			displayMenuOptions(options);
			choice = getChoiceFromUserInput(options);
		}
		out.println();
		return choice;
	}

	private Object getChoiceFromUserInput(Object[] options) {
		Object choice = null;
		String userInput = in.nextLine();
		try {
			int selectedOption = Integer.valueOf(userInput);
			if (selectedOption > 0 && selectedOption <= options.length) {
				choice = options[selectedOption - 1];
			}
		} catch (NumberFormatException e) {
			// eat the exception, an error message will be displayed below since choice will be null
		}
		if (choice == null) {
			out.println(System.lineSeparator() + "*** " + userInput + " is not a valid option ***" + System.lineSeparator());
		}
		return choice;
	}

	private void displayMenuOptions(Object[] options) {
		out.println();
		for (int i = 0; i < options.length; i++) {
			int optionNum = i + 1;
			out.println(optionNum + ") " + options[i]);
		}
		out.print(System.lineSeparator() + "Please choose an option >>> ");
		out.flush();
	}

	public String getUserInput(String prompt) {
		out.print(prompt + ": ");
		out.flush();
		return in.nextLine();
	}

	public Integer getUserInputInteger(String prompt) {
		Integer result = null;
		do {
			out.print(prompt + ": ");
			out.flush();
			String userInput = in.nextLine();
			try {
				result = Integer.parseInt(userInput);
			} catch (NumberFormatException e) {
				out.println(System.lineSeparator() + "*** " + userInput + " is not valid ***" + System.lineSeparator());
			}
		} while (result == null);
		return result;
	}

	public void printError(String errorMessage) {
		System.err.println(errorMessage);
	}

	public void displayCurrentBalance(Account account) {
		System.out.println("Your current account balance is: $" + account.getBalance());
	}

	public void displayRegisteredUsers(List<User> users) {
		System.out.println("--------------------");
		System.out.println("Users");
		System.out.printf("%-10s %-10s \n", "ID", "Name");
		System.out.println("--------------------");
		for (User user : users) {
			System.out.printf("%-10s %-10s \n", user.getUserId(), user.getUserName());
		}
		System.out.println("--------------------");
	}

	public int userIdToSend() {
		int userChoice = 0;
		String userInput = null;
		while (userInput == null) {
			System.out.print("Enter ID of user you are sending to (0 to cancel): ");
			userInput = in.nextLine();
			try {
				userChoice = Integer.parseInt(userInput);
			} catch (NumberFormatException e) {
				userInput = null;
			}
			if (userChoice < 0) {
				userInput = null;
			}
		}
		return userChoice;
	}

	public int userIdToRequest() {
		int userChoice = 0;
		String userInput = null;
		while (userInput == null) {
			System.out.print("Enter ID of user you are requesting from (0 to cancel): ");
			userInput = in.nextLine();
			try {
				userChoice = Integer.parseInt(userInput);
			} catch (NumberFormatException e) {
				userInput = null;
			}
			if (userChoice < 0) {
				userInput = null;
			}
		}
		return userChoice;
	}

	public double amountToTransfer() {
		String userInput = null;
		double amount = 0;
		while (userInput == null) {
			System.out.print("Enter amount: $");
			try {
				userInput = in.nextLine();
				amount = Double.parseDouble(userInput);
			} catch (NumberFormatException e) {
				userInput = null;
			}
			if (amount <= 0) {
				userInput = null;
			}
		}
		return amount;
	}

	public void displayTransferHistory(List<Transfer> transferList, Account account, List<User> registeredUsers) {
		System.out.println("---------------------------------------------");
		System.out.println("Transfers");
		System.out.printf("%-10s %-25s %-10s \n", "ID", "From/To", "Amount");
		System.out.println("---------------------------------------------");
		for (Transfer transfer : transferList) {
			if (transfer.getTransferTypeId() == 2) {
				if (transfer.getAccountFrom() != account.getAccountId()) {
					System.out.printf("%-10s From:%-20s $%-8s \n", transfer.getTransferId(), transfer.getUserName(), transfer.getAmount());
				}else {
					for (User user : registeredUsers) {
						if (transfer.getAccountTo() == user.getAccountId()) {
							System.out.printf("%-10s To:%-22s $%-10s \n", transfer.getTransferId(), user.getUserName(), transfer.getAmount());
						}
					}
				}
			}

			if(transfer.getTransferTypeId() == 1) {
				for (User user : registeredUsers) {
					if (transfer.getAccountFrom() == account.getAccountId()) {
						if (transfer.getAccountTo() == user.getAccountId()) {
							System.out.printf("%-10s From:%-20s $%-8s \n", transfer.getTransferId(), user.getUserName(), transfer.getAmount());
						}
					} else {
						if (transfer.getAccountFrom() == user.getAccountId()) {
							System.out.printf("%-10s To:%-22s $%-10s \n", transfer.getTransferId(), user.getUserName(), transfer.getAmount());
						}
					}
				}
			}
		}
		System.out.println("---------------------------------------------");
	}

	public int getTransferId(){
		int userChoice = 0;
		String userInput = null;
		while (userInput == null) {
			System.out.print("Please enter transfer ID to view details (0 to cancel): ");
			userInput = in.nextLine();
			try {
				userChoice = Integer.parseInt(userInput);
			} catch (NumberFormatException e) {
				userInput = null;
			}
			if (userChoice < 0) {
				userInput = null;
			}
		}
		return userChoice;
	}

	public void displayReceivingTransferDetails(List<Transfer> transferList,  Account account, List<User> registeredUsers, int transferId ){
		System.out.println("---------------------------------------------");
		System.out.println("Transfer Details");
		System.out.println("---------------------------------------------");

		for(Transfer transfer :transferList){
			if(transfer.getTransferId() == transferId){
				if(transfer.getAccountTo() == account.getAccountId()) {
					System.out.println("Id: " + transferId);
					for (User user : registeredUsers) {
						if (transfer.getAccountFrom() == user.getAccountId()) {
							System.out.println("From: " + user.getUserName());
						}
					}
					System.out.println("To: Me");
					System.out.println("Type: Receive");
					System.out.println("Status: Approved");
					System.out.println("Amount: $" + transfer.getAmount());
				}else{
					System.out.println("Id: " + transferId);
					System.out.println("From: Me");
					for (User user : registeredUsers) {
						if (transfer.getAccountTo() == user.getAccountId()) {
							System.out.println("To: " + user.getUserName());
						}
					}
					System.out.println("Type: Send");
					System.out.println("Status: Approved");
					System.out.println("Amount: $" + transfer.getAmount());
				}
			}
		}
	}

	public void displayPendingRequests(List<Transfer> pendingTransfers, List<User> registeredUsers){
		System.out.println("---------------------------------------------");
		System.out.println("Pending Transfers");
		System.out.printf("%-5s %-20s %-15s \n","ID", "To", "Amount");
		System.out.println("---------------------------------------------");

		for(Transfer transfer : pendingTransfers) {
			for (User user : registeredUsers) {
				if(transfer.getAccountFrom() == user.getAccountId())
				System.out.printf("%-5s %-20s $%-15s \n", transfer.getTransferId(), user.getUserName(), transfer.getAmount());
			}
		}
	}

}
