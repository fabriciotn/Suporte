package com.converter;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

import com.facade.FornecedorFacade;
import com.model.Fornecedor;

/**
 * Classe responsável pela conversão as fornecedors.
 * @author 12546446
 *
 */
@FacesConverter(forClass = com.model.Fornecedor.class, value="fornecedorConverter")
public class FornecedorConverter implements Converter{

	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String arg2) {
		FornecedorFacade fornecedorFacade = new FornecedorFacade();
		int fornecedorId;

		try {
			fornecedorId = Integer.parseInt(arg2);
		} catch (NumberFormatException exception) {
			throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro na conversão", "Erro na conversão!"));
		}

		return fornecedorFacade.findFornecedor(fornecedorId);
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {

		if (arg2 == null) {
			return "";
		}
		Fornecedor fornecedor = (Fornecedor) arg2;
		return String.valueOf(fornecedor.getId());
	}
}
