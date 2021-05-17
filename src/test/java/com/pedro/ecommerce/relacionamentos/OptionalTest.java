package com.pedro.ecommerce.relacionamentos;

import com.pedro.ecommerce.EntityManagerTest;
import com.pedro.ecommerce.model.Pedido;
import org.junit.Test;

public class OptionalTest extends EntityManagerTest {

    @Test
    public void verficarComportamento() {
        Pedido pedido = entityManager.find(Pedido.class, 1);

    }
}
