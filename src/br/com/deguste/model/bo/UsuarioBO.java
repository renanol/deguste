package br.com.deguste.model.bo;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.inject.Inject;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import br.com.deguste.dao.GenericHibernateDAO;
import br.com.deguste.interfaces.UsuarioBusiness;
import br.com.deguste.model.entity.Usuario;

public class UsuarioBO extends GenericHibernateDAO<Usuario> implements UsuarioBusiness,Serializable {

	private static final long serialVersionUID = 8786934356180448694L;
	private Integer usuarios;
	
	public UsuarioBO() {
		super(Usuario.class);
	}

	@Override
	public Usuario salvar(Usuario usuario) throws Exception {

		if(usuario.getId() == null){
			this.existeLogin(usuario);
			super.create(usuario);
			
			
		
		}else		
			super.update(usuario);
		

		return usuario;
	}

	@Override
	public void existeLogin(Usuario user) throws Exception{
		try{
			Query queryUsuarios = em.createQuery("SELECT u FROM Usuario u WHERE "
							+ "LOWER(u.login) = LOWER(:login) AND u.ativo =:ativo");
			queryUsuarios.setParameter("login", user.getLogin());
			queryUsuarios.setParameter("ativo", true);
			Number usuarios = (Number) queryUsuarios.getSingleResult();
			
			if (usuarios.longValue() > 0  ) {
				throw new Exception();
			}
		} catch (NoResultException e) {
			return;
		} catch (Exception e) {
			throw new Exception(
					"Login já existe");
		}
	}

	public Usuario selectUsuarioByNome(String nome){
		Criterion criterionNome = Restrictions.like("descricao", nome.toUpperCase());
		Criterion criterionAtivo = Restrictions.eq("ativo", true);
		Usuario usuario = null;
		
		if(!super.selectWhere(criterionNome, criterionAtivo).isEmpty()){
			usuario = super.selectWhere(criterionNome, criterionAtivo).get(0);
		}
		
		return usuario;
	}

	public Usuario verificaLogin(String login, String senha) {
		List<Usuario> usuarios = em.createQuery("SELECT u FROM Usuario u WHERE u.login =:login AND u.senha =:senha ").setParameter("login", login).setParameter("senha", senha).getResultList();
		
		if (usuarios != null && !usuarios.isEmpty()){
			return usuarios.iterator().next();
		}
		
		return null;
	}
	

	@Override
	public List<Usuario> selectAllOrderBy(Order order) {
		return null;
	}

	@Override
	public List<Usuario> selectOrderByWhere(Order order, Criterion... criterions) {
		return null;
	}

	@Override
	public List<Usuario> getUsuariosAtivos() throws Exception {
		return em.createQuery("SELECT u FROM Usuario u WHERE u.ativo =:ativo ").setParameter("ativo", true).getResultList();
	}
	
	@Override
	public List<Usuario> procurarUsuario(Usuario usuario){
		Map<String, Object> parametros = new HashMap<String, Object>();
		StringBuilder query = new StringBuilder("SELECT u FROM Usuario u WHERE  u.ativo =:ativo");
		
		if(usuario.getNome() != null && !usuario.getNome().isEmpty()){
			query.append(" AND LOWER(u.nome) LIKE LOWER(:nome)");
			parametros.put("nome", "%"+usuario.getNome()+"%");
		}
			if(usuario.getLogin() != null && !usuario.getLogin().isEmpty()){
				query.append(" AND LOWER(u.login) LIKE LOWER(:login)");
				parametros.put("login", usuario.getLogin()+"%");
				
			}
			
			parametros.put("ativo", true);
			
			TypedQuery<Usuario> u = em.createQuery(query.toString(), Usuario.class);
			if(parametros != null && !parametros.isEmpty()){
				for(Entry<String, Object> entry : parametros.entrySet()){
					u.setParameter(entry.getKey(), entry.getValue());
				}
			}
			return u.getResultList();
		}

	}
	

