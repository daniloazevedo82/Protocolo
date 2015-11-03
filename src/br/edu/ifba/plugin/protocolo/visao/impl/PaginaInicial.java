package br.edu.ifba.plugin.protocolo.visao.impl;

import java.io.IOException;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.primefaces.context.RequestContext;

import br.edu.ifba.plugin.protocolo.bd.DAO.EtapasProcessoDAO;
import br.edu.ifba.plugin.protocolo.bd.DAO.UsuarioDAO;
import br.edu.ifba.plugin.protocolo.bd.beans.EtapasProcesso;
import br.edu.ifba.plugin.protocolo.bd.beans.Setor;
import br.edu.ifba.plugin.protocolo.bd.beans.Usuario;
import br.edu.ifba.plugin.protocolo.visao.IPaginaInicial;

@ManagedBean(name="paginaInicial")
@ViewScoped
public class PaginaInicial implements IPaginaInicial{

	Integer idusuariologado = 1;
	int idSetorSession;
	String nomeSetorSession;
	public String getNomeSetorSession() {return nomeSetorSession;}
	
	private EtapasProcessoDAO processoDAO = new EtapasProcessoDAO();
	private UsuarioDAO usuarioDAO = new UsuarioDAO();
	public void setProcessoDAO(EtapasProcessoDAO processoDAO) {this.processoDAO = processoDAO;}
	public void setUsuarioDAO(UsuarioDAO usuarioDAO) {this.usuarioDAO = usuarioDAO;}
	
	private Setor setor = new Setor();
	private List<EtapasProcesso> listaEtapasProcessoEmEspera;
	private List<EtapasProcesso> listaEtapasProcessoDaCoordenacao;
	private String login;
	private String senha;
	
	public Setor getSetor() {
		return setor;
	}
	public List<EtapasProcesso> getListaEtapasProcessoEmEspera() {
		return listaEtapasProcessoEmEspera;
	}
	public List<EtapasProcesso> getListaEtapasProcessoDaCoordenacao() {
		return listaEtapasProcessoDaCoordenacao;
	}
	public String getLogin() {
		return login;
	}
	public String getSenha() {
		return senha;
	}
	
	public void setSetor(Setor setor) {
		this.setor = setor;
	}
	@Override
	public void setListaEtapasProcessoEmEspera(List<EtapasProcesso> listaEtapasProcessoEmEspera) {
		this.listaEtapasProcessoEmEspera = listaEtapasProcessoEmEspera;
	}
	@Override
	public void setListaEtapasProcessoDaCoordenacao(List<EtapasProcesso> listaEtapasProcessoDaCoordenacao) {
		this.listaEtapasProcessoDaCoordenacao = listaEtapasProcessoDaCoordenacao;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}
	
	public void login(){
		Usuario usuario = new Usuario();
		if(StringUtils.isNotBlank(login) && StringUtils.isNotBlank(senha)){
			usuario = usuarioDAO.getValidaUsuario(login, senha);
			if(usuario != null){
				setor = usuario.getSetor();
				redirecionarTelaInicial();
			} else {
				redirecionaErro();
			}
		} else {
			redirecionaErro();
		}
	}
	
	public void listar(){
		HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
		HttpServletRequest request = (HttpServletRequest) req;
		HttpSession session = (HttpSession) request.getSession();
		idSetorSession = (int) session.getAttribute("ID_SETOR");
		nomeSetorSession = (String) session.getAttribute("NOME_SETOR");
		listarEmEspera();
		listarDaCoordenacao();
	}
	
	public void listarEmEspera(){
		listaEtapasProcessoEmEspera = processoDAO.getListagemEtapasProcesso(idusuariologado, idSetorSession);
	}
	
	public void listarDaCoordenacao(){
		
	}
	
	public void redirecionarTelaInicial(){  
		try {
			FacesContext context = FacesContext.getCurrentInstance();
			HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
			session.setAttribute("ID_SETOR", setor.getId());
			session.setAttribute("NOME_SETOR", setor.getNome());
			context.getExternalContext().redirect("index.ifba");
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
	
	public void redirecionaErro(){
		RequestContext.getCurrentInstance().execute("alert('Login ou senha inválidos.')");
	}
	
}
