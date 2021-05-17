package com.pedro.ecommerce.mapeamentobasico;

import com.pedro.ecommerce.EntityManagerTest;
import com.pedro.ecommerce.model.Categoria;
import org.junit.Assert;
import org.junit.Test;

public class EstrategiaChavePrimariaTest extends EntityManagerTest {

    @Test
    public void testarEstrategiaChave(){
        Categoria categoria = new Categoria();
        categoria.setNome("Natação");

        entityManager.getTransaction().begin();
        entityManager.persist(categoria);
        entityManager.getTransaction().commit();

        entityManager.clear();

        Categoria categoriaVerificacao = entityManager.find(Categoria.class,categoria.getId());
        Assert.assertNotNull(categoriaVerificacao);
    }
}
