package com.converter;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException; 
import javax.faces.convert.FacesConverter;

import com.facade.LocalFacade;
import com.model.Local;

/**
 * Classe responsável pela conversão as locais.
 * @author 12546446
 *
 */
@FacesConverter(forClass = com.model.Local.class, value="localConverter")
public class LocalConverter implements Converter{

	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String arg2) {
		LocalFacade localFacade = new LocalFacade();
		int localId;

		try {
			localId = Integer.parseInt(arg2);
		} catch (NumberFormatException exception) {
			throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro na conversão", "Erro na conversão!"));
		}

		return localFacade.findLocal(localId);
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {

		if (arg2 == null) {
			return "";
		}
		Local local = (Local) arg2;
		return String.valueOf(local.getId());
	}
}
