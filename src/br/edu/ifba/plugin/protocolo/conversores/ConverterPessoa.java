package br.edu.ifba.plugin.protocolo.conversores;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;

import br.edu.ifba.plugin.protocolo.bd.beans.Pessoa;
import br.edu.ifba.plugin.protocolo.bd.conexao.ConexaoBD;

@FacesConverter(forClass = Pessoa.class)
public class ConverterPessoa implements Converter {

	@Override
	public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String value) {
		if(StringUtils.isNotBlank(value)){
			Integer id = Integer.valueOf(value);
			Pessoa pessoa = ConexaoBD.getInstancia().getEntityManager().find(Pessoa.class, id);
			return pessoa;
		}
		return null;
	}

	@Override
	public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object value) {
		if(value instanceof Pessoa){
			Pessoa entity = (Pessoa) value;
			if(entity != null && entity instanceof Pessoa && entity.getId() != null){
				uiComponent.getAttributes().put(entity.getId().toString(), entity);
				return entity.getId().toString();
			}
		}
		return "";
	}

}
