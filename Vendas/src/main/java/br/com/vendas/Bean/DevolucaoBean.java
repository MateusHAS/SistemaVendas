package br.com.vendas.Bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import org.omnifaces.util.Messages;

import br.com.vendas.dao.ClienteDAO;
import br.com.vendas.dao.DevolucaoDAO;
import br.com.vendas.dao.FuncionarioDAO;
import br.com.vendas.dao.ProdutoDAO;
import br.com.vendas.model.Cliente;
import br.com.vendas.model.Devolucao;
import br.com.vendas.model.Funcionario;
import br.com.vendas.model.ItemDevolucao;
import br.com.vendas.model.Produto;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class DevolucaoBean implements Serializable, StrategyBean{
	
	private Devolucao devolucao;

	private List<Produto> produtos;
	private List<ItemDevolucao> itensDevolucao;
	private List<Cliente> clientes;
	private List<Funcionario> funcionarios;

	public Devolucao getDevolucao() {
		return devolucao;
	}

	public void setDevolucao(Devolucao devolucao) {
		this.devolucao = devolucao;
	}

	public List<ItemDevolucao> getItensDevolucao() {
		return itensDevolucao;
	}

	public void setItensDevolucao(List<ItemDevolucao> itensDevolucao) {
		this.itensDevolucao = itensDevolucao;
	}

	public List<Produto> getProdutos() {
		return produtos;
	}

	public void setProdutos(List<Produto> produtos) {
		this.produtos = produtos;
	}

	public List<Cliente> getClientes() {
		return clientes;
	}

	public void setClientes(List<Cliente> clientes) {
		this.clientes = clientes;
	}

	public List<Funcionario> getFuncionarios() {
		return funcionarios;
	}

	public void setFuncionarios(List<Funcionario> funcionarios) {
		this.funcionarios = funcionarios;
	}

	@PostConstruct
	public void novo() {
		try {
			devolucao = new Devolucao();
			devolucao.setPrecoTotal(new BigDecimal("0.00"));

			ProdutoDAO produtoDAO = new ProdutoDAO();
			produtos = produtoDAO.listar("descricao");

			itensDevolucao = new ArrayList<>();
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar carregar a tela de devolução");
			erro.printStackTrace();
		}
	}

	public void adicionar(ActionEvent evento) {
		Produto produto = (Produto) evento.getComponent().getAttributes().get("produtoSelecionado");

		int achou = -1;
		for (int posicao = 0; posicao < itensDevolucao.size(); posicao++) {
			if (itensDevolucao.get(posicao).getProduto().equals(produto)) {
				achou = posicao;
			}
		}

		if (achou < 0) {
			ItemDevolucao itemDevolucao = new ItemDevolucao();
			itemDevolucao.setPrecoParcial(produto.getPreco());
			itemDevolucao.setProduto(produto);
			itemDevolucao.setQuantidade(new Short("1"));

			itensDevolucao.add(itemDevolucao);
		} else {
			ItemDevolucao itemDevolucao = itensDevolucao.get(achou);
			itemDevolucao.setQuantidade(new Short(itemDevolucao.getQuantidade() + 1 + ""));
			itemDevolucao.setPrecoParcial(produto.getPreco().multiply(new BigDecimal(itemDevolucao.getQuantidade())));
		}

		calcular();
	}

	public void remover(ActionEvent evento) {
		ItemDevolucao itemDevolucao = (ItemDevolucao) evento.getComponent().getAttributes().get("itemSelecionado");

		int achou = -1;
		for (int posicao = 0; posicao < itensDevolucao.size(); posicao++) {
			if (itensDevolucao.get(posicao).getProduto().equals(itemDevolucao.getProduto())) {
				achou = posicao;
			}
		}

		if (achou > -1) {
			itensDevolucao.remove(achou);
		}

		calcular();
	}

	public void calcular() {
		devolucao.setPrecoTotal(new BigDecimal("0.00"));

		for (int posicao = 0; posicao < itensDevolucao.size(); posicao++) {
			ItemDevolucao itemDevolucao = itensDevolucao.get(posicao);
			devolucao.setPrecoTotal(devolucao.getPrecoTotal().add(itemDevolucao.getPrecoParcial()));
		}
	}

	public void finalizar() {
		try {
			devolucao.setHorario(new Date());
			devolucao.setCliente(null);
			devolucao.setFuncionario(null);
			
			FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
			funcionarios = funcionarioDAO.listarOrdenado();

			ClienteDAO clienteDAO = new ClienteDAO();
			clientes = clienteDAO.listarOrdenado();
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar finalizar a devolução");
			erro.printStackTrace();
		}
	}

	public void salvar() {
		try {
			if(devolucao.getPrecoTotal().signum() == 0){
				Messages.addGlobalError("Informe pelo menos um item para a devolução");
				return;
			}
			
			DevolucaoDAO devolucaoDAO = new DevolucaoDAO();
			devolucaoDAO.salvar(devolucao, itensDevolucao);
			
			devolucao = new Devolucao();
			devolucao.setPrecoTotal(new BigDecimal("0.00"));

			ProdutoDAO produtoDAO = new ProdutoDAO();
			produtos = produtoDAO.listar("descricao");

			itensDevolucao = new ArrayList<>();
			
			Messages.addGlobalInfo("Devolução realizada com sucesso");
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar salvar a devolução");
			erro.printStackTrace();
		}
	}


}
