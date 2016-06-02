package br.com.deguste.enumeration;

public enum TipoUsuario {
	
	USUARIO("Usuário"),
	ADMINISTRADOR("Administrador");
	
	private String tipo;
	
	private TipoUsuario(String tipo) {
		this.tipo = tipo;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	
	@Override
	public String toString() {
		return tipo;
	}
	
	
}
