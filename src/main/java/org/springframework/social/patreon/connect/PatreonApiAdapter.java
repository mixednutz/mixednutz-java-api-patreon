package org.springframework.social.patreon.connect;

import org.springframework.social.connect.ApiAdapter;
import org.springframework.social.connect.ConnectionValues;
import org.springframework.social.connect.UserProfile;
import org.springframework.social.patreon.api.Campaign;
import org.springframework.social.patreon.api.Identity;
import org.springframework.social.patreon.api.PatreonClient;


public class PatreonApiAdapter implements ApiAdapter<PatreonClient> {

	@Override
	public boolean test(PatreonClient api) {
		try{
			api.getIdentity();
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public void setConnectionValues(PatreonClient api, ConnectionValues values) {
		try {
			Identity identity = api.getIdentity();
			values.setDisplayName(identity.getCampaigns().stream().map(Campaign::getVanity).findFirst().orElse(null));
			values.setProviderUserId(identity.getId());
			values.setImageUrl(identity.getUser().getThumb_url());
			values.setProfileUrl(identity.getUser().getUrl());
		} catch (RuntimeException e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public UserProfile fetchUserProfile(PatreonClient api) {
		Identity identity = api.getIdentity();
		return new UserProfile(null, identity.getUser().getFull_name(), 
				identity.getUser().getFirst_name(), identity.getUser().getLast_name(), 
				identity.getUser().getEmail(), 
				identity.getCampaigns().stream().map(Campaign::getVanity).findFirst().orElse(null));
	}

	@Override
	public void updateStatus(PatreonClient api, String message) {
		// TODO Auto-generated method stub
		
	}

}
