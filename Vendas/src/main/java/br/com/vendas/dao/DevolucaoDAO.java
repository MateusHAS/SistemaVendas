package br.com.vendas.dao;


import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import br.com.vendas.domain.Devolucao;
import br.com.vendas.domain.ItemDevolucao;
import br.com.vendas.domain.Produto;
import br.com.vendas.util.HibernateUtil;

public class DevolucaoDAO extends GenericDAO<Devolucao> {
	public void salvar(Devolucao devolucao, List<ItemDevolucao> itensDevolucao){
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		Transaction transacao = null;

		try {
			transacao = sessao.beginTransaction();
		
			sessao.save(devolucao);
			
			for(int posicao = 0; posicao < itensDevolucao.size(); posicao++){
				ItemDevolucao itemDevolucao = itensDevolucao.get(posicao);
				itemDevolucao.setDevolucao(devolucao);
				
				sessao.save(itemDevolucao);
				
				
				
				Produto produto = itemDevolucao.getProduto();
				
				int qtde = produto.getQuantidade() + itemDevolucao.getQuantidade();
				
				if(qtde >= 0){
				
				produto.setQuantidade(new Short((qtde) + ""));
				
				sessao.update(produto);
				
			}else{
				throw new RuntimeException("Quantidade insuficiente em estoque");
			}
				
			}
			
			transacao.commit();
		} catch (RuntimeException erro) {
			if (transacao != null) {
				transacao.rollback();
			}
			throw erro;
		} finally {
			sessao.close();
		}
	}
}

