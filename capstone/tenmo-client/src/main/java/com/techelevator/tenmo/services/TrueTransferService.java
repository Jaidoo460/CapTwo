package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.AuthenticatedUser;
import com.techelevator.tenmo.model.Transfer;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpHeaders;


public class TrueTransferService implements TransferService{
    private String baseUrl;
    private RestTemplate restTemplate = new RestTemplate();

    public TrueTransferService(String url){
        this.baseUrl = url;
    }


    @Override
    public void setTransfer(AuthenticatedUser authenticatedUser, Transfer transfer) {
        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.APPLICATION_JSON);
        header.setBearerAuth(authenticatedUser.getToken());
        HttpEntity<Transfer> entity = new HttpEntity(transfer, header);

        String url = baseUrl + "/transfers/" + transfer.getTransferId();

        try{
            restTemplate.exchange(url, HttpMethod.POST, entity, Transfer.class);
        }catch(RestClientResponseException re){
            System.out.println(re.getMessage());
        }catch(ResourceAccessException e){
            System.out.println(e.getMessage());
        }
    }

    @Override
    public Transfer[] getTransfersFromUserId(AuthenticatedUser authenticatedUser, long userId) {
        return new Transfer[0];
    }

    @Override
    public Transfer getTransferFromTransferId(AuthenticatedUser authenticatedUser, long transferId) {
        return null;
    }

    @Override
    public Transfer[] getAllTransfers(AuthenticatedUser authenticatedUser) {
        return new Transfer[0];
    }

    @Override
    public void updateTransfer(AuthenticatedUser authenticatedUser, Transfer transfer) {

    }
}
