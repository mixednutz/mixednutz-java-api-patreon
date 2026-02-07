package net.mixednutz.api.patreon.client;

import org.springframework.social.connect.Connection;
import org.springframework.social.patreon.api.PatreonClient;

import net.mixednutz.api.client.GroupClient;
import net.mixednutz.api.client.MixednutzClient;
import net.mixednutz.api.client.TimelineClient;
import net.mixednutz.api.client.UserClient;

public class PatreonAdapter implements MixednutzClient {
	
	private final Connection<PatreonClient> conn;
	private PostAdapter postAdapter;
	private TierAdapter tierAdapter;
	
	public PatreonAdapter(Connection<PatreonClient> conn) {
		super();
		this.conn = conn;
		initSubApis();
	}

	@Override
	public GroupClient getGroupClient() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TimelineClient<?> getTimelineClient() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UserClient<?> getUserClient() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PostAdapter getPostClient() {
		return postAdapter;
	}
	
	@Override
	public TierAdapter getRoleClient() {
		return tierAdapter;
	}
	
	private void initSubApis() {
		postAdapter = new PostAdapter(conn);
		tierAdapter = new TierAdapter(conn);
	}

	
}
