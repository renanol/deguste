package br.com.deguste.backbean;

import java.awt.Image;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
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
import javax.swing.ImageIcon;

import org.omnifaces.cdi.ViewScoped;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;

import br.com.deguste.enumeration.ModuloImage;
import br.com.deguste.model.bo.EnderecoBO;
import br.com.deguste.model.bo.FuncionarioBO;
import br.com.deguste.model.entity.Funcionario;
import br.com.deguste.util.ImageUtils;
import br.com.deguste.util.Util;

@Named
@ViewScoped
public class FuncionarioBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2710225901918052936L;
	
	
	private Funcionario funcionario;
	
	@Inject
	private FuncionarioBO funcionarioBO;
	
	@Inject
	private EnderecoBO enderecoBO; 
	
	private List<Funcionario> listaFuncionarios;
	
	//Rendereds da pagina
	
	private boolean statusRegister;
	private boolean cadastroRendered;
	private boolean pesquisaRendered;
	private boolean botaoFecharRendered; 
	private boolean botaoApagarRendered;
	
	
	private UploadedFile file;
	private String loteImagem;
	private InputStream inputStream;
	private StreamedContent uploadedFileAsStream;
	
	public FuncionarioBean(){
		this.botaoApagarRendered = false;
		this.setCadastroRendered(false);
		this.setPesquisaRendered(true);
	}
	
	public void limpar(){
		this.funcionario = new Funcionario();
		this.statusRegister = true;
		this.botaoApagarRendered = false;
	}

	
	
	@PostConstruct
	public void init() throws Exception{
		if(funcionario == null){
			funcionario = new Funcionario();
		}
		
		this.procurarFuncionario();
		
		
	}
	
	public void acaoPesquisar(){
		this.limpaBean();
		this.alterStatusRendered();
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
		this.funcionario = new Funcionario();
		if(statusRegister == false){
			this.statusRegister = true;
		}
		this.alterStatusRendered();
	}
	
	
	public void acaoAlterar() throws NoSuchFieldException, SecurityException,
	InstantiationException, IllegalAccessException {
if (this.funcionario.getId() == null) {
	FacesContext.getCurrentInstance().addMessage(null,
			new FacesMessage(FacesMessage.SEVERITY_WARN,
					"O funcionario não foi selecionado.", ""));
} else {
	this.setStatusRegister(false);
	botaoApagarRendered = true;
	setCadastroRendered(true);
	setPesquisaRendered(false);
}
}
	
	public void selecionarObjetoEvent(SelectEvent event) {
		this.funcionario = (Funcionario) event.getObject();
		
		}
	
	
	public void limpaBean(){
		funcionario = new Funcionario();
		listaFuncionarios = new ArrayList<Funcionario>();
	}
	
	
	public void procurarFuncionario() throws Exception{
		this.listaFuncionarios = funcionarioBO.consultaFuncionario(funcionario);
	}
	
