package br.com.deguste.dao;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;



@SuppressWarnings("unchecked")
public abstract class GenericHibernateDAO<T> implements IGenericDAO<T>{

	private static final long serialVersionUID = -1139870888589258822L;

	protected Session session;

	@Inject @PersistenceContext 
	protected EntityManager em;

	private Class<T> persistentClass;

	public GenericHibernateDAO(Class<T> persistentClass) {
		this.persistentClass = persistentClass;
	}

	public void openSession(){
		if(this.session == null)
			this.session = (Session) em.getDelegate();
	}

	public void closeSession(){
		if(this.session != null)
			this.session.close();
	}
	
	@Override
	public T selectById(Long id) {
		T t = null;
		try {
			t = em.find(persistentClass, id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return t;
	}

	@Override
	public List<T> selectFornecedores(){
		this.openSession();
		Criteria criteria = session.createCriteria(persistentClass);
		List<T> result = null;

		try{
			result = (List<T>) criteria.list();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public List<T> selectWhere(Criterion ... criterion) {
		Criteria criteria = null;
		List<T> result = null;
		try{
			this.openSession();
			criteria = session.createCriteria(persistentClass);
			for(Criterion c : criterion){
				criteria.add(c);
			}
			result = (List<T>) criteria.list();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	@Override
	public List<T> selectOrderByWhere(Order order, Criterion ... criterion) {
		List<T> result = null;
		try{
			openSession();
			Criteria criteria = session.createCriteria(persistentClass);
			for(Criterion c : criterion)
				criteria.add(c);
			
			criteria.addOrder(order);
			result = (List<T>) criteria.list();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	@Override
	public List<T> selectAllOrderBy(Order order) {
		this.openSession();
		Criteria criteria = session.createCriteria(persistentClass);
		List<T> result = null;
		
		try{
			criteria.addOrder(order);
			result = (List<T>) criteria.list();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
 
	@Override
	public T create(T entity) {
		try{
			em.getTransaction().begin();
			em.persist(entity);
			em.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return entity;
	}

	@Override
	public T update(T entity){
		try { 
			em.getTransaction().begin();
			entity = em.merge(entity);
			em.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return entity;
	}
	
	public void delete(T entity, int id){
		try {
			em.getTransaction().begin();
			entity = (T) em.find(entity.getClass(), id);
			em.remove(entity);
			em.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}