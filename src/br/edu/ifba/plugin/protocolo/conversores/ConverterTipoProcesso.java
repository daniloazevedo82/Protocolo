package br.edu.ifba.plugin.protocolo.conversores;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;

import br.edu.ifba.plugin.protocolo.bd.beans.TipoProcesso;
import br.edu.ifba.plugin.protocolo.bd.conexao.ConexaoBD;

@FacesConverter(forClass = TipoProcesso.class)
public class ConverterTipoProcesso implements Converter{

	@Override
	public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String value) {
		if(StringUtils.isNotBlank(value)){
			Integer id = Integer.valueOf(value);
			TipoProcesso tipoProcesso = ConexaoBD.getInstancia().getEntityManager().find(TipoProcesso.class, id);
			return tipoProcesso;
		}
		return null;
	}

	@Override
	public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object value) {
		if(value instanceof TipoProcesso){
			TipoProcesso entity = (TipoProcesso) value;
			if(entity != null && entity instanceof TipoProcesso && entity.getId() != null){
				uiComponent.getAttributes().put(entity.getId().toString(), entity);
				return entity.getId().toString();
			}
		}
		return "";
	}
	
	

}
