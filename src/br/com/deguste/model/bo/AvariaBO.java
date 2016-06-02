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
import br.com.deguste.interfaces.AvariaBusiness;
import br.com.deguste.model.entity.Avaria;
import br.com.deguste.model.entity.Categoria;

public class AvariaBO extends GenericHibernateDAO<Avaria> implements AvariaBusiness, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3387490053309256524L;


public AvariaBO(){
	super(Avaria.class);
}

@Override
public Avaria salvar(Avaria avaria) throws Exception{
	if(avaria.getId()== null){
		this.verificaAvaria(avaria);
		super.create(avaria);
	}else
		super.update(avaria);
		return avaria;
}

public void verificaAvaria(Avaria avaria) throws Exception{
	try{
		Query queryAvarias = em.createQuery("SELECT a FROM Avaria a WHERE "
						+ "LOWER(a.descricao) = LOWER(:descricao) AND a.ativo =:ativo");
		queryAvarias.setParameter("ativo", true);
		queryAvarias.setParameter("descricao", avaria.getDescricao());
		Number Avarias = (Number) queryAvarias.getSingleResult();
		
		if (Avarias.longValue() > 0  ) {
			throw new Exception();
		}
	} catch (NoResultException e) {
		return;
	} catch (Exception e) {
		throw new Exception(
				"Essa avaria j� foi cadastrado");
	}
}


@SuppressWarnings("unchecked")
@Override
public List<Avaria> selectFornecedores() {
	return super.em.createQuery("SELECT a FROM Avaria a").getResultList();
}

public List<Avaria>consultaAvaria(Avaria avaria) {
	Map<String, Object> parametros = new HashMap<String, Object>();
	StringBuilder query = new StringBuilder("SELECT a FROM Avaria a WHERE 1=1");
	
	if(avaria.getDescricao() != null && !avaria.getDescricao().isEmpty()){
		query.append(" AND LOWER(a.descricao) LIKE LOWER(:descricao)");
		parametros.put("descricao", "%"+avaria.getDescricao()+"%");
	}
	
	query.append(" AND a.ativo =:ativo");
	parametros.put("ativo", true);
	TypedQuery<Avaria> q = em.createQuery(query.toString(), Avaria.class);
	if(parametros != null && !parametros.isEmpty()){
		for(Entry<String, Object> entry : parametros.entrySet()){
			q.setParameter(entry.getKey(), entry.getValue());
		}
	}
	return q.getResultList();
}

	

}
