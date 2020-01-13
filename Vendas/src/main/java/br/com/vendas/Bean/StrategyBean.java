package br.com.vendas.Bean;

import javax.faces.event.ActionEvent;

public interface StrategyBean {
	
	void novo();
	void adicionar(ActionEvent evento);
	void remover(ActionEvent evento);
	void calcular();
	void salvar();

}
