package com.converter;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException; 
import javax.faces.convert.FacesConverter;

import com.facade.LaudoFacade;
import com.model.Laudo;

/**
 * Classe responsável pela conversão as laudos.
 * @author 12546446
 *
 */
@FacesConverter(forClass = com.model.Laudo.class, value="laudoConverter")
public class LaudoConverter implements Converter{

	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String arg2) {
		LaudoFacade laudoFacade = new LaudoFacade();
		int laudoId;

		try {
			laudoId = Integer.parseInt(arg2);
		} catch (NumberFormatException exception) {
			throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro na conversão", "Erro na conversão!"));
		}

		return laudoFacade.findLaudo(laudoId);
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {

		if (arg2 == null) {
			return "";
		}
		Laudo laudo = (Laudo) arg2;
		return String.valueOf(laudo.getId());
	}
}
