package org.springframework.social.patreon.connect;

import org.springframework.social.oauth2.AbstractOAuth2ServiceProvider;
import org.springframework.social.oauth2.OAuth2Template;
import org.springframework.social.patreon.api.PatreonClient;
import org.springframework.social.patreon.api.impl.PatreonTemplate;

public class PatreonServiceProvider extends AbstractOAuth2ServiceProvider<PatreonClient> {
	
	private static final String BASE_URL="https://www.patreon.com";
	private static final String AUTHORIZE_URL="/oauth2/authorize";
	private static final String ACCESS_TOKEN_URL="/api/oauth2/token";

	public PatreonServiceProvider(String clientId, String clientSecret) {
		super(new OAuth2Template(clientId, clientSecret,
				BASE_URL+AUTHORIZE_URL, 
				BASE_URL+ACCESS_TOKEN_URL));
	}

	@Override
	public PatreonClient getApi(String accessToken) {
		return new PatreonTemplate(accessToken);
	}

}
