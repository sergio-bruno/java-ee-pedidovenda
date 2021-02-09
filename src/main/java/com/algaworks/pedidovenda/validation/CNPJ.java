package com.algaworks.pedidovenda.validation;

import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import org.primefaces.validate.ClientValidator;

@FacesValidator(CNPJ.VALIDATOR_ID)
public class CNPJ implements Validator, ClientValidator {

	public static final String VALIDATOR_ID = "com.hoplon.CNPJ";

	private String cnpj;
	private static final int[] pesoCNPJ = {6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};
	
	@Override
	public void validate(FacesContext context, UIComponent component,
			Object value) throws ValidatorException {
		if (value == null) {
			return;
		}

		cnpj = ((String) value).replaceAll("\\D", "");

		String[] cnpjs = new String[] { "00000000000000", "11111111111111", "22222222222222", 
				                        "44444444444444", "55555555555555", "66666666666666",
				                        "77777777777777", "88888888888888", "99999999999999" 
 									  };
		for (String xcnpj : cnpjs) {
			if (cnpj.equals(xcnpj.trim())) {
				throw new ValidatorException(new FacesMessage(
					FacesMessage.SEVERITY_ERROR, value	+ " não é um CNPJ válido !", ""));				
			}
		}		
		
		if ( cnpj.length() != 14 ) { 
			throw new ValidatorException(new FacesMessage(
					FacesMessage.SEVERITY_ERROR, value	+ " não é um CNPJ válido, o comprimento deve conter 14 digitos !", ""));
		}	

		Integer digito1 = calcularDigito(cnpj.substring(0,12), pesoCNPJ);
	    Integer digito2 = calcularDigito(cnpj.substring(0,12) + digito1, pesoCNPJ);
	    
	    if ( ! cnpj.equals(cnpj.substring(0,12) + digito1.toString() + digito2.toString()) ) {
			throw new ValidatorException(new FacesMessage(
					FacesMessage.SEVERITY_ERROR, value	+ " não é um CNPJ válido, digito verificador não confere !", "..."));
	    }
		
	}

	@Override
	public Map<String, Object> getMetadata() {
		return null;
	}

	@Override
	public String getValidatorId() {
		return VALIDATOR_ID;
	}

	private static int calcularDigito(String str, int[] peso) {
	      int soma = 0;
	      for (int indice=str.length()-1, digito; indice >= 0; indice-- ) {
	         digito = Integer.parseInt(str.substring(indice,indice+1));
	         soma += digito*peso[peso.length-str.length()+indice];
	      }
	      soma = 11 - soma % 11;
	      return soma > 9 ? 0 : soma;
	}	
}