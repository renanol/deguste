package br.com.deguste.model.pojos;

public class BairroLogradouro {
	
	private String cep;
	private Logradouro logradouro;
	private String tipoLogradouro;
	private String cidade;
	private Bairro bairro;
	private String logradouroOutro;
	private String bairroOutro;
	private String estado;
	private boolean flagLocal;

	public BairroLogradouro() {
		logradouro = new Logradouro();
		bairro = new Bairro();
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public Logradouro getLogradouro() {
		return logradouro;
	}

	public void setLogradouro(Logradouro logradouro) {
		this.logradouro = logradouro;
	}

	public String getTipoLogradouro() {
		return tipoLogradouro;
	}

	public void setTipoLogradouro(String tipoLogradouro) {
		this.tipoLogradouro = tipoLogradouro;
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public Bairro getBairro() {
		return bairro;
	}

	public void setBairro(Bairro bairro) {
		this.bairro = bairro;
	}

	public String getLogradouroOutro() {
		return logradouroOutro;
	}

	public void setLogradouroOutro(String logradouroOutro) {
		this.logradouroOutro = logradouroOutro;
	}

	public String getBairroOutro() {
		return bairroOutro;
	}

	public void setBairroOutro(String bairroOutro) {
		this.bairroOutro = bairroOutro;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public boolean isFlagLocal() {
		return flagLocal;
	}

	public void setFlagLocal(boolean flagLocal) {
		this.flagLocal = flagLocal;
	}

}
