package org.dspace.content.dao;

import java.sql.SQLException;
import java.util.List;

import org.dspace.content.Holder;
import org.dspace.core.Context;

public interface HolderDAO extends DSpaceObjectLegacySupportDAO<Holder> {

    public List<Holder> findAll(Context context) throws SQLException;

}
