package com.uniamerica.unijobsbackend.services;

import com.uniamerica.unijobsbackend.Excessoes.RecursoNaoEncontradoExcessao;
import com.uniamerica.unijobsbackend.models.Produto;
import com.uniamerica.unijobsbackend.repositories.RepositorioProduto;
import com.uniamerica.unijobsbackend.repositories.RepositorioTipoProduto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;

@Service
public class ProdutoService {
    @Autowired
    private final RepositorioProduto repositorioProduto;

    @Autowired
    private final RepositorioTipoProduto repositorioTipoProduto;

    public ProdutoService(RepositorioProduto repositorioProduto, RepositorioTipoProduto repositorioTipoProduto) {
        this.repositorioProduto = repositorioProduto;
        this.repositorioTipoProduto = repositorioTipoProduto;
    }

    public List<Produto> VisualizarProduto(){
        return repositorioProduto.findAll();
    }

    public Produto CadastrarProduto(Produto produto) {
        Integer id_tipo_produto = produto.getTipoProduto().getId_tipo_produto();

        var produto1 = repositorioTipoProduto.findById(id_tipo_produto)
          .orElseThrow(
            () -> new RecursoNaoEncontradoExcessao("Categoria não Encontrada! id:" + id_tipo_produto)
          );
        produto.setTipoProduto(produto1);

        return repositorioProduto.save(produto);
    }

    public String DeletarProduto(Integer id) {
        boolean existe = repositorioProduto.existsById(id);
        if(!existe){
            throw new RecursoNaoEncontradoExcessao("Produto não Existe. id: " + id);
        }
        repositorioProduto.deleteById(id);
        return "Produto deletado com sucesso!";
    }

    @Transactional
    public Produto EditarProduto(Integer id, Produto novoProduto) {
        Produto produto1 = repositorioProduto.findById(id)
                .orElseThrow(
                        () -> new IllegalStateException("Produto não Existe. id: " + id)
                );
        if(novoProduto.getTitulo() != null && !Objects.equals(novoProduto.getTitulo(), produto1.getTitulo())){
            produto1.setTitulo(novoProduto.getTitulo());
        }
        if (novoProduto.getDescricao() != null && !Objects.equals(novoProduto.getDescricao(), produto1.getDescricao())){
            produto1.setDescricao(novoProduto.getDescricao());
        }
        if (novoProduto.getMiniatura() != null && !Objects.equals(produto1.getMiniatura(), novoProduto.getMiniatura())){
            produto1.setMiniatura(novoProduto.getMiniatura());
        }

        if (novoProduto.getPrazo() != null && !Objects.equals(produto1.getPrazo(), novoProduto.getPrazo())){
            produto1.setPrazo(novoProduto.getPrazo());
        }

        if (novoProduto.getPreco() != null && !Objects.equals(produto1.getPreco(), novoProduto.getPreco())){
            produto1.setPreco(novoProduto.getPreco());
        }

        if (novoProduto.getTipoProduto() != null && !Objects.equals(produto1.getTipoProduto(), novoProduto.getTipoProduto())){
            produto1.setTipoProduto(novoProduto.getTipoProduto());
        }

        if (!Objects.equals(produto1.getAtivo(), novoProduto.getAtivo())){
            produto1.setAtivo(novoProduto.getAtivo());
        }
        return produto1;
    }
}
