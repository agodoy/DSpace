package org.dspace.content;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

import org.dspace.authorize.AuthorizeException;
import org.dspace.content.dao.HolderDAO;
import org.dspace.core.Context;
import org.springframework.beans.factory.annotation.Autowired;

public class HolderServiceImpl extends DSpaceObjectServiceImpl<Holder> implements HolderService {


    @Autowired(required = true)
    protected HolderDAO holderDAO;
    
	@Override
	public Holder find(Context context, UUID id) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateLastModified(Context context, Holder dso) throws SQLException, AuthorizeException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(Context context, Holder dso) throws SQLException, AuthorizeException, IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getSupportsTypeConstant() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Holder findByIdOrLegacyId(Context context, String id) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Holder findByLegacyId(Context context, int id) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Holder> findAll(Context context) throws SQLException {
		return holderDAO.findAll(context);
	}



}
