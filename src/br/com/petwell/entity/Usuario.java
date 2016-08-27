package br.com.petwell.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="USUARIO")
@SequenceGenerator(allocationSize=1, name="seqUsuario", sequenceName="SEQ_USUARIO_PETWELL")
public class Usuario {
	
	@Id
	@GeneratedValue(generator="seqUsuario", strategy=GenerationType.SEQUENCE)
    private int codigo;
	
	@Column(nullable=false, length=100)
    private String nome;
	
	@Column(nullable=false, length=100, unique=true)
    private String email;
	
	@OneToMany(mappedBy="usuario", fetch=FetchType.LAZY)
    private List<Alimentador> alimentadores;
	
    @Column(nullable=false, length=32)
    private String senha;
    
    @Column(length=50, name="HASH_ACESSO", unique=true)
    private String hashAcesso;

    public Usuario() {
    	super();
    }

    public Usuario(int codigo, String nome, String email, List<Alimentador> alimentadores, String senha) {
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
