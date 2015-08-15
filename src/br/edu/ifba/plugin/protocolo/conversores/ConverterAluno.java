package br.edu.ifba.plugin.protocolo.conversores;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;

import br.edu.ifba.plugin.protocolo.bd.beans.Aluno;
import br.edu.ifba.plugin.protocolo.bd.conexao.ConexaoBD;

@FacesConverter(forClass = Aluno.class)
public class ConverterAluno implements Converter {

	@Override
	public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String value) {
		if(StringUtils.isNotBlank(value)){
			Integer id = Integer.valueOf(value);
			Aluno aluno = ConexaoBD.getInstancia().getEntityManager().find(Aluno.class, id);
			return aluno;
		}
		return null;
	}

	@Override
	public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object value) {
		if(value instanceof Aluno){
			Aluno entity = (Aluno) value;
			if(entity != null && entity instanceof Aluno && entity.getId() != null){
				uiComponent.getAttributes().put(entity.getId().toString(), entity);
				return entity.getId().toString();
			}
		}
		return "";
	}

}
