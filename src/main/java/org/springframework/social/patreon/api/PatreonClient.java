package org.springframework.social.patreon.api;

public interface PatreonClient {
	
	Identity getIdentity();
	
	Campaigns getCampaigns();

}
