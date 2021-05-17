package com.pedro.ecommerce.iniciandocomjpa;

import com.pedro.ecommerce.EntityManagerTest;
import com.pedro.ecommerce.model.Cliente;
import com.pedro.ecommerce.model.SexoCliente;
import org.junit.Assert;
import org.junit.Test;

public class PrimeiroCrudTest extends EntityManagerTest {

    @Test
    public void inserirRegistro(){
        Cliente cliente = new Cliente();

        //cliente.setId(3); Comentado porque estamos utilizando IDENTITY
        cliente.setNome("Pedro Henrique");
        cliente.setSexo(SexoCliente.MASCULINO);
        cliente.setCpf("27130533153");

        entityManager.getTransaction().begin();
        entityManager.persist(cliente);
        entityManager.getTransaction().commit();
        entityManager.clear();

        Cliente clienteVerificacao = entityManager.find(Cliente.class, cliente.getId());

        Assert.assertNotNull(clienteVerificacao);

    }

    @Test
    public void buscarPorIdentificador(){
        Cliente cliente = entityManager.find(Cliente.class,2);
        Cliente verificarCliente = entityManager.find(Cliente.class, cliente.getId());
        Assert.assertNotNull(verificarCliente);

        System.out.println("Cliente encontrado: " + cliente.getNome());

    }

    @Test
    public void atualizarRegistro(){
        Cliente cliente = entityManager.find(Cliente.class,2);
        cliente.setNome("Abobrinha");
        cliente.setCpf("75103427168");
        cliente.setSexo(SexoCliente.MASCULINO);

        entityManager.getTransaction().begin();
        entityManager.merge(cliente);
        entityManager.getTransaction().commit();
        entityManager.clear();

        Cliente verificarCliente = entityManager.find(Cliente.class, cliente.getId());

        Assert.assertEquals("Abobrinha", verificarCliente.getNome());
    }

    @Test
    public void removerRegistro(){
        Cliente cliente = entityManager.find(Cliente.class, 2);

        entityManager.getTransaction().begin();
        entityManager.remove(cliente);
        entityManager.getTransaction().commit();
        entityManager.clear();

        Cliente verificarCliente = entityManager.find(Cliente.class, cliente.getId());

        Assert.assertNull(verificarCliente);
    }

}
