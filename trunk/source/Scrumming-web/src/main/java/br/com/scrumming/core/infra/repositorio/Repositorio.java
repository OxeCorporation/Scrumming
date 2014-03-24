package br.com.scrumming.core.infra.repositorio;

import java.util.Collection;

import br.com.scrumming.core.infra.exceptions.ObjectNotFoundException;
import br.com.scrumming.core.infra.exceptions.RepositoryException;

public interface Repositorio<Entidade, Chave> {

    public static String ANY = "any";
    public static String EQUALS = "equals";
    public static String START = "start";
    public static String END = "end";
    public static String ASC = "asc";
    public static String DESC = "desc";

    /**
     * Método que consulta todos os registros da entidade
     * 
     * @return Collection<Entidade> - uma colecao da entidade
     */
    Collection<Entidade> findAll();

    /**
     * Método que busca um elemento pela sua chave
     * 
     * @param key - a chave da entidade
     * @return Entidade - a entidade buscada
     * @throws ObjectNotFoundException
     */
    Entidade findByKey(Chave key) throws ObjectNotFoundException;

    /**
     * Método que insere ou atualiza os dados, retorna sua chave caso a operação tenha sido com
     * feita.
     * 
     * @param Entidade - a entidade a ser persistida
     * @return Key - a chave da entidade
     */
    Chave insertOrUpdate(Entidade entidade);

    /**
     * Método que remove um determinado registro pela sua chave.
     * 
     * @param paramKey - a chave da entidade
     * @throws RepositoryException
     */
    void remove(Entidade key) throws RepositoryException;

    
    Chave save(Entidade entidade);
    /**
     * Método que busca na base de dados de acordo com um filtro estabelecido
     * 
     * @param isLike - se a consulta deverá ativar o like no banco de dados
     * @param typeLike - o tipo de like a ser utilizado
     * @param ignoreCase - se a consulta deverá diferenciar MAIÚSCULAS de minúsculas
     * @param order - o atributo de ordenação da consulta
     * @param sense - o sentido da ordenação
     * @param filtro - o objeto do tipo dos objetos consultados
     * 
     * @return Uma coleção com os objetos filtrados na base de dados
     * */
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
     * */
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
