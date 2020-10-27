package com.redhat.camel.enrich;

import org.apache.camel.AggregationStrategy;
import org.apache.camel.Exchange;
import org.apache.camel.RuntimeCamelException;

import com.redhat.camel.model.PedidoNovo;
import com.redhat.camel.model.Produto;
import com.redhat.camel.model.ProdutoPedido;

public class ProdutoNovoItemAgregationStrategy implements AggregationStrategy {

	public static final String PEDIDO_NOVO = "pedidoNovo";

	@Override
	public Exchange aggregate(Exchange originalExchange, Exchange newExchange) {
		PedidoNovo pedido = originalExchange.getIn().getHeader(PEDIDO_NOVO, PedidoNovo.class);
		try {
			Produto produto = newExchange.getIn().getBody(Produto.class);

			pedido.getProdutos().add(new ProdutoPedido(produto.getCodigo(), produto.getDescricao(), produto.getValor(),
					originalExchange.getIn().getHeader("quantidade", Integer.class)));

		} catch (Exception e) {
			throw new RuntimeCamelException(e);
		}

		return originalExchange;
	}

}
