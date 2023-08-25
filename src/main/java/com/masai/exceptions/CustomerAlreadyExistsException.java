package com.masai.exceptions;



public class CustomerAlreadyExistsException extends Exception{
    public CustomerAlreadyExistsException(String msg) {
    	super(msg);
    }
}
