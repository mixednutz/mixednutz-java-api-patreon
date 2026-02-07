package net.mixednutz.api.patreon.client;

import java.util.Collections;
import java.util.List;

import org.springframework.social.connect.Connection;
import org.springframework.social.patreon.api.PatreonClient;

import net.mixednutz.api.client.RoleClient;
import net.mixednutz.api.patreon.model.PatreonTier;

public class TierAdapter implements RoleClient {
	
	Connection<PatreonClient> conn;

	public TierAdapter(Connection<PatreonClient> conn) {
		super();
		this.conn = conn;
	}

	@Override
	public boolean hasRoles() {
		return true;
	}
	
	private boolean hasCampaigns() {
		return !conn.getApi().getIdentity().getCampaigns().isEmpty();
	}

	@Override
	public List<PatreonTier> getAvailableRoles() {
		if (!hasCampaigns()) {
			return Collections.emptyList();
		}
		
		return conn.getApi().getCampaigns().getTiers().stream()
				.map(PatreonTier::new).toList();
	}
	
	public List<PatreonTier> getRolesAssigned() {
		return conn.getApi().getIdentity().getTiers().stream()
				.map(PatreonTier::new).toList();
	}
	
}
