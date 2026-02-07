package net.mixednutz.api.patreon.provider;

import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionData;
import org.springframework.social.patreon.api.PatreonClient;
import org.springframework.social.patreon.connect.PatreonConnectionFactory;

import net.mixednutz.api.core.provider.AbstractApiProvider;
import net.mixednutz.api.model.INetworkInfoSmall;
import net.mixednutz.api.patreon.PatreonFeedType;
import net.mixednutz.api.patreon.client.PatreonAdapter;
import net.mixednutz.api.provider.IOauth2Credentials;

public class PatreonProvider extends AbstractApiProvider<PatreonAdapter, IOauth2Credentials>{
	
	private PatreonConnectionFactory connectionFactory;

	public PatreonProvider(PatreonConnectionFactory connectionFactory) {
		super(PatreonAdapter.class, IOauth2Credentials.class);
		this.connectionFactory = connectionFactory;
	}
	
	@Override
	public PatreonAdapter getApi(IOauth2Credentials creds) {
		return new PatreonAdapter(createConnection(createConnectionData(creds)));
	}
	
	@Override
	public INetworkInfoSmall getNetworkInfo() {
		return new PatreonFeedType();
	}

	@Override
	public String getProviderId() {
		return connectionFactory.getProviderId();
	}
	
	protected Connection<PatreonClient> createConnection(ConnectionData cd) {
		return connectionFactory.createConnection(cd);
	}
	
	protected ConnectionData createConnectionData(IOauth2Credentials creds) {
		return new ConnectionData(creds.getProviderId(), null, null, null, null, 
				creds.getAuthCode(), null, creds.getRefreshToken(), creds.getExpireTime());
	}

}
