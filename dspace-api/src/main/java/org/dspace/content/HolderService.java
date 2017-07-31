package org.dspace.content;

import java.sql.SQLException;
import java.util.List;

import org.dspace.content.service.DSpaceObjectLegacySupportService;
import org.dspace.content.service.DSpaceObjectService;
import org.dspace.core.Context;

public interface HolderService extends DSpaceObjectService<Holder>, DSpaceObjectLegacySupportService<Community>
{

	List<Holder> findAll(Context context) throws SQLException;

	void ping();

}
