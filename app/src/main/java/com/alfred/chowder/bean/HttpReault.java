package com.alfred.chowder.bean;

/**
 * Created by Alfred on 2016/7/8.
 */
public class HttpReault<T> extends Entity{
	private int resultCode;
	private String reaultMessage;

	private T data;
}
