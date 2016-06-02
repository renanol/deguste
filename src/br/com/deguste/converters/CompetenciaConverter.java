package br.com.deguste.converters;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter(value = "competenciaConverter")
public class CompetenciaConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext context, UIComponent component, String value) {
                if(value == null || value.equals("") || value.equals("__/____"))
                        return null;
                else{
                        String competencia = value.replace("/", "");
                        return competencia;
                }
        }

        @Override
        public String getAsString(FacesContext context, UIComponent component, Object value) {
                if(value != null){
                        StringBuilder competencia = new StringBuilder();
                        String competenciaStr = value.toString();
                        if(competenciaStr.length() == 6)
                                competencia.append(competenciaStr.substring(0, 2)).append("/").append(competenciaStr.substring(2, 6));
                        return competencia.toString();
                }
                return null;
        }
}
