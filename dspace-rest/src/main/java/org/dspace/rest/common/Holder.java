/**
 * The contents of this file are subject to the license and copyright
 * detailed in the LICENSE and NOTICE files at the root of the source
 * tree and available online at
 *
 * http://www.dspace.org/license/
 */
package org.dspace.rest.common;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.ServletContext;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.log4j.Logger;
import org.dspace.content.Bundle;
import org.dspace.content.factory.ContentServiceFactory;
import org.dspace.content.service.BitstreamService;
import org.dspace.content.service.BundleService;
import org.dspace.core.Constants;
import org.dspace.core.Context;
import org.dspace.utils.DSpace;

@XmlRootElement(name = "holder")
public class Holder extends DSpaceObject {
    protected BitstreamService bitstreamService = ContentServiceFactory.getInstance().getBitstreamService();
    protected BundleService bundleService = ContentServiceFactory.getInstance().getBundleService();

    Logger log = Logger.getLogger(Holder.class);

    private String email;
    private String nickname;
    private Long uploadedQty;
    
    public Holder() {

    }

    public Holder(Holder holder, ServletContext servletContext, String expand, Context context) throws SQLException{
//        super(holder, servletContext);
        setup(holder, servletContext, expand, context);
    }

    public void setup(Holder holder, ServletContext servletContext, String expand, Context context) throws SQLException{
//        List<String> expandFields = new ArrayList<String>();
//        if(expand != null) {
//            expandFields = Arrays.asList(expand.split(","));
//        }
//
//        //A logo bitstream might not have a bundle...
//        if(bitstream.getBundles() != null & bitstream.getBundles().size() >= 0) {
//            if(bitstreamService.getParentObject(context, bitstream).getType() == Constants.ITEM) {
//                bundleName = bitstream.getBundles().get(0).getName();
//            }
//        }
//
//        description = bitstream.getDescription();
//        format = bitstreamService.getFormatDescription(context, bitstream);
//        sizeBytes = bitstream.getSize();
//        String path = new DSpace().getRequestService().getCurrentRequest().getHttpServletRequest().getContextPath();
//        retrieveLink = path + "/bitstreams/" + bitstream.getID() + "/retrieve";
//        mimeType = bitstreamService.getFormat(context, bitstream).getMIMEType();
//        sequenceId = bitstream.getSequenceID();
//        CheckSum checkSum = new CheckSum();
//        checkSum.setCheckSumAlgorith(bitstream.getChecksumAlgorithm());
//        checkSum.setValue(bitstream.getChecksum());
//        this.setCheckSum(checkSum);
//
//        if(expandFields.contains("parent") || expandFields.contains("all")) {
//            parentObject = new DSpaceObject(bitstreamService.getParentObject(context, bitstream), servletContext);
//        } else {
//            this.addExpand("parent");
//        }
//
//        if(expandFields.contains("policies") || expandFields.contains("all")) {
//            // Find policies without context.
//        	List<ResourcePolicy> tempPolicies = new ArrayList<ResourcePolicy>();
//        	List<Bundle> bundles = bitstream.getBundles();
//			for (Bundle bundle : bundles) {
//				List<org.dspace.authorize.ResourcePolicy> bitstreamsPolicies = bundleService.getBitstreamPolicies(context, bundle);
//				for (org.dspace.authorize.ResourcePolicy policy : bitstreamsPolicies) {
//                    if(policy.getdSpaceObject().equals(bitstream)) {
//                        tempPolicies.add(new ResourcePolicy(policy));
//                    }
//				}
//			}
//			
//			policies = tempPolicies.toArray(new ResourcePolicy[0]);
//        } else {
//            this.addExpand("policies");
//        }
//
//        if(!expandFields.contains("all")) {
//            this.addExpand("all");
//        }
    }

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public Long getUploadedQty() {
		return uploadedQty;
	}

	public void setUploadedQty(Long uploadedQty) {
		this.uploadedQty = uploadedQty;
	}
    
    

}
