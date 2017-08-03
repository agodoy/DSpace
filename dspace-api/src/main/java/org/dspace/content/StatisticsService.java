package org.dspace.content;

import java.sql.SQLException;
import java.util.List;

import org.apache.solr.client.solrj.SolrServerException;
import org.dspace.content.service.DSpaceObjectLegacySupportService;
import org.dspace.content.service.DSpaceObjectService;
import org.dspace.core.Context;
import org.dspace.statistics.ObjectCount;

public interface StatisticsService extends DSpaceObjectService<Holder>, DSpaceObjectLegacySupportService<Community>
{

	List<Holder> findAll(Context context) throws SQLException;

	ObjectCount[] viewItemsStatistics(Context context) throws SolrServerException, SQLException;

	ObjectCount[] downloadItemsStatistics() throws SolrServerException;

}
