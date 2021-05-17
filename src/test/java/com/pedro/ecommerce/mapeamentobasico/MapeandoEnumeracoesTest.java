package com.pedro.ecommerce.mapeamentobasico;

import com.pedro.ecommerce.EntityManagerTest;
import com.pedro.ecommerce.model.Cliente;
import com.pedro.ecommerce.model.SexoCliente;
import org.junit.Assert;
import org.junit.Test;

public class MapeandoEnumeracoesTest  extends EntityManagerTest {

    @Test
    public void testarEnum(){
        Cliente cliente = new Cliente();
        //cliente.setId(4); Comentado porque estamos utilizando IDENTITY
        cliente.setNome("Pedro");
        cliente.setSexo(SexoCliente.MASCULINO);
        cliente.setCpf("777");

        entityManager.getTransaction().begin();
        entityManager.persist(cliente);
        entityManager.getTransaction().commit();

        entityManager.clear();

        Cliente clienteVerificacao = entityManager.find(Cliente.class,cliente.getId());
        Assert.assertNotNull(clienteVerificacao);

    }
}
