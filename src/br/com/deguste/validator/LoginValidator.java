package br.com.deguste.validator;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.deguste.model.bo.UsuarioBO;


@Named
@FacesValidator("br.com.farmacia.validator.LoginValidator")
public class LoginValidator implements Validator{

	@Inject 
	private UsuarioBO usuarioBO;
	
	@Override
	public void validate(FacesContext context, UIComponent component, Object value) {
		String login = value.toString();
		
//		if (usuarioBO.existeLogin(login)){
//			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Login já utilizado", null);
//			throw new ValidatorException(msg);
//		}
	}
}
