package br.com.scrumming.core.infra.manager;

import java.io.Serializable;
import java.util.Collection;

import br.com.scrumming.core.infra.repositorio.AbstractRepositorio;
import br.com.scrumming.core.infra.repositorio.ObjetoPersistente;

public interface IManager<Entidade extends ObjetoPersistente<Chave>, Chave extends Serializable>
        extends Serializable {

    /**
     * Insere ou Atualiza uma Entidade
     * 
     * @param element
     * @return Chave
     */
    Chave insertOrUpdate(Entidade element);

    /**
     * Remove uma entidade
     * 
     * @param element
     */
    void remove(Entidade element);

    /**
     * Retorna o Repositorio que será umplementado na classe filha
     * 
     * @return AbstractRepositorio<Entidade, Chave>
     */
    AbstractRepositorio<Entidade, Chave> getRepositorio();

    /**
     * Encontra o objeto pelaChave
     * 
     * @param chave
     * @return Entidade
     */
    Entidade findByKey(Chave chave);

    /**
     * Retorna todos os registros de Uma entidade
     * 
     * @return Collection<Entidade>
     */
    Collection<Entidade> findAll();

    /**
     * Consulta uma entidade de acodo com os filtros passados
     * 
     * @param isLike - se a consulta deverá ativar o like no banco de dados
     * @param typeLike - o tipo de like a ser utilizado
     * @param ignoreCase - se a consulta deverá diferenciar MAIÚSCULAS de minúsculas
     * @param order - o atributo de ordenação da consulta
     * @param sense - o sentido da ordenação
     * @param filtro - o objeto do tipo dos objetos consultados
     * 
     * @return Uma coleção com os objetos filtrados na base de dados
     */
    Collection<Entidade> findByFilter(boolean isLike, String typeLike, boolean ignoreCase,
            String order, String sense, Entidade filtro);

    /**
     * Método que busca na base de dados de acordo com um filtro estabelecido, utilizando paginação
     * 
     * @param start - primeiro registro
     * @param end - último registro
     * @param isLike - se a consulta deverá ativar o like no banco de dados
     * @param typeLike - o tipo de like a ser utilizado
     * @param ignoreCase - se a consulta deverá diferenciar MAIÚSCULAS de minúsculas
     * @param order - o atributo de ordenação da consulta
     * @param sense - o sentido da ordenação
     * @param filtro - o objeto do tipo dos objetos consultados
     * 
     * @return uma coleção com os objetos filtrados na base de dados
     */
    Collection<Entidade> findFilterPaginator(int start, int end, boolean isLike, String typeLike,
            boolean ignoreCase, String order, String sense, Entidade filtro);

    /**
     * Método que consulta pela todas es entidades que tenha o nome do campo igual ao valor do
     * campo.
     * 
     * @param nomeCampo - o nome do atributo declarado na entidade.
     * @param valorCampo - o valor do atributo declarado na entidade.
     * @return uma coleção com os objetos filtrados pelo campo.
     */
    Collection<Entidade> consultarPorCampo(String nomeCampo, Object valorCampo);
}
