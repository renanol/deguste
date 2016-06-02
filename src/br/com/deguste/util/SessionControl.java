package br.com.deguste.util;

import java.io.IOException;
import java.io.Serializable;

import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import br.com.deguste.enumeration.PerfilAcesso;
import br.com.deguste.model.bo.UsuarioBO;
import br.com.deguste.model.entity.Usuario;

public class SessionControl implements Serializable {

	private static final long serialVersionUID = -3150698610365057584L;

	public static final String USUARIO = "usuario.session";
	public static final String ALTERA_USUARIO = "alteraUsuario.session";
	
	@Inject
	private UsuarioBO usuarioBO;
	
	public HttpSession obterSession(){
		return (HttpSession) FacesUtil.obterFacesContext().getExternalContext().getSession(false);
	}
	
	public Usuario getUsuarioSession(){
		return (Usuario) getAttributeSession(USUARIO);
	}
	
	public boolean isAdmin(){
		Usuario user = getUsuarioSession();
		if(user != null && user.getPerfil() != null && user.getPerfil().equals(PerfilAcesso.CHEFE))
			return true;
		return false;
	}
	public boolean isPerfil(){
		Usuario user = getUsuarioSession();
		if(user != null && user.getPerfil() != null && (user.getPerfil().equals(PerfilAcesso.CHEFE) || user.getPerfil().equals(PerfilAcesso.FUNCIONARIO)))
			return true;
		return false;
	}
	
	public boolean isUsuarioInSession(){
		Usuario usuario = getUsuarioSession();
		if (usuario !=null)
			return true;
		return false;
	}
	
	public void addAttributeSession(String nomeAtributo, Object obj) {
		if(nomeAtributo != null && !nomeAtributo.isEmpty()){
			HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
			session.setAttribute(nomeAtributo, obj);
		}
	}

	public Object getAttributeSession(String attribute){
		if(attribute != null && !attribute.isEmpty()){
			HttpSession session = obterSession();
			if(session != null)
				return session.getAttribute(attribute);
		}

		return null;
	}

	public void removeAttributeSession(String attribute){
		if(attribute != null && !attribute.isEmpty()){
			HttpSession session = obterSession();
			if(session != null)
				session.removeAttribute(attribute);
		}
	}
	
	public Usuario login(String login, String senha){
		Usuario usuario = usuarioBO.verificaLogin(login, senha);
		if(usuario != null){
			addAttributeSession(USUARIO, usuario);
			return usuario;
		}

		return null;
	}

	public void logout() throws IOException {
		invalidateSession();
		NavigationUtil.toLogin();
	}

	public void invalidateSession(){
		FacesUtil.obterFacesContext().getExternalContext().invalidateSession();
	}

	public boolean loggedIn(){
		HttpSession session = obterSession();
		if(session != null)
			if(session.getAttribute(USUARIO) != null)
				return true;

		return false;
	}
}