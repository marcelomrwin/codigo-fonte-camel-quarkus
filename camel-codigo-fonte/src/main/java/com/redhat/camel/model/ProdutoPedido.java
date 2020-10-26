package com.redhat.camel.model;

import java.io.Serializable;

public class ProdutoPedido implements Serializable {

	public ProdutoPedido() {
		super();
	}

	public ProdutoPedido(String codigo, String descricao, Double valorUnitario, Integer quantidade) {
		this();
		this.codigo = codigo;
		this.descricao = descricao;
		this.valorUnitario = valorUnitario;
		this.quantidade = quantidade;
		this.valorTotal = quantidade * valorUnitario;
	}

	private static final long serialVersionUID = 6117916878653477968L;
	protected String codigo;
	protected String descricao;
	protected Double valorUnitario;
	protected Integer quantidade;
	protected Double valorTotal;

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Double getValorUnitario() {
		return valorUnitario;
	}

	public void setValorUnitario(Double valorUnitario) {
		this.valorUnitario = valorUnitario;
	}

	public Integer getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}

	public Double getValorTotal() {
		return valorTotal;
	}

	public void setValorTotal(Double valorTotal) {
		this.valorTotal = valorTotal;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ProdutoPedido [");
		if (codigo != null)
			builder.append("codigo=").append(codigo).append(", ");
		if (descricao != null)
			builder.append("descricao=").append(descricao).append(", ");
		if (valorUnitario != null)
			builder.append("valorUnitario=").append(valorUnitario).append(", ");
		if (quantidade != null)
			builder.append("quantidade=").append(quantidade).append(", ");
		if (valorTotal != null)
			builder.append("valorTotal=").append(valorTotal);
		builder.append("]");
		return builder.toString();
	}
}
