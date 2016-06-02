package br.com.deguste.model.pojos;

import javax.persistence.Id;
import javax.persistence.Transient;

//@EntityForm(descricao="Usuario")
public class Usuario  {
	
	private static final long serialVersionUID = 5600221181781840323L;
	
	private Long id;
	
	@Id
	private String nome;
	
	private String senha;
	
	private Boolean forcarTrocaSenha;
	
	private String matricula;
	
	@Transient
	private String senhaAnterior;
	
	
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
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}

	public boolean isForcarTrocaSenha() {
		if(this.forcarTrocaSenha == null)
			this.forcarTrocaSenha = false;
		return forcarTrocaSenha;
	}
	public void setForcarTrocaSenha(boolean forcarTrocaSenha) {
		this.forcarTrocaSenha = forcarTrocaSenha;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((nome == null || nome.equals("")) ? 0 : nome.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		if(obj == null) return false;
		if(obj instanceof Usuario)
			if(((Usuario)obj).getId() == (this.getId())) return true;
		return false;
	}
	public String getSenhaAnterior() {
		return senhaAnterior;
	}
	public void setSenhaAnterior(String senhaAnterior) {
		this.senhaAnterior = senhaAnterior;
	}
	public Boolean getForcarTrocaSenha() {
		return forcarTrocaSenha;
	}
	public void setForcarTrocaSenha(Boolean forcarTrocaSenha) {
		this.forcarTrocaSenha = forcarTrocaSenha;
	}
	public String getMatricula() {
		return matricula;
	}
	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

}
