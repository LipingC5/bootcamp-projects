package com.techelevator.tenmo.services;

import com.techelevator.tenmo.auth.models.AuthenticatedUser;
import com.techelevator.tenmo.models.Account;
import com.techelevator.tenmo.models.Transfer;
import com.techelevator.tenmo.models.User;
import org.springframework.http.MediaType;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;

import java.util.Arrays;
import java.util.List;

public class TenmoService {
    private RestTemplate restTemplate = new RestTemplate();
    private String baseUrl;
    private AuthenticatedUser currentUser;
    private ConsoleService console = new ConsoleService(System.in, System.out);

    public TenmoService(String baseUrl, AuthenticatedUser currentUser) {
        this.baseUrl = baseUrl;
        this.currentUser = currentUser;
    }

    public Account getCurrentAccount() {
        HttpEntity entity = makeAuthEntity();
        Account account = null;
        try {
            account = restTemplate.exchange(baseUrl + "accounts", HttpMethod.GET, entity, Account.class).getBody();
        } catch (RestClientResponseException ex) {
            console.printError(ex.getRawStatusCode() + " : " + ex.getStatusText());
        } catch (ResourceAccessException ex) {
            console.printError(ex.getMessage());
        }
        return account;
    }

    public List<User> getAllRegisteredAccount() {
        HttpEntity entity = makeAuthEntity();
        User[] userArray = null;
        try {
            userArray = restTemplate.exchange(baseUrl + "users", HttpMethod.GET, entity, User[].class).getBody();
        } catch (RestClientResponseException ex) {
            console.printError(ex.getRawStatusCode() + " : " + ex.getStatusText());
        } catch (ResourceAccessException ex) {
            console.printError(ex.getMessage());
        }
        return Arrays.asList(userArray);
    }

    public void addTransfer(Account account, User user, double amount) {
        HttpEntity<Transfer> entity = sendTransferEntity(account, user, amount);
        try {
            restTemplate.postForObject(baseUrl + "transfers", entity, Transfer.class);
        } catch (RestClientResponseException ex) {
            console.printError(ex.getRawStatusCode() + " : " + ex.getStatusText());
        } catch (ResourceAccessException ex) {
            console.printError(ex.getMessage());
        }
    }

    public void requestBucks(Account account, User user, double amount) {
        HttpEntity<Transfer> entity = requestTransferEntity(account, user, amount);
        try {
            restTemplate.postForObject(baseUrl + "requestBucks", entity, Transfer.class);
        } catch (RestClientResponseException ex) {
            console.printError(ex.getRawStatusCode() + " : " + ex.getStatusText());
        } catch (ResourceAccessException ex) {
            console.printError(ex.getMessage());
        }
    }

    public List<Transfer> getTransferHistory(){
        HttpEntity entity = makeAuthEntity();
        Transfer[] transfersArr = null;
        try {
            transfersArr = restTemplate.exchange(baseUrl + "transferHistories", HttpMethod.GET, entity, Transfer[].class).getBody();
        } catch (RestClientResponseException ex) {
            console.printError(ex.getRawStatusCode() + " : " + ex.getStatusText());
        } catch (ResourceAccessException ex) {
            console.printError(ex.getMessage());
        }
        return Arrays.asList(transfersArr);
    }

    public List<Transfer> getPendingRequests(){
        HttpEntity entity = makeAuthEntity();
        Transfer[] transfersArr = null;
        try {
            transfersArr = restTemplate.exchange(baseUrl + "pendingRequests", HttpMethod.GET, entity, Transfer[].class).getBody();
        } catch (RestClientResponseException ex) {
            console.printError(ex.getRawStatusCode() + " : " + ex.getStatusText());
        } catch (ResourceAccessException ex) {
            console.printError(ex.getMessage());
        }
        return Arrays.asList(transfersArr);
    }

    private HttpEntity makeAuthEntity() {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(currentUser.getToken());
        HttpEntity entity = new HttpEntity<>(headers);
        return entity;
    }

    private HttpEntity sendTransferEntity(Account account, User user, double amount) {
        Transfer transfer = sendTransfer(account, user, amount);
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(currentUser.getToken());
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Transfer> entity = new HttpEntity<>(transfer, headers);
        return entity;
    }

    private Transfer sendTransfer(Account account, User user, double amount) {
        Transfer transfer = new Transfer();
        transfer.setAccountFrom(account.getAccountId());
        transfer.setAccountTo(user.getAccountId());
        transfer.setTransferTypeId(2);
        transfer.setTransferStatusId(2);
        transfer.setAmount(amount);
        return transfer;
    }

    private Transfer requestTransfer(Account account, User user, double amount) {
        Transfer transfer = new Transfer();
        transfer.setAccountFrom(account.getAccountId());
        transfer.setAccountTo(user.getAccountId());
        transfer.setTransferTypeId(1);
        transfer.setTransferStatusId(1);
        transfer.setAmount(amount);
        return transfer;
    }

    private HttpEntity requestTransferEntity(Account account, User user, double amount) {
        Transfer transfer = requestTransfer(account, user, amount);
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(currentUser.getToken());
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Transfer> entity = new HttpEntity<>(transfer, headers);
        return entity;
    }

}
