package br.com.deguste.backbean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.inject.Named;

import org.omnifaces.cdi.ViewScoped;

import br.com.deguste.model.bo.CategoriaBO;
import br.com.deguste.model.bo.FornecedorBO;
import br.com.deguste.model.bo.ProdutoBO;
import br.com.deguste.model.bo.UnidadeMedidaBO;
import br.com.deguste.model.entity.Categoria;
import br.com.deguste.model.entity.Fornecedor;
import br.com.deguste.model.entity.Produto;
import br.com.deguste.model.entity.UnidadeMedida;

@Named
@ViewScoped
public class ProdutoBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4540828415059010832L;
	
	@Inject
	private ProdutoBO produtoBO;
	private Produto produto;
	private List<Produto> listaProdutos;
	
	@Inject
	private FornecedorBO fornecedorBO;
	
	@Inject
	private CategoriaBO categoriaBO;
	private Categoria categoria;
	
	@Inject
	private UnidadeMedidaBO unidadeMedidaBO;
	
	private List <Produto> listaProdutosVenda;
	
	//Rendereds da pagina
	
	private boolean statusRegister;
	private boolean dtRender;
	private boolean cadastroRendered;
	private boolean pesquisaRendered;
	private boolean botaoFecharRendered; 
	private boolean botaoApagarRendered;
	
	
	
	
	public ProdutoBean(){
		this.botaoApagarRendered = false;
		this.setCadastroRendered(false);
		this.setPesquisaRendered(true);
	}
	
	@PostConstruct
	public void init() throws Exception{
		if(produto == null){
			produto = new Produto();
		}
	}
	
	public void limpar(){
		this.produto = new Produto();
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
		this.produto = new Produto();
		if(statusRegister == false){
			this.statusRegister = true;
		}
		this.alterStatusRendered();
	}
	
	public void desabilitaProduto(Produto produto){
		
		try {
			produto.setAtivo(false);
			produtoBO.salvar(produto);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Produto foi exclu�do", null);
		FacesContext.getCurrentInstance().addMessage(null, msg);
		produto = new Produto();
		this.listaProdutos = produtoBO.consultaProduto(produto);
		
	}
	
	public void acaoAlterar(Produto Produto) {
		this.produto = Produto; 
		if (this.produto.getId() != null) {
			this.setStatusRegister(false);
			botaoApagarRendered = true;
			setCadastroRendered(true);
			setPesquisaRendered(false);
		}
	}
	
	public List<SelectItem> listaFornecedores() {
		List<SelectItem> items = new ArrayList<SelectItem>();
		items.add(new SelectItem(null, "Selecione"));
		for (Fornecedor fornecedor : fornecedorBO.selectFornecedores()) {
			items.add(new SelectItem(fornecedor, fornecedor.getNome()));
		}

		return items;
	}
	
	public List<SelectItem> listaCategorias() {
		List<SelectItem> items = new ArrayList<SelectItem>();
		items.add(new SelectItem(null, "Selecione"));
		for (Categoria categoria : categoriaBO.selectFornecedores()) {
			items.add(new SelectItem(categoria, categoria.getNome()));
		}

		return items;
	}
	
	public List<SelectItem> listaUnidades() {
		List<SelectItem> items = new ArrayList<SelectItem>();
		items.add(new SelectItem(null, "Selecione"));
		for (UnidadeMedida unidadeMedida : unidadeMedidaBO.selectFornecedores()) {
			items.add(new SelectItem(unidadeMedida, unidadeMedida.getNome()));
		}

		return items;
	}
	
	public void produtoPorCodigo() {
		try {
			this.listaProdutosVenda = produtoBO.produtoPorCodigo(this.produto);
		if (listaProdutosVenda == null || listaProdutosVenda.isEmpty()) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,
							"Nenhum cliente foi encontrado.", ""));
		}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void lucro() throws Exception{
	try{	
		if(produto.getPreco() != null && produto.getCusto() != null){
		BigDecimal preco = new BigDecimal(0);
		BigDecimal custo = new BigDecimal(0);
		BigDecimal lucro = new BigDecimal(0);
		BigDecimal fim = new BigDecimal(100);
		
		lucro = produto.getPreco().subtract(produto.getCusto()).divide(produto.getCusto()).multiply(fim);
		
		produto.setLucro(lucro);
		}
	}
		catch (Exception e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, e
							.getMessage(), null));
		}
	}
	
	
	public void limpaBean(){
		produto = new Produto();
		listaProdutos = new ArrayList<Produto>();
	}
	
	public void procurarProduto() throws Exception{
		this.listaProdutos = produtoBO.consultaProduto(produto);
		this.dtRender = true;
	}
	
	public void cadastroProduto() {
		try {
			if(produto.getId() == null){
				produto.setAtivo(true);
			}
			
			produtoBO.salvar(produto);
			this.limpaBean();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Os dados foram salvos com sucesso", "Dados cadastrados."));
		} catch (Exception e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), null));
		}
	}
	
	
	
	
	
	
	
	public ProdutoBO getProdutoBO() {
		return produtoBO;
	}
	public void setProdutoBO(ProdutoBO produtoBO) {
		this.produtoBO = produtoBO;
	}
	public Produto getProduto() {
		return produto;
	}
	public void setProduto(Produto produto) {
		this.produto = produto;
	}
	public List<Produto> getListaProdutos() {
		return listaProdutos;
	}
	public void setListaProdutos(List<Produto> listaProdutos) {
		this.listaProdutos = listaProdutos;
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
