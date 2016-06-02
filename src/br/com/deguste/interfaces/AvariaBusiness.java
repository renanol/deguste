package br.com.deguste.interfaces;

import br.com.deguste.dao.IGenericDAO;
import br.com.deguste.model.entity.Avaria;

public interface AvariaBusiness extends IGenericDAO<Avaria> {

	public Avaria salvar(Avaria avaria) throws Exception;
}
