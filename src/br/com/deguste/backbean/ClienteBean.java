package br.com.deguste.backbean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.omnifaces.cdi.ViewScoped;

import br.com.deguste.model.bo.ClienteBO;
import br.com.deguste.model.entity.Cliente;

@Named
@ViewScoped
public class ClienteBean implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3394966485451451790L;
	
	
	@Inject
	private ClienteBO clienteBO;
	private Cliente cliente;
	private List<Cliente> listaClientes;
	
	
	//Rendereds da pagina
	
	private boolean statusRegister;
	private boolean dtRender;
	private boolean cadastroRendered;
	private boolean pesquisaRendered;
	private boolean botaoFecharRendered; 
	private boolean botaoApagarRendered;
	
	public ClienteBean(){
		this.botaoApagarRendered = false;
		this.setCadastroRendered(false);
		this.setPesquisaRendered(true);
	}
	
	@PostConstruct
	public void init() throws Exception{
		if(cliente == null){
			cliente = new Cliente();
		}
	}
	
	public void limpar(){
		this.cliente = new Cliente();
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
		this.cliente = new Cliente();
		if(statusRegister == false){
			this.statusRegister = true;
		}
		this.alterStatusRendered();
	}
	
	public void desabilitaCliente(Cliente cliente){
		
		try {
			cliente.setAtivo(false);
			clienteBO.salvar(cliente);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Cliente foi exclu�do", null);
		FacesContext.getCurrentInstance().addMessage(null, msg);
		cliente = new Cliente();
		//this.listaClientes = clienteBO.(cliente);
		
	}
	
	public void acaoAlterar(Cliente cliente) {
		this.cliente = cliente; 
		if (this.cliente.getId() != null) {
			this.setStatusRegister(false);
			botaoApagarRendered = true;
			setCadastroRendered(true);
			setPesquisaRendered(false);
		}
	}
	
	public void limpaBean(){
		cliente = new Cliente();
		listaClientes = new ArrayList<Cliente>();
	}
	
	public void procurarCliente() throws Exception{
		this.listaClientes = clienteBO.consultaClienteAtivo(cliente);
		this.dtRender = true;
	}
	
	public void cadastroCliente() {
		try {
			if(cliente.getId() == null){
				cliente.setAtivo(true);
			}
			
			clienteBO.salvar(cliente);
			this.limpaBean();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Os dados foram salvos com sucesso", "Dados cadastrados."));
		} catch (Exception e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), null));
		}
	}
	
	//pra testar
	public void gerenciarCliente(){
		try {
		if(cliente.getId() != null){
			cliente.setCredito(cliente.getCredito().abs());
			clienteBO.update(cliente);
		}
		} catch (Exception e) {
			e.printStackTrace();
		}
		FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
				"Atualiza��o no cliente foi atualizado", null);
		FacesContext.getCurrentInstance().addMessage(null, msg);

	}

	public ClienteBO getClienteBO() {
		return clienteBO;
	}

	public void setClienteBO(ClienteBO clienteBO) {
		this.clienteBO = clienteBO;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public List<Cliente> getListaClientes() {
		return listaClientes;
	}

	public void setListaClientes(List<Cliente> listaClientes) {
		this.listaClientes = listaClientes;
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




