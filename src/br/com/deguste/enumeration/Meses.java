package br.com.deguste.enumeration;

public enum Meses {
	JANEIRO("Janeiro",1),
	FEVEREIRO("Fevereiro",2),
	MARCO("Março",3),
	ABRIL("Abril",4),
	MAIO("Maio",5),
	JUNHO("Junho",6),
	JULHO("Julho",7),
	AGOSTO("Agosto",8),
	SETEMBRO("Setembro",9),
	OUTUBRO("Outubro",10),
	NOVEMBRO("Novembro",11),
	DEZEMBRO("Dezembro",12);
	
	private String mes;
	private Integer value;
	
	Meses(String mes, Integer value){
		this.mes = mes; 
		this.value = value;
	}

	public String getMes() {
		return mes;
	}

	public Integer getValue() {
		return value;
	}

	

}
