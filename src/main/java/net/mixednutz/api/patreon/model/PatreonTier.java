package net.mixednutz.api.patreon.model;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.social.patreon.api.Tier;

import net.mixednutz.api.model.IExternalRole;

public class PatreonTier implements IExternalRole{
	
	private static final Logger LOG = LoggerFactory.getLogger(PatreonTier.class);
	
	private static final Pattern VANITY_FROM_URL = Pattern.compile("\\/checkout\\/(?<vanity>[a-zA-Z0-9_]*)\\?.*");
	
	private String id;
	private String name;
	private final String providerId = "patreon";
	private String url;
	
	public PatreonTier(Tier tier) {
		super();
		this.id = tier.getId();
		this.name = tier.getTitle();
		this.url = getProviderUrl(tier.getUrl());
	}
	static String getProviderUrl(String tierUrl) {
		try {
			Matcher matcher = VANITY_FROM_URL.matcher(tierUrl);
			if (matcher.matches()) {
				String vanity = matcher.group("vanity");
				return vanity+"/join";
			}
		} catch (Exception e) {	
			LOG.warn("Unable to parse URL {} with regex",tierUrl, e);
		}
		return tierUrl;	
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getProviderId() {
		return providerId;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
		
}
