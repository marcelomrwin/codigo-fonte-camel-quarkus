package com.redhat.rest;

import java.util.SortedMap;
import java.util.TreeMap;

import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.redhat.rest.model.Pedido;

@Path("/pedidos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(ref = "Pedidos")
public class PedidosResource {
	protected Logger logger = LoggerFactory.getLogger(getClass());
	private SortedMap<Integer, Pedido> pedidos = new TreeMap<Integer, Pedido>();

	@GET
	public Response getAll() {
		return Response.ok(pedidos.values()).build();
	}

	@GET
	@Path("/{numPedido}")
	public Response getOne(@PathParam("numPedido") @NotNull Integer numPedido) {
		if (pedidos.containsKey(numPedido))
			return Response.ok(pedidos.get(numPedido)).build();

		return Response.status(Status.NOT_FOUND).build();
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public Response insert(Pedido pedido) {
		logger.info("Salvando pedido {}",pedido.getNumPedido());
		pedidos.put(pedido.getNumPedido(), pedido);
		return Response.status(Status.CREATED).build();
	}
}