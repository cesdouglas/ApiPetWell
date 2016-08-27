package br.com.petwell.exception;

public class DBException extends Exception {

	private static final long serialVersionUID = -6633336830614662251L;

	public DBException() {}
	
	public DBException(String msg){
		super(msg);
	}
	
}
