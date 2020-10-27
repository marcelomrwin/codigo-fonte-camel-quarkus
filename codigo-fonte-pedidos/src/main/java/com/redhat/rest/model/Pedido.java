package com.redhat.rest.model;

import java.io.Serializable;
import java.util.List;

public class Pedido implements Serializable {

	private static final long serialVersionUID = -4658421101931015124L;

	private Integer numPedido;
	private String codigoCliente;
	private String nomeCliente;
	private String emailCliente;
	private String enderecoCliente;
	private List<ItemPedido> produtos;

	public Integer getNumPedido() {
		return numPedido;
	}

	public void setNumPedido(Integer numPedido) {
		this.numPedido = numPedido;
	}

	public String getCodigoCliente() {
		return codigoCliente;
	}

	public void setCodigoCliente(String codigoCliente) {
		this.codigoCliente = codigoCliente;
	}

	public String getNomeCliente() {
		return nomeCliente;
	}

	public void setNomeCliente(String nomeCliente) {
		this.nomeCliente = nomeCliente;
	}

	public String getEmailCliente() {
		return emailCliente;
	}

	public void setEmailCliente(String emailCliente) {
		this.emailCliente = emailCliente;
	}

	public String getEnderecoCliente() {
		return enderecoCliente;
	}

	public void setEnderecoCliente(String enderecoCliente) {
		this.enderecoCliente = enderecoCliente;
	}

	public List<ItemPedido> getProdutos() {
		return produtos;
	}

	public void setProdutos(List<ItemPedido> produtos) {
		this.produtos = produtos;
	}

}
