PrimeFaces.locales['pt_BR'] = {
	messages : {
		'org.hibernate.validator.constraints.NotBlank.message' : '{0} não pode estar em branco'
	}
};

PrimeFaces.converter['com.hoplon.Categoria'] = {
	
	convert : function(element, value) {
		if (value === null || value === '') {
			return null;
		}
		
		return parseInt(value);
	}
		
};