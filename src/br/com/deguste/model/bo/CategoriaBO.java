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
import br.com.deguste.interfaces.CategoriaBusiness;
import br.com.deguste.model.entity.Categoria;
import br.com.deguste.model.entity.Fornecedor;

public class CategoriaBO extends GenericHibernateDAO<Categoria> implements CategoriaBusiness,Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8564784549679701440L;
	
	public CategoriaBO(){
		super(Categoria.class);
	}
	public Categoria salvar(Categoria categoria) throws Exception{
		if(categoria.getId()== null){
			this.verificaCategoria(categoria);
			super.create(categoria);
		}else
			super.update(categoria);
			return categoria;
	}
	
	public void verificaCategoria(Categoria categoria) throws Exception{
		try{
			Query queryCategorias = em.createQuery("SELECT c FROM Categoria c WHERE "
							+ "LOWER(c.nome) = LOWER(:nome) AND c.ativo =:ativo");
			queryCategorias.setParameter("ativo", true);
			queryCategorias.setParameter("nome", categoria.getNome());
			Number categorias = (Number) queryCategorias.getSingleResult();
			
			if (categorias.longValue() > 0  ) {
				throw new Exception();
			}
		} catch (NoResultException e) {
			return;
		} catch (Exception e) {
			throw new Exception(
					"Essa categoria j� foi cadastrado");
		}
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Categoria> selectFornecedores() {
		return super.em.createQuery("SELECT c FROM Categoria c").getResultList();
	}
	
	public List<Categoria>consultaCategoria(Categoria categoria) {
		Map<String, Object> parametros = new HashMap<String, Object>();
		StringBuilder query = new StringBuilder("SELECT c FROM Categoria c WHERE 1=1");
		
		if(categoria.getNome() != null && !categoria.getNome().isEmpty()){
			query.append(" AND LOWER(c.nome) LIKE LOWER(:nome)");
			parametros.put("nome", "%"+categoria.getNome()+"%");
		}
		
		query.append(" AND c.ativo =:ativo");
		parametros.put("ativo", true);
		TypedQuery<Categoria> q = em.createQuery(query.toString(), Categoria.class);
		if(parametros != null && !parametros.isEmpty()){
			for(Entry<String, Object> entry : parametros.entrySet()){
				q.setParameter(entry.getKey(), entry.getValue());
			}
		}
		return q.getResultList();
	}
	
}
