package org.springframework.social.patreon.api;

import java.util.List;

public class Identity {
	
	String id;
	User user;
	List<Campaign> campaigns;
	List<Tier> tiers;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
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
