package br.com.danilosales.crud.model.converters;

import br.com.danilosales.crud.model.TipoTelefone;
import br.com.danilosales.crud.model.converters.TipoTelefoneConverter;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class TipoTelefoneConverter extends Object implements AttributeConverter<TipoTelefone, String> {
	
	public String convertToDatabaseColumn(TipoTelefone tipoTelefone) {
		if (tipoTelefone == null) {
			return null;
		}
		return tipoTelefone.getCodigo();
	}

	public TipoTelefone convertToEntityAttribute(String codigo) {
		if (codigo == null) {
			return null;
		}
		return TipoTelefone.getByCodigo(codigo);
	}
	
}
