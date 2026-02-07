package org.springframework.social.patreon.api;

/**
 * See https://docs.patreon.com/#apiv2-resources
 * 
 * @author apfesta
 *
 */
public abstract class PatreonResourceV2 {
	
	String id;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
}
