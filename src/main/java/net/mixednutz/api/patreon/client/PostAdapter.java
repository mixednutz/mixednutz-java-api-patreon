package net.mixednutz.api.patreon.client;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.social.connect.Connection;
import org.springframework.social.patreon.api.PatreonClient;

import net.mixednutz.api.client.PostClient;
import net.mixednutz.api.model.ITimelineElement;
import net.mixednutz.api.patreon.model.PatreonTier;
import net.mixednutz.api.patreon.model.PostForm;

public class PostAdapter implements PostClient<PostForm>{
	
	Connection<PatreonClient> conn;

	public PostAdapter(Connection<PatreonClient> conn) {
		super();
		this.conn = conn;
	}

	@Override
	public ITimelineElement postToTimeline(PostForm post) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PostForm create() {
		/*
		 * Patreon is read-only
		 * 
		 * The new idea is leave evrything as is and create a "job" for general release
		 * 
		 * make sure you configure post to take in the tier-id
		 * 
		 * make sure the user has roles from the tier.
		 * make sure post respects that role.
		 */
		
		return null;
	}

	@Override
	public Map<String, Object> referenceDataForPosting() {
		return Collections.singletonMap("externalLists", getTiers());
	}
	
	private boolean hasCampaigns() {
		return !conn.getApi().getIdentity().getCampaigns().isEmpty();
	}
	
	private List<PatreonTier> getTiers() {
		if (!hasCampaigns()) {
			return Collections.emptyList();
		}
		
		return conn.getApi().getCampaigns().getTiers().stream()
				.map(PatreonTier::new).collect(Collectors.toList());
	}

	@Override
	public boolean canPost() {
		return false;
	}

}
