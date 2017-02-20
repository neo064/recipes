package com.neo064.recipe.server.data.dao.recipe;

import java.util.List;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;

import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.google.common.base.Optional;
import com.google.common.collect.Lists;
import com.neo064.recipe.model.BaseEntity;
import com.neo064.recipe.model.FoodProduct;
import com.neo064.recipe.model.Ingredient;
import com.neo064.recipe.model.Recipe;
import com.neo064.recipe.server.data.dao.common.GenericDao;

/**
 * Implementation of the {@link IRecipeDao}.
 * 
 * @author Neo
 *
 */
@Repository
public class RecipeDao extends GenericDao<Recipe> implements IRecipeDao {

	@Override
	public List<Recipe> findByKeyword(final String keyword, final Optional<Long> offset,
			final Optional<Long> maxResults) {
		if (StringUtils.isEmpty(keyword)) {
			return Lists.newArrayList();
		}
		final CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
		final CriteriaQuery<Recipe> query = criteriaBuilder.createQuery(Recipe.class);
		final Root<Recipe> from = query.from(Recipe.class);

		query.where(getFindByKeywordClause(keyword, criteriaBuilder, query, from));

		query.orderBy(criteriaBuilder.asc(from.get(Recipe.TITLE_CONST)));
		TypedQuery<Recipe> createQuery = getEntityManager().createQuery(query);

		if (offset.isPresent()) {
			createQuery = createQuery.setFirstResult(offset.get().intValue());
		}
		if (maxResults.isPresent()) {
			createQuery = createQuery.setMaxResults(maxResults.get().intValue());
		}
		return createQuery.getResultList();
	}

	@Override
	public List<Recipe> findAutoComplete(final String keyword) {
		if (StringUtils.isEmpty(keyword)) {
			return Lists.newArrayList();
		}
		final CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
		final CriteriaQuery<Recipe> query = criteriaBuilder.createQuery(Recipe.class);
		final Root<Recipe> from = query.from(Recipe.class);

		query.where(getFindByKeywordClause(keyword, criteriaBuilder, query, from));
		query.multiselect(from.get(BaseEntity.ID_CONST), from.get(Recipe.TITLE_CONST));
		query.orderBy(criteriaBuilder.asc(from.get(Recipe.TITLE_CONST)));
		return getEntityManager().createQuery(query).getResultList();
	}

	@Override
	public Long nbRecordsByKeyword(final String keyword) {
		if (StringUtils.isEmpty(keyword)) {
			return 0L;
		}
		final CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
		final CriteriaQuery<Long> query = criteriaBuilder.createQuery(Long.class);
		final Root<Recipe> from = query.from(Recipe.class);
		query.select(criteriaBuilder.count(from));
		query.where(getFindByKeywordClause(keyword, criteriaBuilder, query, from));
		return getEntityManager().createQuery(query).getSingleResult();
	}

	private Predicate getFindByKeywordClause(final String keyword, final CriteriaBuilder criteriaBuilder,
			final CriteriaQuery<?> query, final Root<Recipe> from) {
		final Subquery<Recipe> subqueryRecipe = query.subquery(Recipe.class);
		final Root<Recipe> fromSubqueryRecipe = subqueryRecipe.from(Recipe.class);
		subqueryRecipe.select(fromSubqueryRecipe);
		subqueryRecipe.where(criteriaBuilder.and(
				criteriaBuilder.equal(from.get(BaseEntity.ID_CONST), fromSubqueryRecipe.get(BaseEntity.ID_CONST)),
				criteriaBuilder.like(criteriaBuilder.lower(fromSubqueryRecipe.get(Recipe.TITLE_CONST)),
						buildLike(keyword))));

		final Subquery<Recipe> subquery = query.subquery(Recipe.class);
		final Root<Recipe> fromSubquery = subquery.from(Recipe.class);
		final Join<Recipe, Ingredient> ingredientsJoin = fromSubquery.join(Recipe.INGREDIENTS_CONST, JoinType.LEFT);
		final Join<Ingredient, FoodProduct> foodProductJoin = ingredientsJoin.join(Ingredient.FOOD_PRODUCT_CONST,
				JoinType.LEFT);
		subquery.select(fromSubquery);
		subquery.where(criteriaBuilder.and(
				criteriaBuilder.equal(from.get(BaseEntity.ID_CONST), fromSubquery.get(BaseEntity.ID_CONST)),
				criteriaBuilder.like(criteriaBuilder.lower(foodProductJoin.get(FoodProduct.NAME_CONST)),
						buildLike(keyword))));
		return criteriaBuilder.or(criteriaBuilder.exists(subqueryRecipe), criteriaBuilder.exists(subquery));
	}

	@Override
	public List<Recipe> findAllPaginatedRecipes(final Long offset, final Long maxResults) {
		final CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
		final CriteriaQuery<Recipe> query = criteriaBuilder.createQuery(Recipe.class);
		final Root<Recipe> from = query.from(Recipe.class);
		query.orderBy(criteriaBuilder.asc(from.get(Recipe.TITLE_CONST)));
		return getEntityManager().createQuery(query).setFirstResult(offset.intValue())
				.setMaxResults(maxResults.intValue()).getResultList();
	}

}
