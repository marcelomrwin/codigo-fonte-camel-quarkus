package com.redhat.produtos.server;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

@WebService
@SOAPBinding(style = SOAPBinding.Style.RPC)
public interface ProdutoWS {
	
	@WebMethod
    Produto consultarProduto(@WebParam(name = "codigo") String codigo);
}

