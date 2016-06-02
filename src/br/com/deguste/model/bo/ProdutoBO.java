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
import br.com.deguste.interfaces.ProdutoBusiness;
import br.com.deguste.model.entity.Categoria;
import br.com.deguste.model.entity.Produto;

public class ProdutoBO  extends GenericHibernateDAO<Produto> implements ProdutoBusiness,Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5356858088456418444L;
	
	public ProdutoBO(){
		super(Produto.class);
	}
	
	@Override
	public Produto salvar(Produto produto) throws Exception{
		
		if(produto.getId()== null){
			this.verificaProduto(produto);
			super.create(produto);
		}else
			super.update(produto);
			
			return produto;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Produto> selectFornecedores() {
		return super.em.createQuery("SELECT p FROM Produto p").getResultList();
	}
	
	public void verificaProduto(Produto produto) throws Exception{
		try{
			Query queryProdutos = em.createQuery(""
					+ "	SELECT p "
					+ "	FROM Produto p "
					+ "	WHERE p.codigo =:codigo "
					+ "	  AND p.ativo =:ativo");
			
			queryProdutos.setParameter("ativo", true);			
			queryProdutos.setParameter("codigo", produto.getCodigo());
			Number Produtos = (Number) queryProdutos.getSingleResult();
			
			if (Produtos.longValue() > 0  ) {
				throw new Exception();
			}
		} catch (NoResultException e) {
			return;
		} catch (Exception e) {
			throw new Exception(
					"Essa produto ja foi cadastrado");
		}
	}
	
	public List<Produto>consultaProduto(Produto produto) {
		Map<String, Object> parametros = new HashMap<String, Object>();
		StringBuilder query = new StringBuilder("SELECT p FROM Produto p WHERE 1=1");
		
		if(produto.getNome() != null && !produto.getNome().isEmpty()){
			query.append(" AND LOWER(c.nome) LIKE LOWER(:nome)");
			parametros.put("nome", "%"+produto.getNome()+"%");
		}
		
		query.append(" AND p.ativo =:ativo");
		parametros.put("ativo", true);
		TypedQuery<Produto> q = em.createQuery(query.toString(), Produto.class);
		if(parametros != null && !parametros.isEmpty()){
			for(Entry<String, Object> entry : parametros.entrySet()){
				q.setParameter(entry.getKey(), entry.getValue());
			}
		}
		return q.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Override 
	public List<Produto> produtoPorCodigo(Produto produto) {
		return super.em.createQuery("SELECT p FROM Produto p WHERE p.codigo =:codigo").setParameter("codigo", produto.getCodigo()).getResultList(); 
	}
	
	public List<Produto> autoComplete(String str){
		TypedQuery<Produto> query = super.em.createQuery("SELECT p FROM Produto p WHERE p.codigo LIKE :codigo OR LOWER(p.nome) LIKE LOWER(:nome)", Produto.class);
		query.setParameter("codigo", "%"+str+"%");
		query.setParameter("nome", "%"+str+"%");
		return query.getResultList();
	}
}
