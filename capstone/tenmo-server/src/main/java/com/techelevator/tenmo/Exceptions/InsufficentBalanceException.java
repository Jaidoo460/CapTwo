package com.techelevator.tenmo.Exceptions;

public class InsufficentBalanceException extends Exception {
    public InsufficentBalanceException(){
        super("Sorry, but your broke!");
    }
}
