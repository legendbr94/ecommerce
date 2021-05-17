package com.pedro.ecommerce.jpql;

import com.pedro.ecommerce.EntityManagerTest;
import com.pedro.ecommerce.dto.ProdutoDTO;
import com.pedro.ecommerce.model.Categoria;
import com.pedro.ecommerce.model.Cliente;
import com.pedro.ecommerce.model.Pedido;
import org.junit.Assert;
import org.junit.Test;

import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

public class PaginacaoJPQLTest extends EntityManagerTest {

    @Test
    public void paginarResultados(){
        String jpql = "select c from Categoria c order by c.nome";

        TypedQuery<Categoria> typedQuery = entityManager.createQuery(jpql,Categoria.class);
        //FIRST_RESULT = MAX_RESULTS + (pagina -1)
        typedQuery.setFirstResult(2);
        typedQuery.setMaxResults(2);

        List<Categoria> lista = typedQuery.getResultList();
        Assert.assertFalse(lista.isEmpty());

        lista.forEach(c -> System.out.println(c.getId() + ", " + c.getNome()));
    }

}
