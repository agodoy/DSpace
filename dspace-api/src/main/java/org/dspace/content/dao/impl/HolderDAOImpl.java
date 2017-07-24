package org.dspace.content.dao.impl;

import java.sql.SQLException;
import java.util.List;
import java.util.UUID;
import org.dspace.content.Community;
import org.dspace.content.Holder;
import org.dspace.content.dao.HolderDAO;
import org.dspace.core.AbstractHibernateDSODAO;
import org.dspace.core.Context;
import org.hibernate.Query;

public class HolderDAOImpl extends AbstractHibernateDSODAO<Holder>  implements HolderDAO {

	@Override
	public Holder findByLegacyId(Context context, int legacyId, Class<Holder> clazz) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Holder create(Context context, Holder t) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void save(Context context, Holder t) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(Context context, Holder t) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public List<Holder> findAll(Context context, Class<Holder> clazz) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Holder findUnique(Context context, String query) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Holder findByID(Context context, Class clazz, int id) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Holder findByID(Context context, Class clazz, UUID id) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Holder> findMany(Context context, String query) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Holder> findAll(Context context) throws SQLException {
		StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append("SELECT ").append(Community.class.getSimpleName()).append(" FROM Community as ").append(Community.class.getSimpleName()).append(" ");
        
        Query query = createQuery(context, queryBuilder.toString());
        return list(query);
	}

}
