
package com.redhat.produtos.server;

import java.util.HashMap;
import java.util.Map;

import javax.jws.WebService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebService(endpointInterface = "com.redhat.produtos.server.ProdutoWS", serviceName = "/produtos", portName = "ProdutosPort")
public class ProdutoWSImpl implements ProdutoWS {

	private Map<String, Produto> produtos;
	protected Logger logger = LoggerFactory.getLogger(getClass());

	public ProdutoWSImpl() {
		LoggerFactory.getLogger(getClass()).info("Starting endpoint");
		produtos = new HashMap<String, Produto>();

		produtos.put("hw001", new Produto("hw001", "Impressora 3D", 1500.0));
		produtos.put("hw002", new Produto("hw002", "Notebook i7 8gb", 2500.0));
		produtos.put("hw003", new Produto("hw003", "MacbookPro i7 16gb", 3500.0));
		produtos.put("hw004", new Produto("hw004", "PC Gamer i9 32gb", 3000.0));
		produtos.put("hw005", new Produto("hw005", "Placa de video 16gb", 2000.0));
		produtos.put("hw006", new Produto("hw006", "Monitor Ultra wide", 2700.0));
		produtos.put("hw007", new Produto("hw007", "Hub USB 7 ports", 300.0));
		produtos.put("hw008", new Produto("hw008", "Cadeira Gamer", 1900.0));
	}

	@Override
	public Produto consultarProduto(String codigo) {
		logger.info("SOAP-WS {}",codigo);		
		return produtos.get(codigo);
	}

}
