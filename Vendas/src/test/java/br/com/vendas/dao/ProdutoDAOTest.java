package br.com.vendas.dao;

import java.math.BigDecimal;

import org.junit.Test;

import br.com.vendas.dao.FornecedorDAO;
import br.com.vendas.dao.ProdutoDAO;
import br.com.vendas.domain.Fornecedor;
import br.com.vendas.domain.Produto;

public class ProdutoDAOTest {
	@Test
	
	public void salvar(){
		FornecedorDAO fornecedorDAO = new FornecedorDAO();
		Fornecedor fornecedor = fornecedorDAO.buscar(new Long("2"));
		
		Produto produto = new Produto();
		produto.setDescricao("Curso de Java");
		produto.setFornecedor(fornecedor);
		produto.setPreco(new BigDecimal("13.70"));
		produto.setQuantidade(new Short("7"));
		
		ProdutoDAO produtoDAO = new ProdutoDAO();
		produtoDAO.salvar(produto);
		
		System.out.println("Produto salvo com sucesso");
	}
}