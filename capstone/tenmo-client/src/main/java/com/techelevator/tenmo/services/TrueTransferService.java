package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.AuthenticatedUser;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.util.BasicLogger;
import org.springframework.http.*;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.session.HeaderWebSessionIdResolver;

import javax.swing.table.TableRowSorter;
import java.math.BigDecimal;


public class TrueTransferService implements TransferService{
    private static final String API_BASE_URL = "http://localhost:8080";
    private RestTemplate restTemplate = new RestTemplate();
    private String authToken = null;

    //public TrueTransferService(String url){
        //this.baseUrl = url;
   // }


    @Override
    public Transfer setTransfer(AuthenticatedUser authenticatedUser, Transfer transfer) {
//        HttpHeaders header = new HttpHeaders();
//        header.setContentType(MediaType.APPLICATION_JSON);
//        header.setBearerAuth(authenticatedUser.getToken());
//        HttpEntity<Transfer> entity = new HttpEntity(transfer, header);
//        String url = baseUrl + "/transfers/" + transfer.getTransferId();

        Transfer newTransfer = null;
        try{
//            newTransfer=restTemplate.postForObject(API_BASE_URL + "transfer" + authenticatedUser, createdHttpEntity(transfer), Transfer.class);
        }catch(RestClientResponseException re){
            System.out.println(re.getMessage());
        }catch(ResourceAccessException e){
            System.out.println(e.getMessage());
        }
        return transfer;
    }

    @Override
    public Transfer[] getTransfersFromUserId(AuthenticatedUser authenticatedUser, long userId) {
        Transfer[] transfer = null;
        try{
            ResponseEntity<Transfer[]> response = restTemplate.exchange(API_BASE_URL+ "/transfer/transfers" + userId, HttpMethod.GET, makeAuthEntity(), Transfer[].class);
            transfer = response.getBody();
        }catch(RestClientResponseException | ResourceAccessException e){
            BasicLogger.log(e.getMessage());
        }
        return transfer;
    }

    @Override
    public Transfer getTransferFromTransferId(AuthenticatedUser authenticatedUser, long transferId) {
        Transfer transfer = null;
        try{
            ResponseEntity<Transfer> response = restTemplate.exchange(API_BASE_URL + "/transfer/transfers" + transferId, HttpMethod.GET, makeAuthEntity(), Transfer.class);
            transfer = response.getBody();
        }catch(RestClientResponseException | ResourceAccessException e){
            BasicLogger.log(e.getMessage());
        }
        return transfer;
    }

    @Override
    public Transfer[] getAllTransfers(AuthenticatedUser authenticatedUser) {
        Transfer[] transfer = null;
        try{
            ResponseEntity<Transfer[]> response = restTemplate.exchange(API_BASE_URL + "/account/transfers", HttpMethod.GET, createdHttpEntity(authenticatedUser), Transfer[].class);
            transfer = response.getBody().length;
        }catch(RestClientResponseException | ResourceAccessException e){
            BasicLogger.log(e.getMessage());
        }
        return transfer;
    }


    private HttpEntity createdTransferEntity(Transfer transfer){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(authToken);
        return new HttpEntity(transfer, headers);
//        HttpEntity entity = new HttpEntity(headers);
//
//        return entity;
    }

    private HttpEntity<Void> makeAuthEntity(){
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(authToken);
        return new HttpEntity<>(headers);
    }

    private HttpEntity createdHttpEntity(AuthenticatedUser authenticatedUser){
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(authenticatedUser.getToken());
        HttpEntity entity = new HttpEntity(headers);

        return entity;
    }
}
