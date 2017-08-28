package org.dspace.authority.curp;

import java.net.MalformedURLException;
import java.util.Date;
import java.util.UUID;

import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.dspace.authority.AuthoritySearchService;
import org.dspace.authority.AuthorityTypes;
import org.dspace.authority.indexer.AuthorityIndexingService;
import org.dspace.authority.orcid.CurpAuthorityValue;
import org.dspace.services.ConfigurationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class CurpServiceImpl implements CurpService {

	@Autowired(required = true)
	private AuthoritySearchService solrService;

	@Autowired(required = true)
	private AuthorityIndexingService indexingService;

	@Autowired(required = true)
	protected AuthorityTypes authorityTypes;

	@Autowired(required = true)
	private ConfigurationService configurationService;

	private static Logger log = Logger.getLogger(CurpServiceImpl.class);

	@Override
	public void indexCurps() {

		String credentials = configurationService.getProperty("curp.authorization.credentials");
		String curpIdFromValue = configurationService.getProperty("curp.curp_id_from");
		String curpIdToValue = configurationService.getProperty("curp.curp_id_to");

		log.info("se obtuvo la configuracion curp.authorization.credentials=" + credentials + " curp.curp_id_from= "
				+ curpIdFromValue + " curp.curp_id_to=" + curpIdToValue);

		Integer curpIdFrom = curpIdFromValue != null && !curpIdFromValue.isEmpty() ? Integer.parseInt(curpIdFromValue)
				: 0;
		Integer curpIdTo = curpIdToValue != null && !curpIdToValue.isEmpty() ? Integer.parseInt(curpIdToValue) : 999999;

		RestTemplate restTemplate = new RestTemplate();

		byte[] plainCredsBytes = credentials != null && !credentials.isEmpty() ? credentials.getBytes()
				: "imta:imTAt05_06".getBytes();
		byte[] base64CredsBytes = Base64.encodeBase64(plainCredsBytes);
		String base64Creds = new String(base64CredsBytes);

		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "Basic " + base64Creds);

		HttpEntity<String> request = new HttpEntity<String>(headers);

		for (int i = curpIdFrom; i < curpIdTo; i++)

			try {
				String url = "http://catalogs.repositorionacionalcti.mx/webresources/persona/" + i;
				ResponseEntity<CurpData> response = restTemplate.exchange(url, HttpMethod.GET, request, CurpData.class);
				CurpData curp = response.getBody();

				if (curp != null && curp.getCurp() != null) {
					log.info("Se va a indexar el CURP: " + curp.getCurp() + " Nombre: " + curp.getNombres()
							+ " Apellido: " + curp.getPrimerApellido() + " ID: " + i);
					System.out.println("Se va a indexar el CURP: " + curp.getCurp() + " Nombre: " + curp.getNombres()
							+ " Apellido: " + curp.getPrimerApellido() + " ID: " + i);

					indexCurpData(curp);
				}
			} catch (Exception e) {
				System.out.println("Ocurrió un error al procesar los CURP");
				e.printStackTrace();
			}

	}

	private void indexCurpData(CurpData curpData) throws MalformedURLException, SolrServerException {

		CurpAuthorityValue curpAuth = new CurpAuthorityValue();

		curpAuth.setId(UUID.randomUUID().toString());
		curpAuth.setCurp_id(curpData.getCurp());
		curpAuth.setCreationDate(new Date());
		curpAuth.setLastModified(new Date());
		curpAuth.setFirstName(curpData.getNombres());
		curpAuth.setLastName(curpData.getPrimerApellido());
		curpAuth.setField("dc_contributor_author");

		SolrQuery solrQuery = new SolrQuery();
		solrQuery.setQuery("curp_id:" + curpData.getCurp());
		QueryResponse response = solrService.search(solrQuery);

		if (response.getResults().isEmpty())
			indexingService.indexContent(curpAuth, true);

		indexingService.commit();

	}

}
