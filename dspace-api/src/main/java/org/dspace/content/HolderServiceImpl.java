package org.dspace.content;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.dspace.authorize.AuthorizeException;
import org.dspace.content.dao.HolderDAO;
import org.dspace.content.dao.ItemDAO;
import org.dspace.content.dao.MetadataValueDAO;
import org.dspace.core.Context;
import org.springframework.beans.factory.annotation.Autowired;

public class HolderServiceImpl extends DSpaceObjectServiceImpl<Holder> implements HolderService {

	@Autowired(required = true)
	protected HolderDAO holderDAO;

	@Autowired(required = true)
	protected ItemDAO itemDao;

	@Autowired(required = true)
	protected MetadataValueDAO metadataDao;

	@Override
	public List<Holder> findAll(Context context) throws SQLException {

		List<Holder> holders = new ArrayList<Holder>();

		List<Item> items = itemDao.findAll(context);

		for (Item item : items) {

			Holder holder = new Holder();

			if (item.getSubmitter() != null)
				holder.setCorreo(item.getSubmitter().getEmail());

			if (item.getMetadata() != null) {
				for (MetadataValue meta : item.getMetadata()) {
					if (meta.getMetadataField().getID() == 1)
						holder.setNombre(meta.getValue());
					if (meta.getMetadataField().getID() == 2) {
						String[] apellidos = StringUtils.split(meta.getValue(), "");
						if(apellidos.length>0)
							holder.setpApellido(apellidos[0]);
						if(apellidos.length>1)
							holder.setsApellido(apellidos[1]);
					}
					if (meta.getMetadataField().getID() == 3)
						holder.setNumTel(meta.getValue());
				}
			}

			holders.add(holder);
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

}
