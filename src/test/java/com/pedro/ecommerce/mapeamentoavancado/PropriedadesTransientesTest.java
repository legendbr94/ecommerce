package com.pedro.ecommerce.mapeamentoavancado;

import com.pedro.ecommerce.EntityManagerTest;
import com.pedro.ecommerce.model.*;
import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDateTime;

public class PropriedadesTransientesTest extends EntityManagerTest {

    @Test
    public void validarPrimeiroNome() {
        Cliente cliente = entityManager.find(Cliente.class,1);

        Assert.assertEquals("Thaynara",cliente.getPrimeiroNome());
    }
}
