package br.com.deguste.model.pojos;

import br.com.deguste.enumeration.TipoComplemento;


public class Endereco {
	
	private Long id;

	private String numero;

	private String apto;

	private String bloco;

	private String edificio;

	private String complemento;

	private TipoComplemento tipoComplemento;

	private BairroLogradouro bairroLogradouro;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getApto() {
		return apto;
	}

	public void setApto(String apto) {
		this.apto = apto;
	}

	public String getBloco() {
		return bloco;
	}

	public void setBloco(String bloco) {
		this.bloco = bloco;
	}

	public String getEdificio() {
		return edificio;
	}

	public void setEdificio(String edificio) {
		this.edificio = edificio;
	}

	public String getComplemento() {
		return complemento;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}

	public TipoComplemento getTipoComplemento() {
		return tipoComplemento;
	}

	public void setTipoComplemento(TipoComplemento tipoComplemento) {
		this.tipoComplemento = tipoComplemento;
	}

	public BairroLogradouro getBairroLogradouro() {
		if(bairroLogradouro==null){
			bairroLogradouro = new BairroLogradouro();
		}
		return bairroLogradouro;
	}

	public void setBairroLogradouro(BairroLogradouro bairroLogradouro) {
		this.bairroLogradouro = bairroLogradouro;
	}

}