//	public void obterEnderecoPorCep(){
//		try{
//			if(funcionario != null && funcionario.getCep() != null && !funcionario.getCep().isEmpty()){
//				EnderecoResponse endereco = enderecoBO.obterEnderecoPorCep(funcionario.getCep());
//				if(endereco != null){
//
//					funcionario.setCep(endereco.getCep());
//					funcionario.setTipoLogradouro(endereco.getTipoDeLogradouro());
//					funcionario.setLogradouro(endereco.getLogradouro());
//					funcionario.setBairro(endereco.getBairro());
//					funcionario.setCidade(endereco.getCidade());
//					funcionario.setEstado(endereco.getEstado());
//
//					if(endereco.getLogradouro() == null){
//						funcionario.setCep(null);
//						funcionario.setNumeroLogradouro(null);
//						throw new Exception("Endereço não encontrado.");
//					}
//				}
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//			FacesContext ctx = FacesContext.getCurrentInstance();
//			ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), null));
//		}
//	}
	
	
	public void cadastrofuncionario() {
		Calendar dataCadastro = new GregorianCalendar();
		try {
			if(funcionario.getId() == null){
				funcionario.setDataCadastro(dataCadastro.getTime());
				funcionario.setAtivo(true);
			}
			
			funcionarioBO.salvar(funcionario);
			this.upload(funcionario);
			this.limpaBean();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Os dados foram salvos com sucesso", "Dados cadastrados."));
		} catch (Exception e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), null));
		}
	}
	
	private void upload(Funcionario funcionario) {

		if (file != null) {

			String folder_file = Util.getPastaFotos() + File.separator
					+ funcionario.getId();

			try { 
				if((new File(folder_file)).exists()){
					new File(folder_file).delete();
				}
				File targetFolder = new File(folder_file);
				targetFolder.mkdirs();

				String[] extensao = file.getContentType().split("/");
				String arquivo = ModuloImage.FUNCIONARIO.toString() + "."
						+ extensao[1];

				OutputStream out = new FileOutputStream(new File(targetFolder,
						arquivo));

				int read = 0;
				byte[] bytes = new byte[2048];

				while ((read = inputStream.read(bytes)) != -1) {
					out.write(bytes, 0, read);
				}
				inputStream.close();
				out.flush();
				out.close();

				funcionario.setTipoFoto(extensao[1]);

				String caminhoCompleto = folder_file + File.separator + arquivo;

				redimencionaImagem(caminhoCompleto);

				funcionarioBO.update(funcionario);
			} catch (IOException e) {
				e.printStackTrace();
				new FacesMessage(FacesMessage.SEVERITY_ERROR, "Falha",
						"Ocorreu uma falha no upload do arquivo.");
			}
		}
	}
	
	public void uploadFile(FileUploadEvent event) throws IOException {
		file = event.getFile();
		inputStream = file.getInputstream();
	}
	
	public boolean existeImagem() throws IOException {
		String folder_file = Util.getPastaFotos() + File.separator + this.funcionario.getId() + File.separator + ModuloImage.FUNCIONARIO.toString() + "." + this.funcionario.getTipoFoto();
		File foto = new File(folder_file);
		return foto.exists();
	}
	
	private void redimencionaImagem(String caminho) {
		Image imagem = null;

		try {
			imagem = new ImageIcon(caminho).getImage();
		} catch (Exception e) {
			e.printStackTrace();
		}

		int tipo = ImageUtils.IMAGE_JPEG;

		try {
			ImageUtils.saveImage(
					ImageUtils.resizeImageProportional(imagem, tipo, 650),
					caminho, tipo);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	public Funcionario getFuncionario() {
		return funcionario;
	}

	public void setFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
	}

	public FuncionarioBO getFuncionarioBO() {
		return funcionarioBO;
	}

	public void setFuncionarioBO(FuncionarioBO funcionarioBO) {
		this.funcionarioBO = funcionarioBO;
	}

	public EnderecoBO getEnderecoBO() {
		return enderecoBO;
	}

	public void setEnderecoBO(EnderecoBO enderecoBO) {
		this.enderecoBO = enderecoBO;
	}

	public List<Funcionario> getListaFuncionarios() {
		return listaFuncionarios;
	}

	public void setListaFuncionarios(List<Funcionario> listaFuncionarios) {
		this.listaFuncionarios = listaFuncionarios;
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

	public UploadedFile getFile() {
		return file;
	}

	public void setFile(UploadedFile file) {
		this.file = file;
	}

	public String getLoteImagem() {
		return loteImagem;
	}

	public void setLoteImagem(String loteImagem) {
		this.loteImagem = loteImagem;
	}

	public InputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	public StreamedContent getUploadedFileAsStream() {
		return uploadedFileAsStream;
	}

	public void setUploadedFileAsStream(StreamedContent uploadedFileAsStream) {
		this.uploadedFileAsStream = uploadedFileAsStream;
	}
	
	
	
	
	

}
