package br.com.petwell.to;


public class ConfiguracaoTO {

    private int codigo;
    private AlimentadorTO alimentador;
    
	public ConfiguracaoTO() {
		super();
	}
	
	public ConfiguracaoTO(int codigo, AlimentadorTO alimentador) {
		super();
		this.codigo = codigo;
		this.alimentador = alimentador;
	}
	public AlimentadorTO getAlimentador() {
		return alimentador;
	}
	public void setAlimentador(AlimentadorTO alimentador) {
		this.alimentador = alimentador;
	}
	public int getCodigo() {
		return codigo;
	}
	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

}
