package br.edu.ifba.plugin.protocolo.visao;

import java.util.List;

import br.edu.ifba.plugin.protocolo.bd.beans.Aluno;
import br.edu.ifba.plugin.protocolo.bd.beans.Curso;
import br.edu.ifba.plugin.protocolo.bd.enumeration.CategoriaEnum;
import br.edu.ifba.plugin.protocolo.bd.enumeration.SexoEnum;

public interface ICadastroAluno {

	public Aluno getAluno();
	
	public void setListaAluno(List<Aluno> listaAluno);

	public void setListaCurso(List<Curso> listaCurso);
	
	public CategoriaEnum getCategoria();
	
	public SexoEnum getSexo();
	
}
