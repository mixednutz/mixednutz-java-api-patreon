package org.springframework.social.patreon.connect;

import org.springframework.social.connect.support.OAuth2ConnectionFactory;
import org.springframework.social.patreon.api.PatreonClient;

public class PatreonConnectionFactory extends OAuth2ConnectionFactory<PatreonClient>{

	public PatreonConnectionFactory(PatreonServiceProvider serviceProvider) {
		super("patreon", serviceProvider, new PatreonApiAdapter());
	}
	
	public PatreonConnectionFactory(String clientId, String clientSecret) {
		this(new PatreonServiceProvider(clientId, clientSecret));
	}

}
