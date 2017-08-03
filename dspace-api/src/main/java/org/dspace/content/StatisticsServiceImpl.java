package org.dspace.content;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.SolrPingResponse;
import org.dspace.authorize.AuthorizeException;
import org.dspace.content.dao.ItemDAO;
import org.dspace.content.dao.MetadataValueDAO;
import org.dspace.core.Context;
import org.dspace.handle.Handle;
import org.dspace.handle.dao.HandleDAO;
import org.dspace.services.ConfigurationService;
import org.dspace.statistics.ObjectCount;
import org.dspace.statistics.service.SolrLoggerService;
import org.springframework.beans.factory.annotation.Autowired;

public class StatisticsServiceImpl extends DSpaceObjectServiceImpl<Holder> implements StatisticsService {

	@Autowired(required = true)
	protected ItemDAO itemDao;

	@Autowired(required = true)
	protected MetadataValueDAO metadataDao;

	@Autowired(required = true)
	private ConfigurationService configurationService;

	@Autowired(required = true)
	private SolrLoggerService solrLoggerService;

	@Autowired(required = true)
	private HandleDAO handleDao;

	@Autowired(required = true)
	private MetadataValueDAO metadataValueDao;

	private static Logger log = Logger.getLogger(StatisticsServiceImpl.class);

	@Override
	public List<Holder> findAll(Context context) throws SQLException {

		List<String> emails = new ArrayList<String>();
		List<Holder> holders = new ArrayList<Holder>();
		List<Item> items = itemDao.findAll(context);

		for (Item item : items) {

			if (item.getSubmitter() != null && item.getSubmitter().getEmail() != null
					&& !emails.contains(item.getSubmitter().getEmail())) {

				Holder holder = new Holder();

				holder.setCorreo(item.getSubmitter().getEmail());
				List<Item> itemsBySubmitter = itemDao.findBySubmitter(context, item.getSubmitter().getID());

				for (Item item2 : itemsBySubmitter) {

					if (holder.getNombre() == null && holder.getNumTel() == null && holder.getpApellido() == null) {

						List<MetadataValue> metadata = metadataValueDao.findByDspaceObject(context, item2.id);

						for (MetadataValue meta : metadata) {
							if (meta.getMetadataField().getID().equals(new Integer(74)))
								holder.setNombre(meta.getValue());
							if (meta.getMetadataField().getID().equals(new Integer(75))) {
								String[] apellidos = StringUtils.split(meta.getValue(), "");
								if (apellidos.length > 0)
									holder.setpApellido(apellidos[0]);
								if (apellidos.length > 1)
									holder.setsApellido(apellidos[1]);
							}
							if (meta.getMetadataField().getID().equals(new Integer(76)))
								holder.setNumTel(meta.getValue());
						}

					}
				}

				holders.add(holder);
				emails.add(item.getSubmitter().getEmail());

			}
		}

		return holders;

	}

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
	public Community findByIdOrLegacyId(Context context, String id) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Community findByLegacyId(Context context, int id) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public void ping() {
		log.info("solr-statistics.spidersfile:" + configurationService.getProperty("solr-statistics.spidersfile"));
		log.info("solr-statistics.server:" + configurationService.getProperty("solr-statistics.server"));
		log.info("usage-statistics.dbfile:" + configurationService.getProperty("usage-statistics.dbfile"));
		log.info("usage-statistics.dbfile:" + configurationService.getProperty("usage-statistics.dbfile"));
		log.info("padron.authorization.usuario:" + configurationService.getProperty("padron.authorization.usuario"));

		HttpSolrServer server = null;

		if (configurationService.getProperty("solr-statistics.server") != null) {
			try {
				server = new HttpSolrServer(configurationService.getProperty("solr-statistics.server"));
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}
		}

		try {
			SolrPingResponse ping = server.ping();
			log.info("Ping of Solr Core [%s] Returned with Status [%d]" + ping.getStatus());

		} catch (Exception e) {
			log.info("Ping of Solr Core [%s] Failed with [%s].  New Core Will be Created" + e.getClass().getName());
		}
	}

	@Override
	public ObjectCount[] viewItemsStatistics(Context context) throws SolrServerException, SQLException {

		String url = configurationService.getProperty("dspace.baseUrl");

		ObjectCount[] items = solrLoggerService.queryFacetField("type:2", "statistics_type:view", "uid", 500000, true,
				null);

		for (ObjectCount objectCount : items) {
			List<Handle> handles = handleDao.findByItemId(context, objectCount.getValue());
			objectCount.setValue(url + "/handle/" + handles.get(0));
		}

		return items;
	}

	@Override
	public ObjectCount[] downloadItemsStatistics() throws SolrServerException {
		return solrLoggerService.queryFacetField("type:0", "statistics_type:view", "uid", 500000, true, null);
	}

}
