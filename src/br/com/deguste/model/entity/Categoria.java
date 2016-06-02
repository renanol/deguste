package br.com.deguste.model.entity;



import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;


@Entity
@SequenceGenerator (name="seqCategoria", sequenceName="seq_categoria", allocationSize = 1)
public class Categoria implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4547085928435835360L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqCategoria")
	private Long id;
	
	@Column (nullable=false, length=50 )
	private String nome;
	
	@Column
	private boolean ativo;
	
	@Override
	public boolean equals(Object obj) {
		if(obj == null) return false;
		if(obj instanceof Categoria)
			if(((Categoria)obj).getId().equals(this.id)) return true;
		
		return false;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}

	public boolean isAtivo() {
		return ativo;
	}

	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}
	

	}


