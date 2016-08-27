package br.com.petwell.to;

import java.util.Calendar;

public class DocCloudantTO {
	private int devCode;
	private Calendar data = Calendar.getInstance();
	
	public DocCloudantTO() {
		super();
	}
	
	public DocCloudantTO(int devCode) {
		super();
		this.devCode = devCode;
	}

	public DocCloudantTO(int devCode, Calendar data) {
		super();
		this.devCode = devCode;
		this.data = data;
	}

	public int getDevCode() {
		return devCode;
	}
	
	public void setDevCode(int devCode) {
		this.devCode = devCode;
	}
	
	public Calendar getData() {
		return data;
	}
	
	public void setData(Calendar data) {
		this.data = data;
	}
	
}
