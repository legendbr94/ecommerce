package com.pedro.ecommerce.mapeamentobasico;

import com.pedro.ecommerce.EntityManagerTest;
import com.pedro.ecommerce.model.Cliente;
import com.pedro.ecommerce.model.EnderecoEntregaPedido;
import com.pedro.ecommerce.model.Pedido;
import com.pedro.ecommerce.model.StatusPedido;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;


public class MapeamentoObjetoEmbutido extends EntityManagerTest {

    @Test
    public void analisarMapeamentoObjetoEmbutido(){
        Cliente cliente = entityManager.find(Cliente.class, 1);

        EnderecoEntregaPedido endereco = new EnderecoEntregaPedido();
        endereco.setCep("74481-110");
        endereco.setLogradouro("Rua Jc 37");
        endereco.setBairro("Jardim Curitiba");
        endereco.setNumero("0");
        endereco.setCidade("Goiânia");
        endereco.setEstado("GO");

        Pedido pedido = new Pedido();
        //pedido.setId(1); Comentado porque estamos utilizando IDENTITY
        pedido.setDataCriacao(LocalDateTime.now());
        pedido.setStatus(StatusPedido.AGUARDANDO);
        pedido.setTotal(new BigDecimal(1000));
        pedido.setEnderecoEntrega(endereco);
        pedido.setCliente(cliente);

        entityManager.getTransaction().begin();
        entityManager.persist(pedido);
        entityManager.getTransaction().commit();

        entityManager.clear();

        Pedido pedidoVerificacao = entityManager.find(Pedido.class, pedido.getId());
        Assert.assertNotNull(pedidoVerificacao);
        Assert.assertNotNull(pedidoVerificacao.getEnderecoEntrega());
        Assert.assertNotNull(pedidoVerificacao.getEnderecoEntrega().getCep());
    }
}
