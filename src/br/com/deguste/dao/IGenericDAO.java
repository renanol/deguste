package br.com.deguste.dao;

import java.io.Serializable;
import java.util.List;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;

public interface IGenericDAO<T> extends Serializable {

    T selectById(Long id);

    List<T> selectFornecedores();
    
    List<T> selectWhere(Criterion ...criterions );
    
    List<T> selectAllOrderBy(Order order);
    
    List<T> selectOrderByWhere(Order order, Criterion ...criterions );

    T create(T entity);
    
    T update(T entity);
}
