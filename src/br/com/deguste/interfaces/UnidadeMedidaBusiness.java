package br.com.deguste.interfaces;


import java.util.List;

import br.com.deguste.dao.IGenericDAO;
import br.com.deguste.model.entity.UnidadeMedida;

public interface UnidadeMedidaBusiness extends IGenericDAO<UnidadeMedida> {
	
	public UnidadeMedida salvar(UnidadeMedida unidadeMedida) throws Exception;
	public List<UnidadeMedida> selectFornecedores();

}
