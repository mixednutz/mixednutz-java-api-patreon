package org.springframework.social.patreon.api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown=true)
public class Campaign extends PatreonResourceV2 {

	private String summary;
	private String vanity;
		
	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getVanity() {
		return vanity;
	}

	public void setVanity(String vanity) {
		this.vanity = vanity;
	}

}
