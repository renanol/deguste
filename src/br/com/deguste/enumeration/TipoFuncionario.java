package br.com.deguste.enumeration;

public enum TipoFuncionario {
	EFETIVO("Efetivo"),
	PRESTADORE("Prestador de serviço"),
	COMISSIONADO("Comissionado"),
	PENCIONISTA("Pensionista");
	
	private String descricao;
	
	TipoFuncionario(String descricao){
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}
	
	
}
