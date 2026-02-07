package org.springframework.social.patreon.api;

import java.util.List;

public class Campaigns {

	List<Campaign> campaigns;
	List<Tier> tiers;
	
	public List<Campaign> getCampaigns() {
		return campaigns;
	}
	public void setCampaigns(List<Campaign> campaigns) {
		this.campaigns = campaigns;
	}
	public List<Tier> getTiers() {
		return tiers;
	}
	public void setTiers(List<Tier> tiers) {
		this.tiers = tiers;
	}
	
}
