package br.com.deguste.backbean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.omnifaces.cdi.ViewScoped;

import br.com.deguste.model.bo.UnidadeMedidaBO;
import br.com.deguste.model.entity.UnidadeMedida;


@Named
@ViewScoped
public class UnidadeMedidaBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5056601181300670434L;
	
	@Inject
	private UnidadeMedidaBO unidadeMedidaBO;
	private UnidadeMedida unidadeMedida;
	private List<UnidadeMedida> listaUnidadeMedidas;
	
	//Rendereds da pagina
	
	private boolean statusRegister;
	private boolean dtRender;
	private boolean cadastroRendered;
	private boolean pesquisaRendered;
	private boolean botaoFecharRendered; 
	private boolean botaoApagarRendered;
	
	public UnidadeMedidaBean(){
		this.botaoApagarRendered = false;
		this.setCadastroRendered(false);
		this.setPesquisaRendered(true);
	}
	
	@PostConstruct
	public void init() throws Exception{
		if(unidadeMedida == null){
			unidadeMedida = new UnidadeMedida();
		}
	}
	
	public void limpar(){
		this.unidadeMedida = new UnidadeMedida();
		this.statusRegister = true;
		this.botaoApagarRendered = false;
	}

	
	public void acaoPesquisar(){
		this.limpaBean();
		this.alterStatusRendered();
		this.dtRender = false;
	}
	
	public void alterStatusRendered() {
		if (cadastroRendered) {
			setCadastroRendered(false);
			setPesquisaRendered(true);
		} else if (pesquisaRendered) {
			setCadastroRendered(true);
			setPesquisaRendered(false);
		}
	}
	
	public void acaoCadastrar(){
		this.unidadeMedida = new UnidadeMedida();
		if(statusRegister == false){
			this.statusRegister = true;
		}
		this.alterStatusRendered();
	}
	
	public void desabilitaunidadeMedida(UnidadeMedida unidadeMedida){
		
		try {
			unidadeMedida.setAtivo(false);
			unidadeMedidaBO.salvar(unidadeMedida);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "unidadeMedida foi excluído", null);
		FacesContext.getCurrentInstance().addMessage(null, msg);
		unidadeMedida = new UnidadeMedida();
		this.listaUnidadeMedidas = unidadeMedidaBO.consultaUnidadeMedida(unidadeMedida);
		
	}
	
	public void acaoAlterar(UnidadeMedida unidadeMedida) {
		this.unidadeMedida = unidadeMedida; 
		if (this.unidadeMedida.getId() != null) {
			this.setStatusRegister(false);
			botaoApagarRendered = true;
			setCadastroRendered(true);
			setPesquisaRendered(false);
		}
	}
	
	public void limpaBean(){
		unidadeMedida = new UnidadeMedida();
		listaUnidadeMedidas = new ArrayList<UnidadeMedida>();
	}
	
	public void procurarunidadeMedida() throws Exception{
		this.listaUnidadeMedidas = unidadeMedidaBO.consultaUnidadeMedida(unidadeMedida);
		this.dtRender = true;
	}
	
	public void cadastrounidadeMedida() {
		try {
			if(unidadeMedida.getId() == null){
				unidadeMedida.setAtivo(true);
			}
			
			unidadeMedidaBO.salvar(unidadeMedida);
			this.limpaBean();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Os dados foram salvos com sucesso", "Dados cadastrados."));
		} catch (Exception e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), null));
		}
	}

	public UnidadeMedidaBO getUnidadeMedidaBO() {
		return unidadeMedidaBO;
	}

	public void setUnidadeMedidaBO(UnidadeMedidaBO unidadeMedidaBO) {
		this.unidadeMedidaBO = unidadeMedidaBO;
	}

	public UnidadeMedida getUnidadeMedida() {
		return unidadeMedida;
	}

	public void setUnidadeMedida(UnidadeMedida unidadeMedida) {
		this.unidadeMedida = unidadeMedida;
	}

	public List<UnidadeMedida> getListaUnidadeMedidas() {
		return listaUnidadeMedidas;
	}

	public void setListaUnidadeMedidas(List<UnidadeMedida> listaUnidadeMedidas) {
		this.listaUnidadeMedidas = listaUnidadeMedidas;
	}

	public boolean isStatusRegister() {
		return statusRegister;
	}

	public void setStatusRegister(boolean statusRegister) {
		this.statusRegister = statusRegister;
	}

	public boolean isDtRender() {
		return dtRender;
	}

	public void setDtRender(boolean dtRender) {
		this.dtRender = dtRender;
	}

	public boolean isCadastroRendered() {
		return cadastroRendered;
	}

	public void setCadastroRendered(boolean cadastroRendered) {
		this.cadastroRendered = cadastroRendered;
	}

	public boolean isPesquisaRendered() {
		return pesquisaRendered;
	}

	public void setPesquisaRendered(boolean pesquisaRendered) {
		this.pesquisaRendered = pesquisaRendered;
	}

	public boolean isBotaoFecharRendered() {
		return botaoFecharRendered;
	}

	public void setBotaoFecharRendered(boolean botaoFecharRendered) {
		this.botaoFecharRendered = botaoFecharRendered;
	}

	public boolean isBotaoApagarRendered() {
		return botaoApagarRendered;
	}

	public void setBotaoApagarRendered(boolean botaoApagarRendered) {
		this.botaoApagarRendered = botaoApagarRendered;
	}
	
	
	


}
