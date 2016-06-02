package br.com.deguste.enumeration;

public enum TipoPonto {

	ENTRADA("ENTRADA"),
	SAIDA("SAÍDA"),
	ENTRADA_TURNO_DOIS("ENTRADA SEGUNDO TURNO"),
	SAIDA_TURNO_DOIS("SAÍDA SEGUNDO TURNO");
	
	
	private String descricao;
	
	TipoPonto(String descricao){
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}

}
