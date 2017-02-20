package com.neo064.recipe.server.data.dao.unit;

import org.springframework.stereotype.Repository;

import com.neo064.recipe.model.Unit;
import com.neo064.recipe.server.data.dao.common.GenericDao;

/**
 * Implementation of the {@link IUnitDao}.
 * 
 * @author Neo
 *
 */
@Repository
public class UnitDao extends GenericDao<Unit> implements IUnitDao {

}
