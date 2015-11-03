package br.edu.ifba.plugin.protocolo.conversores;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;

import br.edu.ifba.plugin.protocolo.bd.beans.TipoDocumentoAcademico;
import br.edu.ifba.plugin.protocolo.bd.conexao.ConexaoBD;

@FacesConverter(forClass = TipoDocumentoAcademico.class)
public class ConverterTipoDocumentoAcademico implements Converter{
	
	@Override
	public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String value) {
		if(StringUtils.isNotBlank(value)){
			Integer id = Integer.valueOf(value);
			TipoDocumentoAcademico tipoDocumentoAcademico = 
					ConexaoBD.getInstancia().getEntityManager().find(TipoDocumentoAcademico.class, id);
			return tipoDocumentoAcademico;
		}
		return null;
	}

	@Override
	public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object value) {
		if(value instanceof TipoDocumentoAcademico){
			TipoDocumentoAcademico entity = (TipoDocumentoAcademico) value;
			if(entity != null && entity instanceof TipoDocumentoAcademico && entity.getId() != null){
				uiComponent.getAttributes().put(entity.getId().toString(), entity);
				return entity.getId().toString();
			}
		}
		return "";
	}
	
}
