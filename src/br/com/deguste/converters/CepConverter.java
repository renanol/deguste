package br.com.deguste.converters;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter(value = "cepConverter")
public class CepConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext arg0, UIComponent arg1, String arg2) {
        if(arg2==null || arg2.equals("") || arg2.equals("__.___-___"))
                return null;
    	else {
                return arg2.replace("-", "").replace(".", "");
        }
    }

    @Override
    public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
        if(arg2 != null){
            StringBuilder cep = new StringBuilder();
            String cepsm = arg2.toString();
            if(cepsm.length() == 8)
                    cep.append(cepsm.substring(0, 2)).append(".").append(cepsm.substring(2, 5)).append("-").append(cepsm.substring(5, 8));
            return cep.toString();
        }
        return null;
    }
}
