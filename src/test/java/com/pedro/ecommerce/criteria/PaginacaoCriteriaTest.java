package com.pedro.ecommerce.criteria;

import com.pedro.ecommerce.EntityManagerTest;
import com.pedro.ecommerce.model.Categoria;
import org.junit.Assert;
import org.junit.Test;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class PaginacaoCriteriaTest extends EntityManagerTest {

    @Test
    public void paginarResultadosCriteria(){
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Categoria> criteriaQuery = criteriaBuilder.createQuery(Categoria.class);
        Root<Categoria> root = criteriaQuery.from(Categoria.class);

        criteriaQuery
                .select(root)
                .orderBy(criteriaBuilder.asc(root.get("nome")));

        TypedQuery<Categoria> typedQuery = entityManager.createQuery(criteriaQuery);

        // FIRST_RESULT = MAX_RESULTS * (pagina - 1)
        typedQuery.setFirstResult(6);
        typedQuery.setMaxResults(2);

        List<Categoria> lista = typedQuery.getResultList();
    }
}
