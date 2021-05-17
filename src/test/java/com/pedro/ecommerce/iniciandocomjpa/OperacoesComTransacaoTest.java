package com.pedro.ecommerce.iniciandocomjpa;

import com.pedro.ecommerce.EntityManagerTest;
import com.pedro.ecommerce.model.Produto;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;


public class OperacoesComTransacaoTest extends EntityManagerTest {

    @Test
    public void impedirOperacaoComBancoDeDados(){
        Produto produto = entityManager.find(Produto.class,1);
        entityManager.detach(produto);

        entityManager.getTransaction().begin();
        produto.setNome("Teste");
        entityManager.getTransaction().commit();

        entityManager.clear();

        Produto produtoVerificacao = entityManager.find(Produto.class,produto.getId());
        Assert.assertEquals("Teste",produto.getNome());
    }

    @Test
    public void mostrarDiferencaPersistMerge(){
        Produto produtoPersist = new Produto();
        
        //produtoPersist.setId(4);
        produtoPersist.setNome("Smartphone Android");
        produtoPersist.setDescricao("Top");
        produtoPersist.setPreco(new BigDecimal(2000));
        produtoPersist.setDataCriacao(LocalDateTime.now());

        entityManager.getTransaction().begin();
        entityManager.persist(produtoPersist);
        produtoPersist.setNome("Celular Android");
        entityManager.getTransaction().commit();

        entityManager.clear();

        Produto produtoVerificacaoPersist = entityManager.find(Produto.class, produtoPersist.getId());
        Assert.assertNotNull(produtoVerificacaoPersist);


        Produto produtoMerge = new Produto();

        //produtoMerge.setId(6); Comentado porque estamos utilizando IDENTITY
        produtoMerge.setNome("Notebook");
        produtoMerge.setDescricao("Notebook Top");
        produtoMerge.setPreco(new BigDecimal(2000));
        produtoMerge.setDataCriacao(LocalDateTime.now());

        entityManager.getTransaction().begin();
        produtoMerge = entityManager.merge(produtoMerge);
        System.out.println(">>" + produtoMerge);
        produtoMerge.setNome("Notebook 2");
        entityManager.getTransaction().commit();

        entityManager.clear();

        Produto produtoVerificacaoMerge = entityManager.find(Produto.class, produtoMerge.getId());
        Assert.assertNotNull(produtoVerificacaoMerge);

    }

    @Test
    public void inserirObjetoComMerge() {
        Produto produto = new Produto();

//        produto.setId(4);
        produto.setNome("Microfone Rode Videmic");
        produto.setDescricao("A melhor qualidade de som.");
        produto.setPreco(new BigDecimal(1000));
        produto.setDataCriacao(LocalDateTime.now());

        entityManager.getTransaction().begin();
        Produto produtoSalvo = entityManager.merge(produto);
        entityManager.getTransaction().commit();

        entityManager.clear();

        Produto produtoVerificacao = entityManager.find(Produto.class, produtoSalvo.getId());
        Assert.assertNotNull(produtoVerificacao);
    }




    @Test
    public void atualizarObjetoGerenciado(){
        Produto produto = entityManager.find(Produto.class,1);

        entityManager.getTransaction().begin();
        produto.setNome("Testando Nome Gerenciado");
        entityManager.getTransaction().commit();

        entityManager.clear();

        Produto produtoVerificacao = entityManager.find(Produto.class,produto.getId());
        Assert.assertEquals("Testando Nome Gerenciado",produto.getNome());
    }

    @Test
    public void atualizarObjeto(){
        Produto produto = new Produto();
        produto.setId(2);
        produto.setNome("Testando Nome");
        produto.setDescricao("Testando Descrição");
        produto.setPreco(new BigDecimal(500));

        entityManager.getTransaction().begin();
        entityManager.merge(produto);
        entityManager.getTransaction().commit();

        entityManager.clear();

        Produto produtoVerificacao = entityManager.find(Produto.class,produto.getId());
        Assert.assertNotNull(produtoVerificacao);
        Assert.assertEquals("Testando Nome",produto.getNome());
    }

    @Test
    public void removerObjeto() {
        Produto produto = entityManager.find(Produto.class,3);
        //produto.setId(3); Comentado porque estamos utilizando IDENTITY

        entityManager.getTransaction().begin();
        entityManager.remove(produto);
        entityManager.getTransaction().commit();

            //entityManager.clear(); Não é necessário na asserção para operação de remoção.

        Produto produtoVerificacao = entityManager.find(Produto.class,3);
        Assert.assertNull(produtoVerificacao);
    }


    @Test
    public void inserirOprimeiroObjeto(){
        Produto produto = new Produto();

        //produto.setId(2); Comentado porque estamos utilizando IDENTITY
        produto.setNome("Camera Canon");
        produto.setDescricao("Camera de digital");
        produto.setPreco(new BigDecimal(5000));
        produto.setDataCriacao(LocalDateTime.now());

        entityManager.getTransaction().begin();
        entityManager.persist(produto);
        entityManager.getTransaction().commit();

        entityManager.clear();

        Produto produtoVerificacao = entityManager.find(Produto.class, produto.getId());

        Assert.assertNotNull(produtoVerificacao);


    }

    @Test
    public void abrirEFecharATransacao() {
//        Produto produto = new Produto(); // Somente para o método não mostrar erros.

        entityManager.getTransaction().begin();

//        entityManager.persist(produto);
//        entityManager.merge(produto);
//        entityManager.remove(produto);

        entityManager.getTransaction().commit();
    }
}
