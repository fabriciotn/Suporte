package com.util;

/**
 * Classe para retirar as máscaras dos campos de telefones e CEP's
 * @author 12546446
 *
 */
public class RetiraMascaras {
	
	public static String retirar(String campo){
		String semMascara;
		semMascara = campo.replace("(", "");
		semMascara = semMascara.replace(")", "");
		semMascara = semMascara.replace("-", "");
		semMascara = semMascara.replace("/", "");
		semMascara = semMascara.replace(".", "");
		
		return semMascara;
	}
}
