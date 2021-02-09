package com.algaworks.pedidovenda.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import com.algaworks.pedidovenda.model.Endereco;
import com.algaworks.pedidovenda.repository.Enderecos;
import com.algaworks.pedidovenda.utils.cdi.CDIServiceLocator;

@FacesConverter(forClass = Endereco.class)
public class EnderecoConverter implements Converter {

	//@Inject
	private Enderecos enderecos;
	
	public EnderecoConverter() {
		enderecos = CDIServiceLocator.getBean(Enderecos.class);
	}
	
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		Endereco retorno = null;
		
		if (value != null) {
			Long id = new Long(value);
			retorno = enderecos.porId(id);
		}
		
		return retorno;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value != null) {
			return ((Endereco) value).getId().toString();
		}
		
		return "";
	}

}