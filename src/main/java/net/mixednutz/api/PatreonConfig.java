package net.mixednutz.api;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.social.connect.web.CredentialsCallback;
import org.springframework.social.connect.web.CredentialsInterceptor;
import org.springframework.social.patreon.api.PatreonClient;
import org.springframework.social.patreon.connect.PatreonConnectionFactory;

import net.mixednutz.api.patreon.PatreonConnectionProperties;
import net.mixednutz.api.patreon.provider.PatreonProvider;
import net.mixednutz.api.provider.IOauth2Credentials;

@Profile("patreon")
@Configuration
@EnableConfigurationProperties(PatreonConnectionProperties.class)
@ComponentScan("net.mixednutz.patreon")
public class PatreonConfig {
	
	@Bean
	PatreonConnectionFactory patreonConnectionFactory(PatreonConnectionProperties patreonConnectionProperties) {
		return new PatreonConnectionFactory(patreonConnectionProperties.getClientId(), 
				patreonConnectionProperties.getClientSecret());
	}
	@Bean
	PatreonProvider PatreonProvider(PatreonConnectionFactory patreonConnectionFactory) {
		return new PatreonProvider(patreonConnectionFactory);
	}
	
	@Bean
	public CredentialsInterceptor<PatreonClient, IOauth2Credentials> patreonCredentialsInterceptor(CredentialsCallback callback) {
		return new PatreonCredentialsInterceptor(callback);
	}
	
//	@Bean
//	public PatreonExternalListVisibilityProvider patreonExternalListVisibilityProvider() {
//		return new PatreonExternalListVisibilityProvider();
//	}
	
	public static class PatreonCredentialsInterceptor extends CredentialsInterceptor<PatreonClient, IOauth2Credentials> {

		public PatreonCredentialsInterceptor(CredentialsCallback callback) {
			super(PatreonClient.class, IOauth2Credentials.class, callback);
		}
		
	}

}
