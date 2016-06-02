package br.com.deguste.model.bo;


import java.io.Serializable;
import java.util.List;

import br.com.deguste.dao.GenericHibernateDAO;
import br.com.deguste.interfaces.VendaBusiness;
import br.com.deguste.model.entity.Venda;

public class VendaBO extends GenericHibernateDAO<Venda> implements VendaBusiness, Serializable  {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4874294880153854187L;
	
	public VendaBO(){
		super(Venda.class);
	}
	
	@Override
	public Venda salvar(Venda venda) throws Exception{
		if(venda.getId() == null)
			super.create(venda);
		else
			super.update(venda);
		
		return venda;
			
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Venda> selectAll() {
		return super.em.createQuery("SELECT v FROM Venda v").getResultList();
	}

}
