package br.edu.ifba.plugin.protocolo.conversores;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;

import br.edu.ifba.plugin.protocolo.bd.beans.Usuario;
import br.edu.ifba.plugin.protocolo.bd.conexao.ConexaoBD;

@FacesConverter(forClass = Usuario.class)
public class ConverterUsuario implements Converter {

	@Override
	public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String value) {
		if(StringUtils.isNotBlank(value)){
			Integer id = Integer.valueOf(value);
			Usuario usuario = ConexaoBD.getInstancia().getEntityManager().find(Usuario.class, id);
			return usuario;
		}
		return null;
	}

	@Override
	public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object value) {
		if(value instanceof Usuario){
			Usuario entity = (Usuario) value;
			if(entity != null && entity instanceof Usuario && entity.getId() != null){
				uiComponent.getAttributes().put(entity.getId().toString(), entity);
				return entity.getId().toString();
			}
		}
		return "";
	}

}
