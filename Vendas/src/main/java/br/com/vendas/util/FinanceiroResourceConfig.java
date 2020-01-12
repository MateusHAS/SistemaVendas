package br.com.vendas.util;



import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.server.ResourceConfig;

//http://localhost:8080/Vendas/rest
@ApplicationPath("rest")
public class FinanceiroResourceConfig extends ResourceConfig {
	public FinanceiroResourceConfig(){
		packages("br.com.vendas.service");
	}
}