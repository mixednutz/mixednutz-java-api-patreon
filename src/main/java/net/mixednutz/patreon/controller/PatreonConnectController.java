package net.mixednutz.patreon.controller;

import java.util.Collections;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.GenericTypeResolver;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionFactory;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.connect.web.ConnectInterceptor;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.social.oauth2.OAuth2Operations;
import org.springframework.social.patreon.connect.PatreonConnectionFactory;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.WebRequest;

/**
 * I don't think we need this class because the Oauth2 stuff works out of the box
 */
//@Controller
public class PatreonConnectController {
	
	private final PatreonConnectionFactory connectionFactory;
	
	private final ConnectionRepository connectionRepository;
	
	private final MultiValueMap<Class<?>, ConnectInterceptor<?>> connectInterceptors = new LinkedMultiValueMap<Class<?>, ConnectInterceptor<?>>();
	
	@Autowired
	private List<ConnectInterceptor<?>> autowiredConnectInterceptors;

	@Autowired
	public PatreonConnectController(PatreonConnectionFactory connectionFactory,
			ConnectionRepository connectionRepository) {
		super();
		this.connectionFactory = connectionFactory;
		this.connectionRepository = connectionRepository;
	}
	
	public void addInterceptor(ConnectInterceptor<?> interceptor) {
		Class<?> serviceApiType = GenericTypeResolver.resolveTypeArgument(interceptor.getClass(), ConnectInterceptor.class);
		connectInterceptors.add(serviceApiType, interceptor);
	}

	@RequestMapping(value="/patreon", method=RequestMethod.POST)
	public String connect(NativeWebRequest request) {
		MultiValueMap<String, String> parameters = new LinkedMultiValueMap<String, String>(); 
		preConnect(connectionFactory, parameters, request);
		
		OAuth2Operations oauth = connectionFactory.getOAuthOperations();
		
		String code = request.getParameter("code");
		String redirectUrl = request.getNativeRequest(HttpServletRequest.class).getRequestURL().toString();
		try {
			AccessGrant grant = oauth.exchangeForAccess(code, redirectUrl, null);
			Connection<?> connection = connectionFactory.createConnection(grant);
			addConnection(connection, connectionFactory, request);
		} catch (HttpClientErrorException e) {
			e.printStackTrace();
		}
		
		return "redirect:/";
	}
	
	@PostConstruct
	public void addInterceptors() {
		for (ConnectInterceptor<?> connectInterceptor: this.autowiredConnectInterceptors) {
			this.addInterceptor(connectInterceptor);
		}
	}
	
	private void addConnection(Connection<?> connection, ConnectionFactory<?> connectionFactory, WebRequest request) {
		connectionRepository.addConnection(connection);
		postConnect(connectionFactory, connection, request);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void preConnect(ConnectionFactory<?> connectionFactory, MultiValueMap<String, String> parameters, WebRequest request) {
		for (ConnectInterceptor interceptor : interceptingConnectionsTo(connectionFactory)) {
			interceptor.preConnect(connectionFactory, parameters, request);
		}
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void postConnect(ConnectionFactory<?> connectionFactory, Connection<?> connection, WebRequest request) {
		for (ConnectInterceptor interceptor : interceptingConnectionsTo(connectionFactory)) {
			interceptor.postConnect(connection, request);
		}
	}

	private List<ConnectInterceptor<?>> interceptingConnectionsTo(ConnectionFactory<?> connectionFactory) {
		Class<?> serviceType = GenericTypeResolver.resolveTypeArgument(connectionFactory.getClass(), ConnectionFactory.class);
		List<ConnectInterceptor<?>> typedInterceptors = connectInterceptors.get(serviceType);
		if (typedInterceptors == null) {
			typedInterceptors = Collections.emptyList();
		}
		return typedInterceptors;
	}

}
