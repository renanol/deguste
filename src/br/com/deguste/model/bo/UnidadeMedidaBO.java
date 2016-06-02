package br.com.deguste.model.bo;


import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import br.com.deguste.dao.GenericHibernateDAO;
import br.com.deguste.interfaces.UnidadeMedidaBusiness;
import br.com.deguste.model.entity.UnidadeMedida;
import br.com.deguste.model.entity.UnidadeMedida;

public class UnidadeMedidaBO extends GenericHibernateDAO<UnidadeMedida> implements UnidadeMedidaBusiness, Serializable  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2807665071924161929L;
	
	public UnidadeMedidaBO(){
		super(UnidadeMedida.class);
	}
	
	public UnidadeMedida salvar(UnidadeMedida unidadeMedida) throws Exception{
		if(unidadeMedida.getId() == null){
			this.verificaUnidadeMedida(unidadeMedida);
			super.create(unidadeMedida);
		}else
			super.update(unidadeMedida);
			return unidadeMedida;
	}
	
	public void verificaUnidadeMedida(UnidadeMedida UnidadeMedida) throws Exception{
		try{
			Query queryUnidadeMedidas = em.createQuery("SELECT u FROM UnidadeMedida u WHERE "
							+ "LOWER(u.nome) = LOWER(:nome) AND u.ativo =:ativo");
			queryUnidadeMedidas.setParameter("ativo", true);
			queryUnidadeMedidas.setParameter("nome", UnidadeMedida.getNome());
			Number UnidadeMedidas = (Number) queryUnidadeMedidas.getSingleResult();
			
			if (UnidadeMedidas.longValue() > 0  ) {
				throw new Exception();
			}
		} catch (NoResultException e) {
			return;
		} catch (Exception e) {
			throw new Exception(
					"Essa unidade de Medida j� foi cadastrado");
		}
	}
	
	public List<UnidadeMedida>consultaUnidadeMedida(UnidadeMedida unidadeMedida) {
		Map<String, Object> parametros = new HashMap<String, Object>();
		StringBuilder query = new StringBuilder("SELECT u FROM UnidadeMedida u WHERE 1=1");
		
		if(unidadeMedida.getNome() != null && !unidadeMedida.getNome().isEmpty()){
			query.append(" AND LOWER(u.nome) LIKE LOWER(:nome)");
			parametros.put("nome", "%"+unidadeMedida.getNome()+"%");
		}
		
		query.append(" AND u.ativo =:ativo");
		parametros.put("ativo", true);
		TypedQuery<UnidadeMedida> q = em.createQuery(query.toString(), UnidadeMedida.class);
		if(parametros != null && !parametros.isEmpty()){
			for(Entry<String, Object> entry : parametros.entrySet()){
				q.setParameter(entry.getKey(), entry.getValue());
			}
		}
		return q.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<UnidadeMedida> selectFornecedores() {
		return super.em.createQuery("SELECT u FROM UnidadeMedida u").getResultList();
	}


}
