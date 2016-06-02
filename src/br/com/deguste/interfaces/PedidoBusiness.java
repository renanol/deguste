package br.com.deguste.interfaces;


import br.com.deguste.dao.IGenericDAO;
import br.com.deguste.model.entity.Pedido;

public interface PedidoBusiness extends IGenericDAO<Pedido> {
	
	public Pedido salvar(Pedido pedido) throws Exception;

}
