package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.AuthenticatedUser;
import com.techelevator.tenmo.model.Balance;
import com.techelevator.util.BasicLogger;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;

public class TrueAccountService implements AccountService{
    private static final String API_BASE_URL = "http://localhost:8080";
    private final RestTemplate restTemplate = new RestTemplate();
    private String authToken = null;

//    public TrueAccountService(String baseUrl){
//        this.restTemplate = new RestTemplate();
//        this.baseUrl = baseUrl;
//    }

    @Override
    public BigDecimal getBalance(AuthenticatedUser authenticatedUser){

        BigDecimal balance = null;
        try{
            ResponseEntity<Balance> response =
                    restTemplate.exchange(API_BASE_URL + "/account/balance", HttpMethod.GET, createdHttpEntity(authenticatedUser), Balance.class);
                    balance = response.getBody().getBalance();
//            balance = restTemplate.getForObject(API_BASE_URL + "/account/balance", Balance.class);
        }catch (RestClientResponseException | ResourceAccessException e){
            BasicLogger.log(e.getMessage());
        }
        return balance;
//        HttpEntity entity = createdHttpEntity(authenticatedUser);
//
//        Balance balance = null;
//
//        try{
//            balance = restTemplate.exchange(baseUrl + "/balance", HttpMethod.GET, entity, Balance.class).getBody();
//        }
//        catch(RestClientResponseException r){
//            System.out.println(r.getMessage());
//        }
//        catch (ResourceAccessException re){
//            System.out.println(re.getMessage());
//        }
//        return balance;
    }

    @Override
    public Account getAccountByUserId(AuthenticatedUser authenticatedUser, long userId) {
        return null;
    }

    @Override
    public Account getAccountById(AuthenticatedUser authenticatedUser, long accountId) {
        return null;
    }


    private HttpEntity createdHttpEntity(AuthenticatedUser authenticatedUser){
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(authenticatedUser.getToken());
        HttpEntity entity = new HttpEntity(headers);

        return entity;
    }

    private HttpEntity<Void> makeAuthEntity(){
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(authToken);

        return new HttpEntity<>(headers);
    }

}
