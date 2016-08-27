package br.com.petwell.exception;

public class EntityNotFoundException extends Exception{

	private static final long serialVersionUID = -5708226994728777384L;

	public EntityNotFoundException() {
	}
	
	public EntityNotFoundException(String msg) {
		super(msg);
	}
}
