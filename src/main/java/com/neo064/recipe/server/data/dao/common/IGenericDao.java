package com.neo064.recipe.server.data.dao.common;

import java.io.Serializable;
import java.util.List;

import com.neo064.recipe.model.BaseEntity;

/**
 * Contains basic functions to retrieve data from database.
 *
 * @author Neo
 *
 * @param <T>
 *            the type of data to retrieve
 */
public interface IGenericDao<T extends BaseEntity> extends Serializable {

	/**
	 * Find an entity by its unique id.
	 *
	 * @param id
	 *            the id of the entity to retrieve.
	 * @return the corresponding entity.
	 */
	T findById(Long id);

	/**
	 * Save an entity into the database.
	 *
	 * @param entity
	 *            the entity to save.
	 * @return the saved entity with its id in the database.
	 */
	T save(T entity);

	/**
	 * Deletes the given entity from the database.
	 *
	 * @param entity
	 *            the entity to delete.
	 */
	void delete(T entity);

	/**
	 *
	 * @return retrieves all entities of the corresponding table T.
	 */
	List<T> findAll();

	/**
	 *
	 * @return the number of rows of the corresponding table T.
	 */
	Long findAllCount();

}
