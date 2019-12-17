package br.com.Vendas.Bean;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

import br.com.Vendas.domain.Funcionario;

@ManagedBean(name = "MBAutenticador")
public class AutenticadorBean {
  private Funcionario usuario = new Funcionario();

  public String doEfetuarLogin() {
    if("sakurai".equals(usuario.getEmail()) &&
       "12345".equals(usuario.getSenha())) {
      /* Se escrever o login e senha correto então vai para a tela principal do sistema. */
      return "template";
    }else {
    	/* Cria uma mensagem. */
        FacesMessage msg = new FacesMessage("Usuário ou senha inválido!");
        /* Obtém a instancia atual do FacesContext e adiciona a mensagem de erro nele. */
        FacesContext.getCurrentInstance().addMessage("erro", msg);
        return null;
    }
  }

  public Funcionario getUsuario() {
    return usuario;
  }

  public void setUsuario(Funcionario usuario) {
    this.usuario = usuario;
  }
}