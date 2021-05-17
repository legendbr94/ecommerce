package com.pedro.ecommerce.detalhesimportantes;

import com.pedro.ecommerce.EntityManagerTest;
import com.pedro.ecommerce.model.Cliente;
import org.junit.Test;

public class ValidacaoTest extends EntityManagerTest {

    @Test
    public void validarCliente() {
        entityManager.getTransaction().begin();

        Cliente cliente = new Cliente();
        entityManager.merge(cliente);

        entityManager.getTransaction().commit();
    }
}