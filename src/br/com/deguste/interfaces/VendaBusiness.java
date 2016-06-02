package br.com.deguste.interfaces;


import java.util.List;

import br.com.deguste.dao.IGenericDAO;
import br.com.deguste.model.entity.Venda;

public interface VendaBusiness extends IGenericDAO<Venda> {
	
	public Venda salvar(Venda venda) throws Exception;
	public List<Venda> selectAll();

}
