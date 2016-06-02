package br.com.deguste.enumeration;


public enum PerfilAcesso {
	SECRETARIO("Secretário"),
	CHEFE("Chefe"),
	FUNCIONARIO("Funcionário");
	
	private String descricao;
	
	PerfilAcesso(String descricao){
		this.descricao = descricao;
	}
	
	public String getDescricao(){
		return descricao;
	}
	
	@Override
	public String toString() {
		return descricao;
	}
}
