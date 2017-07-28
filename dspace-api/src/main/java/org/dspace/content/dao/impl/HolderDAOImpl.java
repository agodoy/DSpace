package org.dspace.content.dao.impl;

import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.dspace.content.Community;
import org.dspace.content.Holder;
import org.dspace.content.Item;
import org.dspace.content.dao.HolderDAO;
import org.dspace.core.AbstractHibernateDSODAO;
import org.dspace.core.Context;
import org.hibernate.Query;

public class HolderDAOImpl extends AbstractHibernateDSODAO<Holder>  implements HolderDAO {

    private static Logger log = Logger.getLogger(HolderDAOImpl.class);
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
		
//		log.info("llega a la dao");
//		StringBuilder queryBuilder = new StringBuilder();
//        queryBuilder.append("SELECT email from Item, EPerson e where submitter_id = e.uuid");
//
//		log.info("arma query: " + queryBuilder.toString());
//        Query query = createQuery(context, queryBuilder.toString());
//        log.info("arma query: " + query);
        

//        List<Item> items = list(createQuery(context, "SELECT * from Item"));
        
        
        
        return null;
	}

}
