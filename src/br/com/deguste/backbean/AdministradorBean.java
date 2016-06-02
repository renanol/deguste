package br.com.deguste.backbean;

import java.net.UnknownHostException;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.deguste.model.entity.Usuario;

@SessionScoped
@ManagedBean
public class AdministradorBean {

	
	private String url;
	private String mensagemGeralInformacao;
	private Usuario usuario;
	private boolean logged;
	
	public AdministradorBean(){
		this.usuario = new Usuario();
		logged = false;
	}
	
	public String getMensagemGeralInformacao() {
		return mensagemGeralInformacao;
	}

	public void setMensagemGeralInformacao(String mensagemGeralInformacao) {
		this.mensagemGeralInformacao = mensagemGeralInformacao;
	}
	
//	public void abrirDialogMensagemGeralInformacao(String mensagemGeralInformacao){
//		this.mensagemGeralInformacao = mensagemGeralInformacao;
//		FacesUtil.updateRequestContext("pgDialogGeralMensagemInformacao");
//		FacesUtil.executeRequestContext("PF('dialogMensagemGeralInformacao').show()");
//	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public boolean isLogged() {
		if(usuario!=null && usuario.getId()!=null){
			logged = true;
		}
		return logged;
	}

	public void setLogged(boolean logged) {
		this.logged = logged;
	}

	public String getUrl() {
		if(url==null){
			url = "/pages/index.xhtml";
		}
		return url;
	}

	public String inputUrl(String url) {
		this.url = "/pages/" + url;
		return "/pages/common/index.xhtml";
	}
	

	public String capturarIpLocal() throws UnknownHostException{
		return "";
		//return FacesUtil.obterIp();
	}
}
