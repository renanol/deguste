package br.com.deguste.model.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;



@Entity
@SequenceGenerator(name="seqFuncionario", sequenceName = "seq_Funcionario", allocationSize = 1)
public class Funcionario  implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 787155985360198200L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqFuncionario")
	private Long id;
	
	@Column(nullable=false, length = 255)
	private String nome;
	
	@Column(nullable=false, length = 14)
	private String cpf;
	
	@Column(nullable=false, length = 8)
	private String rg;
	
	@Column(nullable=false, length = 6)
	private String orgaoExpeditor;
	
	@Column
	private String telefone;
	
	@Column
	private String email;

	//CRIAR DEPOIS UMA CLASSE ENDEREÇO E APONTAR O POJO PRA ELA, PEGUIÇA NÃO DEIXOU EU TERMINAR ISSO
	
//	@Column
//	private String endereco;
		
	
	@Column
	private String tipoFoto;
	

	@Column
	private boolean ativo;
	
	@Temporal(TemporalType.DATE)
	private Date dataCadastro;
	
	@Override
	public boolean equals(Object obj) {
		if(obj == null) return false;
		if(obj instanceof Funcionario)
			if(((Funcionario)obj).getId().equals(this.id)) return true;
		
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


	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf.replaceAll("\\.","").replaceAll("\\/","").replace("-","");
	}
	
	public String getRg() {
		return rg;
	}

	public void setRg(String rg) {
		this.rg = rg;
	}

	public String getOrgaoExpeditor() {
		return orgaoExpeditor;
	}

	public void setOrgaoExpeditor(String orgaoExpeditor) {
		this.orgaoExpeditor = orgaoExpeditor;
	}

	public boolean isAtivo() {
		return ativo;
	}

	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}


	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

//	public String getEndereco() {
//		return endereco;
//	}
//
//	public void setEndereco(String endereco) {
//		this.endereco = endereco;
//	}


	public String getTipoFoto() {
		return tipoFoto;
	}

	public void setTipoFoto(String tipoFoto) {
		this.tipoFoto = tipoFoto;
	}

	public Date getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(Date dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	

	
	
}
