package com.pedro.ecommerce.criteria;

import com.pedro.ecommerce.EntityManagerTest;
import com.pedro.ecommerce.dto.ProdutoDTO;
import com.pedro.ecommerce.model.*;
import org.junit.Assert;
import org.junit.Test;

import javax.persistence.Tuple;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class BasicoCriteriaTest extends EntityManagerTest {

    @Test
    public void usarDistinct() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Pedido> criteriaQuery = criteriaBuilder.createQuery(Pedido.class);
        Root<Pedido> root = criteriaQuery.from(Pedido.class);
        root.join(Pedido_.itens);

        criteriaQuery.select(root);
        criteriaQuery.distinct(true);

        TypedQuery<Pedido> typedQuery = entityManager.createQuery(criteriaQuery);
        List<Pedido> lista = typedQuery.getResultList();

        lista.forEach(p -> System.out.println("ID: " + p.getId()));
    }

    @Test
    public void ordenarResultados() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Cliente> criteriaQuery = criteriaBuilder.createQuery(Cliente.class);
        Root<Cliente> root = criteriaQuery.from(Cliente.class);

        criteriaQuery.orderBy(criteriaBuilder.desc(root.get(Cliente_.nome)));

        TypedQuery<Cliente> typedQuery = entityManager.createQuery(criteriaQuery);

        List<Cliente> lista = typedQuery.getResultList();
        Assert.assertFalse(lista.isEmpty());

        lista.forEach(c -> System.out.println(c.getId() + ", " + c.getNome()));
    }


    @Test
    //Melhor Forma de retorno que a TUPLE e OBJECT
    public void projetarOResultadoDTO(){
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<ProdutoDTO> criteriaQuery = criteriaBuilder.createQuery(ProdutoDTO.class);
        Root<Produto> root = criteriaQuery.from(Produto.class);

        criteriaQuery.select(criteriaBuilder
                .construct(ProdutoDTO.class,root.get("id"),root.get("nome")));

        TypedQuery<ProdutoDTO> typedQuery = entityManager.createQuery(criteriaQuery);
        List<ProdutoDTO>lista = typedQuery.getResultList();
        Assert.assertFalse(lista.isEmpty());

        lista.forEach(dto -> System.out.println("ID: " + dto.getId() + " NOME: " + dto.getNome()));
    }

    @Test
    public void projetarOResultadoTuple(){
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Tuple> criteriaQuery = criteriaBuilder.createTupleQuery();

        Root<Produto> root = criteriaQuery.from(Produto.class);

        //criteriaQuery.multiselect(root.get("id"),root.get("nome"));
        criteriaQuery.select(criteriaBuilder.tuple(root.get("id").alias("id"),root.get("nome").alias("nome")));

        TypedQuery<Tuple> typedQuery = entityManager.createQuery(criteriaQuery);
        List<Tuple>lista = typedQuery.getResultList();
        Assert.assertFalse(lista.isEmpty());

        lista.forEach(t -> System.out.println("ID: " + t.get(0) + " NOME: " + t.get(1)));
    }

    @Test
    public void projetarOResultado(){
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Object[]> criteriaQuery = criteriaBuilder.createQuery(Object[].class);

        Root<Produto> root = criteriaQuery.from(Produto.class);

        criteriaQuery.multiselect(root.get("id"),root.get("nome"));

        TypedQuery<Object[]> typedQuery = entityManager.createQuery(criteriaQuery);
        List<Object[]>lista = typedQuery.getResultList();
        Assert.assertFalse(lista.isEmpty());

        lista.forEach(arr -> System.out.println("ID: " + arr[0] + " NOME: " + arr[1]));
    }

    @Test
    public void retornarTodosOsProdutosExercicio(){
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Produto>criteriaQuery = criteriaBuilder.createQuery(Produto.class);
        Root<Produto> root = criteriaQuery.from(Produto.class);

        criteriaQuery.select(root);

        TypedQuery<Produto> typedQuery = entityManager.createQuery(criteriaQuery);
        List<Produto> lista = typedQuery.getResultList();
        Assert.assertFalse(lista.isEmpty());
    }


    @Test
    public void selecionarUmAtributoParaRetorno(){
        // Aqui a descri????o mais adequada seria a seguinte:
        // Declaro a vari??vel criteriaBuilder, que recebe o retorno do m??todo getCriteriaBuilder invocado a partir da vari??vel entityManager
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

        // A classe CriteriaQuery ?? quem vai receber as instru????es pra montar a consulta.
        // Nesse momento n??o temos nada pra equiparar com uma consulta SQL ainda. Apenas criamos as classes necess??rias
        CriteriaQuery<Cliente> criteriaQuery = criteriaBuilder.createQuery(Cliente.class);

        // Aqui, j?? temos algo equivalente ao seguinte: "from pedido"
        // Pensando em uma consulta SQL nativa, isso n??o rodaria, e aqui tamb??m n??o, pois precisamos no m??nimo indicar quais campos queremos selecionar
        Root<Pedido> root = criteriaQuery.from(Pedido.class);


        // Aqui indicamos os campos que queremos selecionar. A classe Root mant??m uma refer??ncia a tabela raiz da consulta, declarada na cl??usula "from". Nesse caso, a tabela pedido
        // Nesse momento, temos uma consulta equiparada a: "select cliente from pedido"
        // Agora a consulta j?? poderia ser executada
        criteriaQuery.select(root.get("cliente"));

        // Pra retornar todos os campos (*), seria dessa forma:
        // criteriaQuery.select(root);
        // Assim a consulta seria "select * from pedido"

        // Se precisamos filtrar os dados, adicionamos a cl??usula "where"
        // Aqui a consulta est?? equiparada a "select cliente from pedido where id = 1"
        criteriaQuery.where(criteriaBuilder.equal(root.get("id"),1));

        // A classe CriteriaQuery apenas define a consulta. A execu????o fica a cargo de Query, nesse caso usamos a especializa????o TypedQuery que permite definir o tipo do retorno
        TypedQuery<Cliente> typedQuery = entityManager.createQuery(criteriaQuery);


        // Executamos a consulta e na verdade n??o estamos "garantindo" que ?? um resultado ??nico. Estamos dizendo que esperamos por um resultado ??nico. Se a consulta resultar em mais de um, uma exce????o ser?? lan??ada
        Cliente cliente = typedQuery.getSingleResult();

        //Validando pelo Assert  se o resultado retornado ?? igual ao parametro informado.
        Assert.assertEquals("Thaynara Oliveira", cliente.getNome());
    }

    @Test
    public void buscarPorIdentificador(){

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Pedido> criteriaQuery = criteriaBuilder.createQuery(Pedido.class);
        Root<Pedido> root = criteriaQuery.from(Pedido.class);

        criteriaQuery.select(root);

        criteriaQuery.where(criteriaBuilder.equal(root.get("id"), 1));

        //String jpql = "select p from Pedido p where p.id = 1";

        TypedQuery<Pedido> typedQuery = entityManager
                //.createQuery(jpql, Pedido.class);
                .createQuery(criteriaQuery);

        Pedido pedido = typedQuery.getSingleResult();
        Assert.assertNotNull(pedido);

    }
}
