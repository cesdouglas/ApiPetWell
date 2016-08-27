package br.com.petwell.to;

import com.google.gson.Gson;

public class ResponseTO {
	
	private boolean status;
	private String hashAcesso;

	public boolean getStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}
	
	public static String statusFalse(){
		ResponseTO rj = new ResponseTO();
		rj.status = false;
		return new Gson().toJson(rj);
	}
	
	public String getHashAcesso() {
		return hashAcesso;
	}

	public void setHashAcesso(String hashAcesso) {
		this.hashAcesso = hashAcesso;
	}
	
	public static String statusTrue(){
		ResponseTO rj = new ResponseTO();
		rj.status = true;
		return new Gson().toJson(rj);
	}
	
	public static String statusTrue(String hashAcesso){
		ResponseTO rj = new ResponseTO();
		rj.status = true;
		rj.hashAcesso = hashAcesso;
		return new Gson().toJson(rj);
	}
}
