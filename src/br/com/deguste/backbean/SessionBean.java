package br.com.deguste.backbean;

import java.io.IOException;
import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;

import br.com.deguste.model.entity.Usuario;
import br.com.deguste.util.NavigationUtil;
import br.com.deguste.util.SessionControl;



@Named
@SessionScoped
public class SessionBean implements Serializable {
	

	private static final long serialVersionUID = 2598510283673645554L;
	
	@Inject private SessionControl session;
	
	private String ip;
	
	public SessionBean(){
		this.pegarIP();
	}
	
	
	
	@PostConstruct
	public void init(){
		
	}
	
	public String nomeUsuarioLogado(){
		Usuario user = (Usuario) getSession().getAttributeSession(SessionControl.USUARIO);
		if(user != null)
			return user.getNome();

		return null;
	}

	public Usuario login(String login, String senha){
		return getSession().login(login, senha);
	}
	
	public void pegarIP(){
		FacesContext fc = FacesContext.getCurrentInstance();  
	    HttpServletRequest request = (HttpServletRequest) fc.getExternalContext().getRequest();  
	    this.ip = (request.getRemoteAddr());
		
	}
	
	public void logout() throws IOException {
		getSession().logout();
	}
	
	public boolean isAdmin() {
		return getSession().isAdmin();
	}
	
	public boolean isPerfil(){
		return getSession().isPerfil();
	}
	public boolean loggedIn(){
		return getSession().loggedIn();
	}

	public void listaUsuarios() {
		NavigationUtil.toUsuario();
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	/**
	 * @return the session
	 */
	public SessionControl getSession() {
		return session;
	}

	/**
	 * @param session the session to set
	 */
	public void setSession(SessionControl session) {
		this.session = session;
	}
}