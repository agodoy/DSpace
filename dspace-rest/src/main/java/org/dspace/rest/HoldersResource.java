/**
 * The contents of this file are subject to the license and copyright
 * detailed in the LICENSE and NOTICE files at the root of the source
 * tree and available online at
 *
 * http://www.dspace.org/license/
 */
package org.dspace.rest;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;
import org.dspace.authorize.AuthorizeException;
import org.dspace.authorize.factory.AuthorizeServiceFactory;
import org.dspace.authorize.service.AuthorizeService;
import org.dspace.content.Holder;
import org.dspace.content.HolderService;
import org.dspace.content.factory.ContentServiceFactory;
import org.dspace.content.service.CollectionService;
import org.dspace.content.service.InstallItemService;
import org.dspace.content.service.ItemService;
import org.dspace.content.service.WorkspaceItemService;
import org.dspace.core.Constants;
import org.dspace.core.LogManager;
import org.dspace.rest.common.Collection;
import org.dspace.rest.common.Item;
import org.dspace.rest.common.MetadataEntry;
import org.dspace.rest.exceptions.ContextException;
import org.dspace.usage.UsageEvent;
import org.dspace.workflow.WorkflowService;
import org.dspace.workflow.factory.WorkflowServiceFactory;

/**
 * This class provides all CRUD operation over holders.
 * 
 * @author agodoy
 */
@Path("/holders")
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
    public org.dspace.rest.common.Holder[] getHolders()
            throws WebApplicationException
    {

        log.info("inicia el servicio");
        org.dspace.core.Context context = null;
        List<Holder> holders = new ArrayList<Holder>();

        try
        {
            context = createContext();

//            log.trace("creacion contexto");
//            if (!((limit != null) && (limit >= 0) && (offset != null) && (offset >= 0)))
//            {
//                log.warn("Paging was badly set.");
//                limit = 100;
//                offset = 0;
//            }

            holders = holdersService.findAll(context);
            log.info("holdersService.findAll");
////            for(org.dspace.content.Holder holder : dspaceHolders)
////            {
////                if (authorizeService.authorizeActionBoolean(context, dspaceCollection, org.dspace.core.Constants.READ))
////                {
////                    Collection collection = new org.dspace.rest.common.Collection(dspaceCollection, servletContext, null, context, limit,
////                            offset);
////                    collections.add(collection);
////                    writeStats(dspaceCollection, UsageEvent.Action.VIEW, user_ip, user_agent,
////                            xforwardedfor, headers, request, context);
////                }
////            }
            context.complete();
            log.info("context.complete()");
        }
        catch (SQLException e)
        {
            processException("Something went wrong while reading collections from database. Message: " + e, context);
        }
        catch (ContextException e)
        {
            processException("Something went wrong while reading collections, ContextError. Message: " + e.getMessage(), context);
        }
        finally
        {
            log.info("finally");
            processFinally(context);
        }

        log.info("All holders were successfully read.");
        return holders.toArray(new org.dspace.rest.common.Holder[0]);
    }

}
