package com.pedro.ecommerce.relacionamentos;

import com.pedro.ecommerce.EntityManagerTest;
import com.pedro.ecommerce.model.Categoria;
import com.pedro.ecommerce.model.Pedido;
import org.junit.Assert;
import org.junit.Test;

public class RemovendoEntidadesReferenciadasTest extends EntityManagerTest {


    @Test
    public void removerEntidadeRelacionada(){
        Pedido pedido = entityManager.find(Pedido.class,1);

        Assert.assertFalse(pedido.getItens().isEmpty());

        entityManager.getTransaction().begin();
        //Removendo os itens do pedido antes da remoção do pedido.
        pedido.getItens().forEach(i -> entityManager.remove(i));

        //Removendo o pedido que já não possui mais nenhum item vinculado.
        entityManager.remove(pedido);
        entityManager.getTransaction().commit();

        entityManager.clear();

        Pedido pedidoVerificacao = entityManager.find(Pedido.class,1);
        Assert.assertNull(pedidoVerificacao);
    }

}
