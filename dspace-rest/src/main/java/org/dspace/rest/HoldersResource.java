/**
 * The contents of this file are subject to the license and copyright
 * detailed in the LICENSE and NOTICE files at the root of the source
 * tree and available online at
 *
 * http://www.dspace.org/license/
 */
package org.dspace.rest;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;
import org.dspace.authorize.factory.AuthorizeServiceFactory;
import org.dspace.authorize.service.AuthorizeService;
import org.dspace.content.HolderService;
import org.dspace.content.factory.ContentServiceFactory;
import org.dspace.content.service.ItemService;
import org.dspace.content.service.WorkspaceItemService;
import org.dspace.rest.common.Holder;
import org.dspace.rest.exceptions.ContextException;

/**
 * This class provides all CRUD operation over holders.
 * 
 * @author agodoy
 */
@Path("/padron")
public class HoldersResource extends Resource
{
    protected HolderService holdersService = ContentServiceFactory.getInstance().getHolderService();
    protected ItemService itemService = ContentServiceFactory.getInstance().getItemService();
    protected AuthorizeService authorizeService = AuthorizeServiceFactory.getInstance().getAuthorizeService();
    protected WorkspaceItemService workspaceItemService = ContentServiceFactory.getInstance().getWorkspaceItemService();
    
    private static Logger log = Logger.getLogger(HoldersResource.class);


    /**
     * Return array of all holders in DSpace. You can add more properties
     * through expand parameter.
     * 
     * @return Return array of collection, on which has logged user permission
     *         to view.
     * @throws WebApplicationException
     *             It is thrown when was problem with database reading
     *             (SQLException) or problem with creating
     *             context(ContextException).
     */
    @GET
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public List<Holder> getHolders() throws WebApplicationException{
        org.dspace.core.Context context = null;
        List<org.dspace.content.Holder> holders = null;
        List<Holder> holdersResult = new ArrayList<Holder>();

        try
        {
            context = createContext();

            holders = holdersService.findAll(context);
            
            for (org.dspace.content.Holder holder : holders) {
				Holder h = new Holder();
				h.setCorreo(holder.getCorreo());
				h.setNombre(holder.getNombre());
				h.setNumTel(holder.getNumTel());
				h.setpApellido(holder.getpApellido());
				h.setsApellido(holder.getsApellido());
				holdersResult.add(h);
			}
            
            context.complete();
        }
        catch (SQLException e)
        {
            processException("Something went wrong while reading holders from database. Message: " + e, context);
        }
        catch (ContextException e)
        {
            processException("Something went wrong while reading holders, ContextError. Message: " + e.getMessage(), context);
        }
        finally
        {
            processFinally(context);
        }

        log.info("All holders were successfully read.");
        return holdersResult;
    }

}
