package br.com.deguste.backbean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

import br.com.deguste.enumeration.PerfilAcesso;
import br.com.deguste.model.bo.ClienteBO;
import br.com.deguste.model.bo.FuncionarioBO;
import br.com.deguste.model.bo.UsuarioBO;
import br.com.deguste.model.entity.Cliente;
import br.com.deguste.model.entity.Funcionario;
import br.com.deguste.model.entity.Usuario;
import br.com.deguste.util.NavigationUtil;
import br.com.deguste.util.SHA;
import br.com.deguste.util.SessionControl;





@Named
@ManagedBean
@org.omnifaces.cdi.ViewScoped
public class UsuarioBean implements Serializable {

	private static final long serialVersionUID = 2656864881305209232L;

	private Usuario usuario;
	private String antigaSenha;
	private String novaSenha;
	@Inject 
	private UsuarioBO usuarioBO;
	private List<Usuario> listaUsuarios;
	
	private List<Usuario> filtraUsuarios;
	private List<PerfilAcesso> allperfis;
	private String param;
	private boolean editarSenha; 
	
	private boolean statusRegister;
	
	private boolean dtRender;
	private boolean cadastroRendered;
	private boolean pesquisaRendered;
	private boolean botaoFecharRendered; 
	private boolean botaoApagarRendered;
	
	
	@Inject
	private SessionControl session;
	
	@Inject
	private FuncionarioBO funcionarioBO;
	private Funcionario funcionario;
	
	@Inject
	private ClienteBO clienteBO;
	private Cliente cliente;
	
	public UsuarioBean(){
		this.botaoApagarRendered = false;
		this.setCadastroRendered(false);
		this.setPesquisaRendered(true);
		this.dtRender = false;
	}
	
	
	
	@PostConstruct
	public void inicialization() {
		if(usuario == null){
		this.usuario = new Usuario();
		}	
	}


	public UsuarioBO getUsuarioBO() {
		return usuarioBO;
	}
	

