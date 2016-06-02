package br.com.deguste.util;

import java.awt.image.RenderedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.Date;

import javax.faces.context.FacesContext;
import javax.imageio.ImageIO;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.primefaces.util.Base64;

public class Util {
	public static Object getManagedBean(String managedBean){
		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
		HttpSession session = (HttpSession) request.getSession();
		return session.getAttribute(managedBean);
	}
	
	public static String obterCaminhoReal(String caminho) {
        ServletContext sc = (ServletContext) FacesContext.getCurrentInstance()
                .getExternalContext().getContext();
        return sc.getRealPath(caminho);
    }
	
	public static String getPastaFotos(){
		StringBuffer caminho = new StringBuffer();
//		caminho.append(System.getProperty("catalina.base"));
		caminho.append("//home//renanoliveira//public");
		
		// /data/content/farma/Farmacias/fotos
		caminho.append(File.separator);
		caminho.append("data");
		caminho.append(File.separator);
		caminho.append("content");
		caminho.append(File.separator);
		caminho.append("Funcionarios");
		caminho.append(File.separator);
		caminho.append("fotos");
		return caminho.toString();
	}
	
	public static String getPastaTemp(){
		StringBuffer caminho = new StringBuffer();
		caminho.append("C:/Temp_farma_imagens");
		
		caminho.append(File.separator);
		caminho.append("data");
		caminho.append(File.separator);
		caminho.append("content");
		caminho.append(File.separator);
		caminho.append("Produtos");
		caminho.append(File.separator);
		caminho.append("fotos");
		return caminho.toString();
	}
	
	public static String chartToFileImageTemp(String base64, String nameFile) throws IOException{
		Date hoje = new Date();
    	String nomeArquivo = SHA.bytesToHex(SHA.hash256(nameFile + hoje.getTime())) + 
							 ".png";
    	String caminho = Util.getPastaFotos() + File.separator + nomeArquivo;
		if(base64.split(",").length > 1){
	        String encoded = base64.split(",")[1];
	        byte[] decoded = Base64.decode(encoded);

            RenderedImage renderedImage = ImageIO.read(new ByteArrayInputStream(decoded));
            ImageIO.write(renderedImage, "png", new File(caminho)); // use a proper path & file name here.
	    }else{
	    	caminho = "";
	    }
		return caminho;
	}
}
