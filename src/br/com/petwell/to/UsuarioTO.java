package br.com.petwell.to;

import java.util.List;

import com.google.gson.annotations.Expose;

import br.com.petwell.entity.Alimentador;

public class UsuarioTO {
	
    private int codigo;
	
    @Expose
    private String nome;
    
    @Expose
    private String email;
    
    @Expose
    private List<Alimentador> alimentadores;
	
    private String senha;
    
    private String hashAcesso;

    public UsuarioTO() {
    	super();
    }

    public UsuarioTO(int codigo, String nome, String email, List<Alimentador> alimentadores, String senha) {
		super();
		this.codigo = codigo;
		this.nome = nome;
		this.email = email;
		this.setAlimentadores(alimentadores);
		this.senha = senha;
	}

	public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

	public String getHashAcesso() {
		return hashAcesso;
	}

	public void setHashAcesso(String hashAcesso) {
		this.hashAcesso = hashAcesso;
	}

	public List<Alimentador> getAlimentadores() {
		return alimentadores;
	}

	public void setAlimentadores(List<Alimentador> alimentadores) {
		this.alimentadores = alimentadores;
	}
}
