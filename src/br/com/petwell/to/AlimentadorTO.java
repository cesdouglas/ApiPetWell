package br.com.petwell.to;

import java.util.List;

import br.com.petwell.entity.Usuario;

public class AlimentadorTO {
	
    private int codigo;
	
	private int devCode;
	
	private String nome;
	
    private Usuario usuario;
	
    private List<ConfiguracaoTO> configuracoes;
    

    public AlimentadorTO(int codigo, int devCode, Usuario usuario, List<ConfiguracaoTO> configuracoes, String nome) {
		super();
		this.codigo = codigo;
		this.setDevCode(devCode);
		this.usuario = usuario;
		this.configuracoes = configuracoes;
		this.nome = nome;
	}

	public AlimentadorTO() {
    	super();
    }
	

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

	public List<ConfiguracaoTO> getConfiguracoes() {
		return configuracoes;
	}

	public void setConfiguracoes(List<ConfiguracaoTO> configuracoes) {
		this.configuracoes = configuracoes;
	}

	public int getDevCode() {
		return devCode;
	}

	public void setDevCode(int devCode) {
		this.devCode = devCode;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
}
