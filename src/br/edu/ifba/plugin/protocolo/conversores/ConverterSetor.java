package br.edu.ifba.plugin.protocolo.conversores;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;

import br.edu.ifba.plugin.protocolo.bd.beans.Setor;
import br.edu.ifba.plugin.protocolo.bd.conexao.ConexaoBD;

@FacesConverter(forClass = Setor.class)
public class ConverterSetor implements Converter {

	@Override
	public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String value) {
		if(StringUtils.isNotBlank(value)){
			Integer id = Integer.valueOf(value);
			Setor setor = ConexaoBD.getInstancia().getEntityManager().find(Setor.class, id);
			return setor;
		}
		return null;
	}

	@Override
	public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object value) {
		if(value instanceof Setor){
			Setor entity = (Setor) value;
			if(entity != null && entity instanceof Setor && entity.getId() != null){
				uiComponent.getAttributes().put(entity.getId().toString(), entity);
				return entity.getId().toString();
			}
		}
		return "";
	}

}
