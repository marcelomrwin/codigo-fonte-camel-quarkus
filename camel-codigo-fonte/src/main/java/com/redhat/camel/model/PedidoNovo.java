package com.redhat.camel.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PedidoNovo implements Serializable {

	public PedidoNovo() {
		super();		
	}

	public PedidoNovo(Integer numPedido, String codigoCliente, String nomeCliente,String emailCliente, String enderecoCliente,
			Double valorTotal) {
		super();
		this.numPedido = numPedido;
		this.codigoCliente = codigoCliente;
		this.nomeCliente = nomeCliente;
		this.emailCliente = emailCliente;
		this.enderecoCliente = enderecoCliente;
		this.valorTotal = valorTotal;
	}

	private static final long serialVersionUID = 724894274450528069L;

	protected Integer numPedido;
	protected String codigoCliente;
	protected String nomeCliente;
	protected String emailCliente;
	protected String enderecoCliente;
	protected Double valorTotal;

	protected List<ProdutoPedido> produtos = new ArrayList<ProdutoPedido>();

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

	public String getEnderecoCliente() {
		return enderecoCliente;
	}

	public void setEnderecoCliente(String enderecoCliente) {
		this.enderecoCliente = enderecoCliente;
	}

	public Double getValorTotal() {
		return valorTotal;
	}

	public void setValorTotal(Double valorTotal) {
		this.valorTotal = valorTotal;
	}

	public List<ProdutoPedido> getProdutos() {
		return produtos;
	}

	public void setProdutos(List<ProdutoPedido> produtos) {
		this.produtos = produtos;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("PedidoNovo [");
		if (numPedido != null)
			builder.append("numPedido=").append(numPedido).append(", ");
		if (codigoCliente != null)
			builder.append("codigoCliente=").append(codigoCliente).append(", ");
		if (nomeCliente != null)
			builder.append("nomeCliente=").append(nomeCliente).append(", ");
		if (emailCliente != null)
			builder.append("emailCliente=").append(emailCliente).append(", ");
		if (enderecoCliente != null)
			builder.append("enderecoCliente=").append(enderecoCliente).append(", ");
		if (valorTotal != null)
			builder.append("valorTotal=").append(valorTotal).append(", ");
		if (produtos != null)
			builder.append("produtos=").append(produtos);
		builder.append("]");
		return builder.toString();
	}

	public String getEmailCliente() {
		return emailCliente;
	}

	public void setEmailCliente(String emailCliente) {
		this.emailCliente = emailCliente;
	}
}
