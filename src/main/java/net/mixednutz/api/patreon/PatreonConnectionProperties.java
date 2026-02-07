package net.mixednutz.api.patreon;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix="mixednutz.social.patreon")
public class PatreonConnectionProperties {

	private String clientId;
	private String clientSecret;
	
	public String getClientId() {
		return clientId;
	}
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	public String getClientSecret() {
		return clientSecret;
	}
	public void setClientSecret(String clientSecret) {
		this.clientSecret = clientSecret;
	}
	
}
