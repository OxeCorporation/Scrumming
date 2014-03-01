package br.com.scrumming.core.infra.repositorio;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.Collection;
import java.util.Collections;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;

import br.com.scrumming.core.infra.exceptions.ObjectNotFoundException;
import br.com.scrumming.core.infra.exceptions.RepositoryException;

public abstract class AbstractRepositorio<Entidade extends ObjetoPersistente<Chave>, Chave extends Serializable>
		implements Repositorio<Entidade, Chave> {

	private static final String UNCHECKED = "unchecked";

	@Autowired
	private SessionFactory sessionFactory;

	protected SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public Session getSession() {
		return getSessionFactory().getCurrentSession();
	}

	@SuppressWarnings(UNCHECKED)
	protected Class<Entidade> getEntidadeClazz() {
		return (Class<Entidade>) ((ParameterizedType) getClass()
				.getGenericSuperclass()).getActualTypeArguments()[0];
	}

	protected Criteria createCriteria() {
		Session currentSession = getSession();
		return currentSession.createCriteria(getEntidadeClazz());
	}

	protected Criteria createCriteria(Class<?> classe) {
		return getSession().createCriteria(classe);
	}

	/** {@inheritDoc} */
	@Override
	public Chave insertOrUpdate(Entidade entidade) {
		getSession().saveOrUpdate(entidade);
		return entidade.getChave();
	}

	@Override
	/** {@inheritDoc} */
	public void remove(Entidade entidade) throws RepositoryException {
		Session sessao = getSession();
		try {
			sessao.delete(entidade);
			sessao.flush();
		} catch (org.hibernate.exception.ConstraintViolationException e) {
			throw new RepositoryException(e, "erro.repository.constraint."
					+ e.getConstraintName());
		} catch (HibernateException e) {
			throw new RepositoryException(e, "erro.repository");
		} catch (Exception e) {
			throw new RepositoryException(e, "erro.repository.inesperado");
		}
	}

	/** {@inheritDoc} */
	@Override
	@SuppressWarnings(UNCHECKED)
	public Collection<Entidade> findAll() {
		return getSession().createCriteria(getEntidadeClazz()).list();
	}

	/** {@inheritDoc} */
	@SuppressWarnings(UNCHECKED)
	@Override
	public Entidade findByKey(Chave key) throws ObjectNotFoundException {
		Session sessao = getSession();
		Object elemento = sessao.get(getEntidadeClazz(), key);
		if (elemento == null) {
			throw new ObjectNotFoundException("erro.dao.naoEncontrado."
					+ getEntidadeClazz().getName());
		}
		return (Entidade) elemento;
	}

	/** {@inheritDoc} */
	@Override
	@SuppressWarnings(UNCHECKED)
	public Collection<Entidade> consultarPorCampo(String nomeCampo,
			Object valorCampo) {

		Criteria criteria = createCriteria(getEntidadeClazz());

		if (valorCampo instanceof String) {
			criteria.add(Restrictions.eq(nomeCampo, valorCampo).ignoreCase());
		} else {
			criteria.add(Restrictions.eq(nomeCampo, valorCampo));
		}

		return Collections.checkedCollection(criteria.list(),
				getEntidadeClazz());
	}

	/** {@inheritDoc} */
	@SuppressWarnings(UNCHECKED)
	@Override
	public Collection<Entidade> findByFilter(boolean isLike, String typeLike,
			boolean ignoreCase, String ordem, String sense, Entidade filtro) {

		Session session = getSession();
		Criteria criteria = session.createCriteria(getEntidadeClazz());
		Example example = Example.create(filtro);
		example = getLike(isLike, typeLike, example);
		example = getIgnoreCase(ignoreCase, example);
		criteria.add(example);
		getOrder(ordem, sense, criteria);
		return Collections.checkedCollection(criteria.list(),
				getEntidadeClazz());
	}

	/** {@inheritDoc} */
	@Override
	@SuppressWarnings(UNCHECKED)
	public Collection<Entidade> findFilterPaginator(int start, int end,
			boolean isLike, String typeLike, boolean ignoreCase, String ordem,
			String sense, Entidade filtro) {

		Session session = getSession();
		Criteria criteria = session.createCriteria(getEntidadeClazz());
		Example exemple = Example.create(filtro);
		exemple = getLike(isLike, typeLike, exemple);
		exemple = getIgnoreCase(ignoreCase, exemple);
		criteria.add(exemple);
		criteria.setFirstResult(start);
		criteria.setMaxResults(end - start);
		getOrder(ordem, sense, criteria);
		return Collections.checkedCollection(criteria.list(),
				getEntidadeClazz());
	}

	private Example getLike(boolean isLike, String typeLike, Example e) {
		if (isLike) {
			if (typeLike.equals(ANY)) {
				e = e.enableLike(MatchMode.ANYWHERE);
			} else if (typeLike.equals(EQUALS)) {
				e = e.enableLike(MatchMode.EXACT);
			} else if (typeLike.equals(START)) {
				e = e.enableLike(MatchMode.START);
			} else {
				e = e.enableLike(MatchMode.END);
			}
		}
		return e;
	}

	private Example getIgnoreCase(boolean ignoreCase, Example e) {
		if (ignoreCase) {
			e = e.ignoreCase();
		}
		return e;
	}

	private void getOrder(String order, String sense, Criteria crit) {
		if (sense.equals(ASC)) {
			crit.addOrder(Order.asc(order));
		} else if (sense.equals(DESC)) {
			crit.addOrder(Order.desc(order));
		}
	}
}