	public void setUsuarioBO(UsuarioBO usuarioBO) {
		this.usuarioBO = usuarioBO;
	}

	
	public void cadastroUsuario() {
		Calendar dataCadastro = new GregorianCalendar();
		Funcionario funcSelecionado = new Funcionario();
		try {
			if(usuario.getId() == null){
				usuario.setDataCadastro(dataCadastro.getTime());
				usuario.setPrimeiroAcesso(true);
				usuario.setAtivo(true);
			}
			if(usuario.getNome().trim().isEmpty()){
				throw new Exception("O nome está vazio!");
			}
			
			usuarioBO.salvar(usuario);
			this.limpaBean();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Os dados foram salvos com sucesso", "Dados cadastrados."));
		} catch (Exception e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), null));
		}
	}

	public void alteraSenha() {
		FacesContext context = FacesContext.getCurrentInstance();
		RequestContext contextRequest = RequestContext.getCurrentInstance(); 
		usuario = session.getUsuarioSession();
		
		if(usuario.getSenha() != null){
		
		if (usuario.isPrimeiroAcesso()){
			usuario.setSenha(novaSenha);
			usuario.setPrimeiroAcesso(false);

			try {
				usuarioBO.update(usuario);
			} catch (Exception e) {
				e.printStackTrace();
			}
			contextRequest.execute("PF('dlgSenha').show();");  
			return;

		} else {
			String hashSenhaAntiga = SHA.bytesToHex(SHA.hash256(antigaSenha));
			if (usuario.getSenha().equals(hashSenhaAntiga)){
				usuario.setSenha(novaSenha);

				try {
					usuarioBO.update(usuario);
				} catch (Exception e) {
					e.printStackTrace();
				}
				contextRequest.execute("PF('dlgSenhaAlterada').show();");  

			} else {
				context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Senha antiga não confere", "Senha antiga não confere"));
				return;
			}
		} 
		
		
		} else {
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "O campo senha está vazio", ""));
			return;
		}
	}
	
	public void mudarSenha() {
		FacesContext context = FacesContext.getCurrentInstance();
		RequestContext contextRequest = RequestContext.getCurrentInstance(); 
		usuario = session.getUsuarioSession();

		if (usuario.isPrimeiroAcesso()){
			usuario.setSenha(novaSenha);
			usuario.setPrimeiroAcesso(false);

			try {
				usuarioBO.salvar(usuario);
			} catch (Exception e) {
				e.printStackTrace();
			}
			contextRequest.execute("PF('dlgSenhaAlterada').show();");  
			return;

		} else {
			String hashSenhaAntiga = SHA.bytesToHex(SHA.hash256(antigaSenha));
			if (usuario.getSenha().equals(hashSenhaAntiga)){
				usuario.setSenha(novaSenha);

				try {
					usuarioBO.salvar(usuario);
				} catch (Exception e) {
					e.printStackTrace();
				}
				contextRequest.execute("PF('dlgSenhaAlterada').show();");  

			} else {
				context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Senha antiga não confere", "Senha antiga não confere"));
				return;
			}
		}
	}
	public void alteraUsuario() {
		try {
			if(usuario.getId() == 0){
			}
			if(usuario.getNome().trim().isEmpty()){
				throw new Exception("O nome está vazio!");
				 
			}
			usuario.setPrimeiroAcesso(true);
			usuarioBO.salvar(usuario);
			this.limpaBean();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "O usuário foi alterado com sucesso!", "Dados cadastrados."));
			
			FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
			
		} catch (Exception e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), null));
		
		}
		
	}
	

	public String alteraSenhaRedirect(){
		if (usuario.isPrimeiroAcesso()){
			return NavigationUtil.TO_CONSULTA_USUARIO;
		} else {
			String hashSenhaAntiga = SHA.bytesToHex(SHA.hash256(usuario.getSenha()));
			if (usuario.getSenha() == hashSenhaAntiga){
				return NavigationUtil.TO_HOME;
			} else {
				return NavigationUtil.TO_PAGES_HOME;
			}
		}
	}
	
	public void resetarSenhaPadrao(Usuario usuario){
		try {
			if (usuario.getId() != null) {
				usuario.setPrimeiroAcesso(true);
				usuario.setSenha("mudar123");
				usuarioBO.update(usuario);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
				"A senha do usuário foi alterada, a senha agora é mudar123", null);
		FacesContext.getCurrentInstance().addMessage(null, msg);

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
		this.usuario = new Usuario();
		if(statusRegister == false){
			this.statusRegister = true;
		}
		this.alterStatusRendered();
	}
	
	public void acaoPesquisar(){
		this.limpaBean();
		this.alterStatusRendered();
		this.dtRender = false;
	}
	


	public void desabilitaUsuario(Usuario usuario){
		
		try {
			usuario.setAtivo(false);
			usuarioBO.salvar(usuario);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Usuário foi excluído", null);
		FacesContext.getCurrentInstance().addMessage(null, msg);
		usuario = new Usuario();
		this.listaUsuarios = usuarioBO.procurarUsuario(usuario);
		
	}
	
	public List<SelectItem> allPerfil(){
		List<SelectItem> items = new ArrayList<SelectItem>();
		items.add(new SelectItem("Selecione"));
		for(PerfilAcesso perfil : PerfilAcesso.values())
			items.add(new SelectItem(perfil, perfil.getDescricao()));

		return items;
	}
	
	public void procurarUsuario(){
		this.listaUsuarios = usuarioBO.procurarUsuario(this.usuario);
		this.dtRender = true;
	}
	
	
	
	public void selecionarObjetoEvent(SelectEvent event) {
		this.usuario = (Usuario) event.getObject();
		
		}
	
	
	public void acaoAlterar(Usuario usuario) {
		this.usuario = usuario; 
		if (this.usuario.getId() != null) {
			this.setStatusRegister(false);
			botaoApagarRendered = true;
			setCadastroRendered(true);
			setPesquisaRendered(false);
		}
	}
	
	public void limpar(){
		this.usuario = new Usuario();
		this.statusRegister = true;
		this.botaoApagarRendered = false;
	}
	
	public List<SelectItem>listaFuncionariosAtivos() throws Exception{
		List<SelectItem> itens = new ArrayList<SelectItem>();
		itens.add(new SelectItem(null, "Selecione"));
		for(Funcionario func : funcionarioBO.getFuncionariosAtivos()){
			itens.add(new SelectItem(func, func.getNome()));
		}
		return itens;
	}
	
	public List<SelectItem>listaClientesAtivos() throws Exception{
		List<SelectItem> itens = new ArrayList<SelectItem>();
		itens.add(new SelectItem(null, "Selecione"));
		for(Cliente cliente : clienteBO.getClientesAtivos()){
			itens.add(new SelectItem(cliente, cliente.getNome()));
		}
		return itens;
	}

	
	

	public void limpaBean(){
		usuario = new Usuario();
		listaUsuarios = new ArrayList<Usuario>();
	}

	
	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public List<Usuario> getListaUsuarios() throws Exception {
		//this.listaUsuarios = usuarioBO.getUsuariosAtivos();
		return listaUsuarios;
	}

	public void setListaUsuarios(List<Usuario> listaUsuarios) {
		this.listaUsuarios = listaUsuarios;
	}

	public List<Usuario> getFiltraUsuarios() {
		return filtraUsuarios;
	}

	public void setFiltraUsuarios(List<Usuario> filtraUsuarios) {
		this.filtraUsuarios = filtraUsuarios;
	}

	public List<PerfilAcesso> getAllperfis() {
		return allperfis;
	}

	public void setAllperfis(List<PerfilAcesso> allperfis) {
		this.allperfis = allperfis;
	}


	public String getAntigaSenha() {
		return antigaSenha;
	}


	public void setAntigaSenha(String antigaSenha) {
		this.antigaSenha = antigaSenha;
	}


	public String getNovaSenha() {
		return novaSenha;
	}


	public void setNovaSenha(String novaSenha) {
		this.novaSenha = novaSenha;
	}

	public String getParam() {
		return param;
	}

	public void setParam(String param) throws Exception {
		try{
			this.param = param;
			if(param != null && !param.isEmpty())
				this.usuario = usuarioBO.selectById(Long.valueOf(param));

		} catch (NumberFormatException e) {
			throw new Exception("Formato de parametro ilegal");
		} catch (IllegalArgumentException e) {
			throw new Exception("Argumento ilegal");
		} catch (Exception e) {
			throw new Exception("Erro inesperado!");
		}
	}

	public boolean isEditarSenha() {
		return editarSenha;
	}

	public void setEditarSenha(boolean editarSenha) {
		this.editarSenha = editarSenha;
	}


	public SessionControl getSession() {
		return session;
	}

	public void setSession(SessionControl session) {
		this.session = session;
	}


	public Funcionario getFuncionario() {
		return funcionario;
	}

	public void setFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
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

	public FuncionarioBO getFuncionarioBO() {
		return funcionarioBO;
	}

	public void setFuncionarioBO(FuncionarioBO funcionarioBO) {
		this.funcionarioBO = funcionarioBO;
	}

	public boolean isDtRender() {
		return dtRender;
	}

	public void setDtRender(boolean dtRender) {
		this.dtRender = dtRender;
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
	
	
	
	
}
