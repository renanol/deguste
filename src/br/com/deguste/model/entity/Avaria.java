package br.com.deguste.model.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@Entity
@SequenceGenerator(name = "seqAvaria", sequenceName = "seq_avaria", allocationSize = 1)
public class Avaria implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1350877055036791999L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqAvaria")
	private Long id;
	
	@Column (nullable = false)
	private String descricao;
	
	@Column
	private boolean ativo;
	
	@Override
	public boolean equals(Object obj) {
		if(obj == null) return false;
		if(obj instanceof Avaria)
			if(((Avaria)obj).getId().equals(this.id)) return true;
		
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

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public boolean isAtivo() {
		return ativo;
	}

	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}
	
	
	

}
