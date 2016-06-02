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
import br.com.deguste.interfaces.FornecedorBusiness;
import br.com.deguste.model.entity.Fornecedor;
import br.com.deguste.model.entity.Usuario;

public class FornecedorBO extends GenericHibernateDAO<Fornecedor> implements FornecedorBusiness,Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1482262853120477742L;
	
	public FornecedorBO(){
		super(Fornecedor.class);
	}
	@Override
	public Fornecedor salvar(Fornecedor fornecedor) throws Exception{
		if(fornecedor.getId()== null){
			this.verificaFornecedor(fornecedor);
			super.create(fornecedor);
		}else {
		
			super.update(fornecedor);
		}		
		
		return fornecedor;
	}
	
	public void verificaFornecedor(Fornecedor fornecedor) throws Exception{
		try{
			Query queryFornecedores = em.createQuery("SELECT f FROM Fornecedor f WHERE "
							+ "f.ativo =:ativo AND f.cnpj =:cnpj");
			queryFornecedores.setParameter("ativo", true);
			queryFornecedores.setParameter("cnpj", fornecedor.getCnpj());
			Number fornecedores = (Number) queryFornecedores.getSingleResult();
			
			if (fornecedores.longValue() > 0  ) {
				throw new Exception();
			}
		} catch (NoResultException e) {
			return;
		} catch (Exception e) {
			throw new Exception(
					"Esse fornecedor j� foi cadastrado");
		}
	}
	
	
	
	
	
	@Override
	public List<Fornecedor> getFornecedoresAtivos() throws Exception {
		return em.createQuery("SELECT u FROM Fornecedor u WHERE u.ativo =:ativo").setParameter("ativo", true).getResultList();
	}
	
	@Override
	public List<Fornecedor>consultaFornecedodor(Fornecedor fornecedor) {
		Map<String, Object> parametros = new HashMap<String, Object>();
		StringBuilder query = new StringBuilder("SELECT f FROM Fornecedor f WHERE 1=1");
		
		if(fornecedor.getNome() != null && !fornecedor.getNome().isEmpty()){
			query.append(" AND LOWER(f.nome) LIKE LOWER(:nome)");
			parametros.put("nome", "%"+fornecedor.getNome()+"%");
		}
		
		if(fornecedor.getCnpj() != null && !fornecedor.getCnpj().isEmpty()){
			query.append(" AND f.cnpj =:cnpj");
			parametros.put("cnpj", fornecedor.getCnpj());
		}
		query.append(" AND f.ativo =:ativo");
		parametros.put("ativo", true);
		TypedQuery<Fornecedor> q = em.createQuery(query.toString(), Fornecedor.class);
		if(parametros != null && !parametros.isEmpty()){
			for(Entry<String, Object> entry : parametros.entrySet()){
				q.setParameter(entry.getKey(), entry.getValue());
			}
		}
		return q.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Fornecedor> selectFornecedores() {
		return super.em.createQuery("SELECT f FROM Fornecedor f where f.ativo = true  ").getResultList();
	}
}
