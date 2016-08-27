package br.com.petwell.entity;


public class Configuracao {

    private int codigo;
    private Alimentador alimentador;
    
	public Configuracao() {
		super();
	}
	
	public Configuracao(int codigo, Alimentador alimentador) {
		super();
		this.codigo = codigo;
		this.alimentador = alimentador;
	}
	public Alimentador getAlimentador() {
		return alimentador;
	}
	public void setAlimentador(Alimentador alimentador) {
		this.alimentador = alimentador;
	}
	public int getCodigo() {
		return codigo;
	}
	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

}
