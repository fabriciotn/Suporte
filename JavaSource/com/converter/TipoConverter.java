package com.converter;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException; 
import javax.faces.convert.FacesConverter;

import com.facade.TipoFacade;
import com.model.Tipo;

/**
 * Classe responsável pela conversão as tipos.
 * @author 12546446
 *
 */
@FacesConverter(forClass = com.model.Tipo.class, value="tipoConverter")
public class TipoConverter implements Converter{

	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String arg2) {
		TipoFacade tipoFacade = new TipoFacade();
		int tipoId;

		try {
			tipoId = Integer.parseInt(arg2);
		} catch (NumberFormatException exception) {
			throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro na conversão", "Erro na conversão!"));
		}

		return tipoFacade.findTipo(tipoId);
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {

		if (arg2 == null) {
			return "";
		}
		Tipo tipo = (Tipo) arg2;
		return String.valueOf(tipo.getId());
	}
}
