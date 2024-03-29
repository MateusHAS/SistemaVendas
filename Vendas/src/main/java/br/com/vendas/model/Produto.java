package br.com.vendas.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;


@Entity
public class Produto extends GenericDomain {
	@Column(length = 80, nullable = false)
	private String descricao;
	
	@Transient
	private String caminho;
	
		
	
	@Column(nullable = false)
	private Short quantidade;
	
	@Column(nullable = false, precision = 6, scale = 2)
	private BigDecimal preco;
	
	@ManyToOne
	@JoinColumn(nullable = false)
	private Fornecedor fornecedor;
	
	
	public String getCaminho() {
		return caminho;
	}
	
	
	public void setCaminho(String caminho) {
		this.caminho = caminho;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Short getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Short quantidade) {
		this.quantidade = quantidade;
	}

	public BigDecimal getPreco() {
		return preco;
	}

	public void setPreco(BigDecimal preco) {
		this.preco = preco;
	}

	public Fornecedor getFornecedor() {
		return fornecedor;
	}

	public void setFornecedor(Fornecedor fornecedor) {
		this.fornecedor = fornecedor;
	}
}
