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

import br.com.deguste.model.bo.CategoriaBO;
import br.com.deguste.model.entity.Categoria;

@Named
@ViewScoped
public class CategoriaBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4544861486839002470L;
	
	@Inject
	private CategoriaBO categoriaBO;
	private Categoria categoria;
	private List<Categoria> listaCategorias;
	
	//Rendereds da pagina
	
	private boolean statusRegister;
	private boolean dtRender;
	private boolean cadastroRendered;
	private boolean pesquisaRendered;
	private boolean botaoFecharRendered; 
	private boolean botaoApagarRendered;
	
	public CategoriaBean(){
		this.botaoApagarRendered = false;
		this.setCadastroRendered(false);
		this.setPesquisaRendered(true);
	}
	
	@PostConstruct
	public void init() throws Exception{
		if(categoria == null){
			categoria = new Categoria();
		}
	}
	
	public void limpar(){
		this.categoria = new Categoria();
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
		this.categoria = new Categoria();
		if(statusRegister == false){
			this.statusRegister = true;
		}
		this.alterStatusRendered();
	}
	
	public void desabilitacategoria(Categoria categoria){
		
		try {
			categoria.setAtivo(false);
			categoriaBO.salvar(categoria);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Categoria foi excluído", null);
		FacesContext.getCurrentInstance().addMessage(null, msg);
		categoria = new Categoria();
		this.listaCategorias = categoriaBO.consultaCategoria(categoria);
		
	}
	
	public void acaoAlterar(Categoria categoria) {
		this.categoria = categoria; 
		if (this.categoria.getId() != null) {
			this.setStatusRegister(false);
			botaoApagarRendered = true;
			setCadastroRendered(true);
			setPesquisaRendered(false);
		}
	}
	
	public void limpaBean(){
		categoria = new Categoria();
		listaCategorias = new ArrayList<Categoria>();
	}
	
	public void procurarcategoria() throws Exception{
		this.listaCategorias = categoriaBO.consultaCategoria(categoria);
		this.dtRender = true;
	}
	
	public void cadastrocategoria() {
		try {
			if(categoria.getId() == null){
				categoria.setAtivo(true);
			}
			
			categoriaBO.salvar(categoria);
			this.limpaBean();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Os dados foram salvos com sucesso", "Dados cadastrados."));
		} catch (Exception e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), null));
		}
	}
	

	public CategoriaBO getCategoriaBO() {
		return categoriaBO;
	}

	public void setCategoriaBO(CategoriaBO categoriaBO) {
		this.categoriaBO = categoriaBO;
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	public List<Categoria> getListaCategorias() {
		return listaCategorias;
	}

	public void setListaCategorias(List<Categoria> listaCategorias) {
		this.listaCategorias = listaCategorias;
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
