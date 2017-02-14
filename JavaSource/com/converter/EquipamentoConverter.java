package com.converter;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException; 
import javax.faces.convert.FacesConverter;

import com.facade.EquipamentoFacade;
import com.model.Equipamento;

/**
 * Classe responsável pela conversão as equipamentos.
 * @author 12546446
 *
 */
@FacesConverter(forClass = com.model.Equipamento.class, value="equipamentoConverter")
public class EquipamentoConverter implements Converter{

	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String arg2) {
		EquipamentoFacade equipamentoFacade = new EquipamentoFacade();
		int equipamentoId;

		try {
			equipamentoId = Integer.parseInt(arg2);
		} catch (NumberFormatException exception) {
			throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro na conversão", "Erro na conversão!"));
		}

		return equipamentoFacade.findEquipamento(equipamentoId);
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {

		if (arg2 == null) {
			return "";
		}
		Equipamento equipamento = (Equipamento) arg2;
		return String.valueOf(equipamento.getId());
	}
}
