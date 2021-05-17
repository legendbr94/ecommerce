package com.pedro.ecommerce.iniciandocomjpa;

import com.pedro.ecommerce.EntityManagerTest;
import com.pedro.ecommerce.model.Produto;
import org.junit.Assert;
import org.junit.Test;

public class ConsultandoRegistroTest extends EntityManagerTest {

    @Test
    public void buscarPorIdentificador(){
        Produto produto = entityManager.find(Produto.class,1);
        //Produto produto = entityManager.getReference(Produto.class,1);

        Assert.assertNotNull(produto);
        Assert.assertEquals("Kindle",produto.getNome());

    }

    @Test
    public void atualizarReferencia(){
        Produto produto = entityManager.find(Produto.class,1);
        produto.setNome("Garrafa");

        entityManager.refresh(produto);

        Assert.assertEquals("Kindle",produto.getNome());
    }
}
