package br.com.deguste.util;

import java.io.IOException;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class FacesUtil {

	public static FacesContext obterFacesContext() {
		return FacesContext.getCurrentInstance();
	}

	public static String obterCaminhoReal(String caminho) {
		ServletContext sc = (ServletContext) obterFacesContext().getExternalContext().getContext();
		return sc.getRealPath(caminho);
	}
	
	public static String obterCaminhoContexto() {
		ServletContext sc = (ServletContext) obterFacesContext().getExternalContext().getContext();
		return sc.getContextPath();
	}

	public static HttpServletRequest obterRequest() {
		return (HttpServletRequest) obterFacesContext().getExternalContext().getRequest();
	}

	public static HttpServletResponse obterResponse() {
        return (HttpServletResponse) obterFacesContext().getExternalContext().getResponse();
    }
		//da redirect na pagina
	public static void redirectTo(String page) {
		try {
			obterResponse().sendRedirect(page+".jsf");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void registrarMensagemInfo(String mensagemInfo){
		obterFacesContext().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, null, mensagemInfo));
	}

	public static void registrarMensagemAlerta(String mensagemAlerta){
		obterFacesContext().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, null, mensagemAlerta));
	}

	public static void registrarMensagemErro(String mensagemErro){
		obterFacesContext().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, null, mensagemErro));
	}

	public static void registrarMensagemErroFatal(String mensagemErroFatal){
		obterFacesContext().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, null, mensagemErroFatal));
	}
}
