package br.com.vendas.service;


import javax.ws.rs.GET;
import javax.ws.rs.Path;

// http://localhost:8080/Financeiro/rest/financeiro

@Path("financeiro")
public class FinanceiroService {
	@GET
	public String exibir(){
		return "Teste";
	}
}