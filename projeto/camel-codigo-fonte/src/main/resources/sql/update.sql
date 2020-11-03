update clientes
set
    nome = :#nome,
    email = :#email,
    endereco = :#endereco
where
    cpf = :#cpf
