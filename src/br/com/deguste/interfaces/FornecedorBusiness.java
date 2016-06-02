package br.com.deguste.interfaces;


import java.util.List;

import br.com.deguste.dao.IGenericDAO;
import br.com.deguste.model.entity.Fornecedor;


public interface FornecedorBusiness extends IGenericDAO<Fornecedor>{
	public Fornecedor salvar(Fornecedor user) throws Exception;
	public List<Fornecedor> getFornecedoresAtivos() throws Exception;
	public List<Fornecedor>consultaFornecedodor(Fornecedor fornecedor);
	public List<Fornecedor> selectFornecedores();
}
