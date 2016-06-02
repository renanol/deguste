package br.com.deguste.model.bo;

import java.io.Serializable;
import br.com.deguste.dao.GenericHibernateDAO;
import br.com.deguste.interfaces.PedidoBusiness;
import br.com.deguste.model.entity.Pedido;


public class PedidoBO extends GenericHibernateDAO<Pedido> implements PedidoBusiness, Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3350548141424103254L;

	public PedidoBO(){
		super(Pedido.class);
	}
	
	@Override
	public Pedido salvar(Pedido pedido) throws Exception{
		if(pedido.getId()== null){
			super.create(pedido);
		}else
			super.update(pedido);
			return pedido;
	}
	
	
	
	
	
}
