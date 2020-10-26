package com.redhat.camel.model;

public class Cliente {
	public Cliente() {
		super();
	}

	public Cliente(String cpf, String nome, String email, String endereco) {
		super();
		this.cpf = cpf;
		this.nome = nome;
		this.email = email;
		this.endereco = endereco;
	}

	protected String cpf;
	protected String nome;
	protected String email;
	protected String endereco;

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Cliente [");
		if (cpf != null)
			builder.append("cpf=").append(cpf).append(", ");
		if (nome != null)
			builder.append("nome=").append(nome).append(", ");
		if (email != null)
			builder.append("email=").append(email).append(", ");
		if (endereco != null)
			builder.append("endereco=").append(endereco);
		builder.append("]");
		return builder.toString();
	}
}
