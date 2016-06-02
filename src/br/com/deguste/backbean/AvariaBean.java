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

import br.com.deguste.model.bo.AvariaBO;
import br.com.deguste.model.entity.Avaria;

@Named
@ViewScoped
public class AvariaBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6630106852726126560L;
	
	@Inject
	private AvariaBO avariaBO;
	private Avaria avaria;
	private List<Avaria> listaAvarias;
	
	//Rendereds da pagina
	
	private boolean statusRegister;
	private boolean dtRender;
	private boolean cadastroRendered;
	private boolean pesquisaRendered;
	private boolean botaoFecharRendered; 
	private boolean botaoApagarRendered;
	
	public AvariaBean(){
		this.botaoApagarRendered = false;
		this.setCadastroRendered(false);
		this.setPesquisaRendered(true);
	}
	
	@PostConstruct
	public void init() throws Exception{
		if(avaria == null){
			avaria = new Avaria();
		}
	}
	
	public void limpar(){
		this.avaria = new Avaria();
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
		this.avaria = new Avaria();
		if(statusRegister == false){
			this.statusRegister = true;
		}
		this.alterStatusRendered();
	}
	
	public void desabilitaAvaria(Avaria avaria){
		
		try {
			avaria.setAtivo(false);
			avariaBO.salvar(avaria);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Avaria foi excluído", null);
		FacesContext.getCurrentInstance().addMessage(null, msg);
		avaria = new Avaria();
		this.listaAvarias = avariaBO.consultaAvaria(avaria);
		
	}
	
	public void acaoAlterar(Avaria avaria) {
		this.avaria = avaria; 
		if (this.avaria.getId() != null) {
			this.setStatusRegister(false);
			botaoApagarRendered = true;
			setCadastroRendered(true);
			setPesquisaRendered(false);
		}
	}
	
	public void limpaBean(){
		avaria = new Avaria();
		listaAvarias = new ArrayList<Avaria>();
	}
	
	public void procurarAvaria() throws Exception{
		this.listaAvarias = avariaBO.consultaAvaria(avaria);
		this.dtRender = true;
	}
	
	public void cadastroAvaria() {
		try {
			if(avaria.getId() == null){
				avaria.setAtivo(true);
			}
			
			avariaBO.salvar(avaria);
			this.limpaBean();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Os dados foram salvos com sucesso", "Dados cadastrados."));
		} catch (Exception e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), null));
		}
	}

	public AvariaBO getAvariaBO() {
		return avariaBO;
	}

	public void setAvariaBO(AvariaBO avariaBO) {
		this.avariaBO = avariaBO;
	}

	public Avaria getAvaria() {
		return avaria;
	}

	public void setAvaria(Avaria avaria) {
		this.avaria = avaria;
	}

	public List<Avaria> getListaAvarias() {
		return listaAvarias;
	}

	public void setListaAvarias(List<Avaria> listaAvarias) {
		this.listaAvarias = listaAvarias;
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
