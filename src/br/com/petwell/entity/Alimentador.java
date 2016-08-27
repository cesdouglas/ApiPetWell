package br.com.petwell.entity;

import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import com.google.gson.annotations.Expose;

@Entity
@Table(name="ALIMENTADOR")
@SequenceGenerator(allocationSize=1, name="seqAlimentador", sequenceName="SEQ_ALIMENTADOR_PETWELL")
public class Alimentador {
	
	@Id
	@GeneratedValue(generator="seqAlimentador", strategy=GenerationType.SEQUENCE)
    private int codigo;
	
	@Expose
	@Column(name="dev_code", nullable=false, unique=true)
	private int devCode;
	
	@Expose
	@Column(nullable=false, length=50)
	private String nome;
	
	@ManyToOne
	@JoinColumn(name="CD_USUARIO")
    private Usuario usuario;
	
	@Transient
	@Expose
    private List<Configuracao> configuracoes;
    

    public Alimentador(int codigo, int devCode, Usuario usuario, List<Configuracao> configuracoes, String nome) {
		super();
		this.codigo = codigo;
		this.setDevCode(devCode);
		this.usuario = usuario;
		this.configuracoes = configuracoes;
		this.nome = nome;
	}

	public Alimentador() {
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

	public List<Configuracao> getConfiguracoes() {
		return configuracoes;
	}

	public void setConfiguracoes(List<Configuracao> configuracoes) {
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
