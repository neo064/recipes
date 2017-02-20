package com.neo064.recipe.server.data.dao.foodproduct;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.google.common.collect.Lists;
import com.neo064.recipe.model.FoodProduct;
import com.neo064.recipe.server.data.dao.common.GenericDao;
import com.neo064.recipe.server.data.dao.common.ISearchAutoComplete;

/**
 * Implementation of the {@link IFoodProductDao}.
 * 
 * @author Neo
 *
 */
@Repository
public class FoodProductDao extends GenericDao<FoodProduct>
		implements IFoodProductDao, ISearchAutoComplete<FoodProduct> {

	@Override
	public List<FoodProduct> findAutoComplete(final String keyword) {
		if (StringUtils.isEmpty(keyword)) {
			return Lists.newArrayList();
		}
		final CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
		final CriteriaQuery<FoodProduct> query = criteriaBuilder.createQuery(FoodProduct.class);
		final Root<FoodProduct> from = query.from(FoodProduct.class);
		query.where(criteriaBuilder.like(from.get(FoodProduct.NAME_CONST), buildLike(keyword)));
		query.orderBy(criteriaBuilder.asc(from.get(FoodProduct.NAME_CONST)));
		return getEntityManager().createQuery(query).getResultList();
	}

}
