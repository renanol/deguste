package br.com.deguste.backbean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.omnifaces.cdi.ViewScoped;
import org.primefaces.event.SelectEvent;

import br.com.deguste.model.bo.EnderecoBO;
import br.com.deguste.model.bo.FornecedorBO;
import br.com.deguste.model.entity.Fornecedor;
import br.com.deguste.model.entity.Usuario;
import br.com.deguste.model.pojos.EnderecoResponse;
@Named
@ViewScoped
public class FornecedorBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3365380944979199645L;


	@Inject
	private Fornecedor fornecedor;
	
	@Inject
	private EnderecoBO enderecoBO;
	
	@Inject
	private FornecedorBO fornecedorBO;
	private List<Fornecedor>listaFornecedores;
	
	//Rendereds da pagina
	
	private boolean statusRegister;
	private boolean dtRender;
	private boolean cadastroRendered;
	private boolean pesquisaRendered;
	private boolean botaoFecharRendered; 
	private boolean botaoApagarRendered;

	public FornecedorBean() {
		// TODO Auto-generated constructor stub
		this.botaoApagarRendered = false;
		this.setCadastroRendered(false);
		this.setPesquisaRendered(true);
		
	}
	
	public void limpar(){
		this.fornecedor = new Fornecedor();
		this.statusRegister = true;
		this.botaoApagarRendered = false;
	}

	
	
	@PostConstruct
	public void init() throws Exception{
		if(fornecedor == null){
			fornecedor = new Fornecedor();
		}
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
		this.fornecedor = new Fornecedor();
		if(statusRegister == false){
			this.statusRegister = true;
		}
		this.alterStatusRendered();
	}
	
	public void desabilitafornecedor(Fornecedor fornecedor){
		
		try {
			fornecedor.setAtivo(false);
			fornecedorBO.salvar(fornecedor);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Fornecedor foi excluído", null);
		FacesContext.getCurrentInstance().addMessage(null, msg);
		fornecedor = new Fornecedor();
		this.listaFornecedores = fornecedorBO.consultaFornecedodor(fornecedor);
		
	}
	
	
	public void acaoAlterar(Fornecedor fornecedor) {
		this.fornecedor = fornecedor; 
		if (this.fornecedor.getId() != null) {
			this.setStatusRegister(false);
			botaoApagarRendered = true;
			setCadastroRendered(true);
			setPesquisaRendered(false);
		}
	}
	
	
	public void selecionarObjetoEvent(SelectEvent event) {
		this.fornecedor = (Fornecedor) event.getObject();
		
		}
	
	
	public void limpaBean(){
		fornecedor = new Fornecedor();
		listaFornecedores = new ArrayList<Fornecedor>();
	}
	
	
	public void procurarFornecedor() throws Exception{
		this.listaFornecedores = fornecedorBO.consultaFornecedodor(fornecedor);
		this.dtRender = true;
	}
	
	public void obterEnderecoPorCep(){
		try{
			if(fornecedor != null && fornecedor.getCep() != null && !fornecedor.getCep().isEmpty()){
				EnderecoResponse endereco = enderecoBO.obterEnderecoPorCep(fornecedor.getCep());
				if(endereco != null){

					fornecedor.setCep(endereco.getCep());
					fornecedor.setTipoLogradouro(endereco.getTipoDeLogradouro());
					fornecedor.setLogradouro(endereco.getLogradouro());
					fornecedor.setBairro(endereco.getBairro());
					fornecedor.setCidade(endereco.getCidade());
					fornecedor.setEstado(endereco.getEstado());

					if(endereco.getLogradouro() == null){
						fornecedor.setCep(null);
						fornecedor.setNumeroLogradouro(null);
						throw new Exception("Endereço não encontrado.");
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			FacesContext ctx = FacesContext.getCurrentInstance();
			ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), null));
		}
	}
	
	
	public void cadastroFornecedor() {
		Calendar dataCadastro = new GregorianCalendar();
		try {
			if(fornecedor.getId() == null){
				fornecedor.setDataCadastro(dataCadastro.getTime());
				fornecedor.setAtivo(true);
			}
			
			fornecedorBO.salvar(fornecedor);
			this.limpaBean();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Os dados foram salvos com sucesso", "Dados cadastrados."));
		} catch (Exception e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), null));
		}
	}


	public Fornecedor getFornecedor() {
		return fornecedor;
	}


	public void setFornecedor(Fornecedor fornecedor) {
		this.fornecedor = fornecedor;
	}


	public FornecedorBO getFornecedorBO() {
		return fornecedorBO;
	}


	public void setFornecedorBO(FornecedorBO fornecedorBO) {
		this.fornecedorBO = fornecedorBO;
	}


	public List<Fornecedor> getListaFornecedores() {
		return listaFornecedores;
	}


	public void setListaFornecedores(List<Fornecedor> listaFornecedores) {
		this.listaFornecedores = listaFornecedores;
	}


	public boolean isStatusRegister() {
		return statusRegister;
	}


	public void setStatusRegister(boolean statusRegister) {
		this.statusRegister = statusRegister;
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

	public boolean isDtRender() {
		return dtRender;
	}

	public void setDtRender(boolean dtRender) {
		this.dtRender = dtRender;
	}
	
	

	



}
