package br.com.scrumming.core.infra.manager;

import java.io.Serializable;
import java.util.Collection;

import org.springframework.transaction.annotation.Transactional;

import br.com.scrumming.core.infra.exceptions.ObjectNotFoundException;
import br.com.scrumming.core.infra.repositorio.ObjetoPersistente;

public abstract class AbstractManager<Entidade extends ObjetoPersistente<Chave>, Chave extends Serializable>
        implements IManager<Entidade, Chave> {

    /**
     * Serial version
     */
    private static final long serialVersionUID = 1L;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Chave insertOrUpdate(Entidade element) {
        validarInclusaoOuAlteracao(element);
        try {
            return getRepositorio().insertOrUpdate(element);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Chave save(Entidade entidade){
    	try {
			return getRepositorio().save(entidade);
		} catch (Exception e) {
			 throw new RuntimeException(e);
		}
    }
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void remove(Entidade entidade) {
        validarRemocao(entidade);
        try {
            getRepositorio().remove(entidade);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Entidade findByKey(Chave chave) {
        try {
            return getRepositorio().findByKey(chave);
        } catch (ObjectNotFoundException ex) {
            return null;
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Collection<Entidade> findAll() {
        try {
            return getRepositorio().findAll();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Collection<Entidade> findByFilter(boolean isLike, String typeLike, boolean ignoreCase,
            String ordem, String sense, Entidade filtro) {
        try {
            return getRepositorio()
                    .findByFilter(isLike, typeLike, ignoreCase, ordem, sense, filtro);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Collection<Entidade> findFilterPaginator(int start, int end, boolean isLike,
            String typeLike, boolean ignoreCase, String ordem, String sense, Entidade filtro) {
        try {
            return getRepositorio().findFilterPaginator(start, end, isLike, typeLike, ignoreCase,
                    ordem, sense, filtro);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Collection<Entidade> consultarPorCampo(String nomeCampo, Object valorCampo) {
        return getRepositorio().consultarPorCampo(nomeCampo, valorCampo);
    }

    public void validarInclusaoOuAlteracao(Entidade entidade) {
        // Implementado na subclasse caso necessário.
    }

    public void validarRemocao(Entidade entidade) {
        // Implementado na subclasse caso necessário.
    }
}
