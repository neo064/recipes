package com.neo064.recipe.server.data.dao.common;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import org.springframework.core.GenericTypeResolver;
import org.springframework.util.StringUtils;

import com.neo064.recipe.model.BaseEntity;

/**
 * Basic Dao, implementation of {@link IGenericDao}. All daos inherits it.
 *
 * @author Neo
 *
 * @param <T>
 *            the type of the entity
 */
public abstract class GenericDao<T extends BaseEntity> implements IGenericDao<T> {

	@PersistenceContext
	private transient EntityManager entityManager;

	private final Class<T> genericType;

	/**
	 * Constructor.
	 */
	@SuppressWarnings("unchecked")
	public GenericDao() {
		this.genericType = (Class<T>) GenericTypeResolver.resolveTypeArgument(getClass(), GenericDao.class);
	}

	@Override
	public T findById(final Long id) {
		final CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<T> query = criteriaBuilder.createQuery(genericType);
		final Root<T> from = query.from(genericType);
		query.where(criteriaBuilder.equal(from.<Long>get(BaseEntity.ID_CONST), id));
		return entityManager.createQuery(query).getSingleResult();
	}

	@Override
	@Transactional
	public T save(final T entity) {
		return entityManager.merge(entity);
	}

	@Override
	@Transactional
	public void delete(final T entity) {
		entityManager.remove(entity);
	}

	public EntityManager getEntityManager() {
		return entityManager;
	}

	protected String buildLike(final String likeValue) {
		String key = "";
		if (!StringUtils.isEmpty(likeValue)) {
			key = likeValue.toLowerCase();
		}
		return new StringBuilder().append('%').append(key).append('%').toString();
	}

	@Override
	public List<T> findAll() {
		final CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<T> query = criteriaBuilder.createQuery(genericType);
		query.from(genericType);
		return entityManager.createQuery(query).getResultList();
	}

	@Override
	public Long findAllCount() {
		final CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
		final CriteriaQuery<Long> query = criteriaBuilder.createQuery(Long.class);
		final Root<T> from = query.from(genericType);
		query.select(criteriaBuilder.count(from));
		return getEntityManager().createQuery(query).getSingleResult();
	}
}
