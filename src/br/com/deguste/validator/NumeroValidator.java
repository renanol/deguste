package br.com.deguste.validator;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import br.com.deguste.util.StringUtil;


@FacesValidator("br.gov.pb.joaopessoa.validator.NumeroValidator")
public class NumeroValidator implements Validator{

	@Override
	public void validate(FacesContext context, UIComponent component, Object value) {
		if (!StringUtil.isNumber(value.toString()) && !value.toString().isEmpty()){
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Digite apenas números", null);
			throw new ValidatorException(msg);
		}
	
	
	
	
	
	
	}
}
