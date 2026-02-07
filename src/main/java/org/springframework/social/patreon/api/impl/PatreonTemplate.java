package org.springframework.social.patreon.api.impl;

import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.social.oauth2.AbstractOAuth2ApiBinding;
import org.springframework.social.patreon.api.Campaign;
import org.springframework.social.patreon.api.Campaigns;
import org.springframework.social.patreon.api.Identity;
import org.springframework.social.patreon.api.PatreonClient;
import org.springframework.social.patreon.api.PatreonResourceV2;
import org.springframework.social.patreon.api.Tier;
import org.springframework.social.patreon.api.User;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;

public class PatreonTemplate extends AbstractOAuth2ApiBinding implements PatreonClient {
	
	private static final String BASE_URL="https://www.patreon.com";
	private static final String IDENTITY_URL = "/api/oauth2/v2/identity";
	private static final String CAMPAIGNS_URL = "/api/oauth2/v2/campaigns";
	
	private static final String ID = "id";
	private static final String ATTRIBUTES = "attributes";
	private static final String TYPE = "type";

	
	private final ObjectMapper mapper = new ObjectMapper();

	public PatreonTemplate(String accessToken) {
		super(accessToken);
	}
	
	public Identity getIdentity() {
		URI uri = UriComponentsBuilder.fromUriString(BASE_URL+IDENTITY_URL)
			.queryParam("include","campaign,memberships.currently_entitled_tiers")
			.queryParam("fields[campaign]","summary,vanity")
			.queryParam("fields[tier]", "title,url")
			.queryParam("fields[user]", "email,first_name,last_name,full_name,image_url,thumb_url,url")
			.build().toUri();
		
		ResponseEntity<IdentityResponse> response = getRestTemplate().exchange(uri, HttpMethod.GET, new HttpEntity<String>((String)null), IdentityResponse.class);
		
		Identity identity = new Identity();
				
		Map<String, Object> data = response.getBody().getData();
		identity.setId(data.get(ID).toString());
		identity.setUser(List.of(data).stream()
				.filter(m->m.containsKey(ATTRIBUTES))
				.map(m->convertToResource(m, User.class))
				.findFirst().orElseThrow());
		
		List<Map<String, Object>> included = response.getBody().getIncluded();
		identity.setCampaigns(included.stream()
				.filter(m->"campaign".equals(m.get(TYPE)))
				.filter(m->m.containsKey(ATTRIBUTES))
				.map(m->convertToResource(m, Campaign.class))
				.collect(Collectors.toList()));	
		
		identity.setTiers(included.stream()
				.filter(m->"tier".equals(m.get(TYPE)))
				.filter(m->m.containsKey(ATTRIBUTES))
				.map(m->convertToResource(m, Tier.class))
				.collect(Collectors.toList()));
		
		return identity;
	}
	
	public Campaigns getCampaigns() {
		URI uri = UriComponentsBuilder.fromUriString(BASE_URL+CAMPAIGNS_URL)
				.queryParam("include","tiers")
				.queryParam("fields[tier]", "title,url")
				.queryParam("fields[campaign]", "summary,vanity")
				.build().toUri();
		
		ResponseEntity<CampaignsResponse> response = getRestTemplate().exchange(uri, HttpMethod.GET, new HttpEntity<String>((String)null), CampaignsResponse.class);
				
		Campaigns campaigns = new Campaigns();
		
		List<Map<String, Object>> data = response.getBody().getData();
		campaigns.setCampaigns(data.stream()
			.filter(m->m.containsKey(ATTRIBUTES))
			.map(m->convertToResource(m, Campaign.class))
			.collect(Collectors.toList()));
		
		
		List<Map<String, Object>> included = response.getBody().getIncluded();
		campaigns.setTiers(included.stream()
			.filter(m->"tier".equals(m.get(TYPE)))
			.filter(m->m.containsKey(ATTRIBUTES))
			.map(m->convertToResource(m, Tier.class))
			.collect(Collectors.toList()));

		return campaigns;
	}
	
	/**
	 * Extracts a Resource from a top-level map.  Top-level maps should include
	 * attributes, id, and type.
	 * 
	 * @param <R>
	 * @param map
	 * @param resourceType
	 * @return
	 */
	protected <R extends PatreonResourceV2> R convertToResource(Map<String, Object> map, Class<R> resourceType) {
		R resource = mapper.convertValue(map.get(ATTRIBUTES), resourceType);
		resource.setId(map.get(ID).toString());
		return resource;
	}

	
	public static class IdentityResponse {
		
		private Map<String, Object> data;
		private List<Map<String, Object>> included;

		public Map<String, Object> getData() {
			return data;
		}

		public void setData(Map<String, Object> data) {
			this.data = data;
		}

		public List<Map<String, Object>> getIncluded() {
			return included;
		}

		public void setIncluded(List<Map<String, Object>> included) {
			this.included = included;
		}

		
	}
	
	public static class CampaignsResponse {
		
		private List<Map<String, Object>> data;
		private List<Map<String, Object>> included;

		public List<Map<String, Object>> getData() {
			return data;
		}

		public void setData(List<Map<String, Object>> data) {
			this.data = data;
		}

		public List<Map<String, Object>> getIncluded() {
			return included;
		}

		public void setIncluded(List<Map<String, Object>> included) {
			this.included = included;
		}

		
	}

}
