package com.techelevator.tenmo.model;

import java.math.BigDecimal;

public class Transfers {
    private long transferId;
    private String transferTypeId;
    private String transferStatusId;
    private Long accountFrom;
    //^^ used to be type Account
    private Long accountTo;
    //^^ used to be type Account
    private BigDecimal amount;

    public Transfers(){}
    public Transfers(long transferId, String transferTypeId, String transferStatusId, Long accountFrom, Long accountTo, BigDecimal amount) {
        this.transferId = transferId;
        this.transferTypeId = transferTypeId;
        this.transferStatusId = transferStatusId;
        this.accountFrom = accountFrom;
        this.accountTo = accountTo;
        this.amount = amount;
    }

    public Transfers(int transferTypeId, String transferTypeDesc) {}

    public long getTransferId() {
        return transferId;
    }

    public void setTransferId(long transferId) {
        this.transferId = transferId;
    }

    public String getTransferTypeId() {
        return transferTypeId;
    }

    public void setTransferTypeId(String transferTypeId) {
        this.transferTypeId = transferTypeId;
    }

    public String getTransferStatusId() {
        return transferStatusId;
    }

    public void setTransferStatusId(String transferStatusId) {
        this.transferStatusId = transferStatusId;
    }

    public Long getAccountFrom() {
        return accountFrom;
    }

    public void setAccountFrom(Long accountFrom) {
        this.accountFrom = accountFrom;
    }

    public Long getAccountTo() {
        return accountTo;
    }

    public void setAccountTo(Long accountTo) {
        this.accountTo = accountTo;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
