package br.com.deguste.validator;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import javax.inject.Inject;

import br.com.deguste.model.bo.UsuarioBO;


@FacesValidator("br.gov.pb.joaopessoa.validator.ExisteNomeValidator")
public class ExisteNomeValidator implements Validator{

	@Inject private UsuarioBO usuarioBO;
	
	@Override
	public void validate(FacesContext context, UIComponent component, Object value) {
		String nome = value.toString();
		
		if (usuarioBO.selectUsuarioByNome(nome)!= null){
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Já existe esse nome no sistema", null);
			throw new ValidatorException(msg);
		}
	}
}
