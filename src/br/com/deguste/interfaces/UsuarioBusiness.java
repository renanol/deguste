package br.com.deguste.interfaces;


import java.util.List;

import br.com.deguste.dao.IGenericDAO;
import br.com.deguste.model.entity.Usuario;



public interface UsuarioBusiness extends IGenericDAO<Usuario> {

	public Usuario salvar(Usuario user) throws Exception;
	public void existeLogin(Usuario user) throws Exception;
	public List<Usuario> getUsuariosAtivos() throws Exception;
	public List<Usuario> procurarUsuario(Usuario usuario);
}
