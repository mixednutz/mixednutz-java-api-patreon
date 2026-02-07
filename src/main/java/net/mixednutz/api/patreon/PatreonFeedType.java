package net.mixednutz.api.patreon;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import net.mixednutz.api.core.model.NetworkInfoSmall;

@JsonIgnoreProperties(ignoreUnknown=true)
public class PatreonFeedType extends NetworkInfoSmall {
	
	private static final String DISPLAY_NAME = "Patreon";
	private static final String HOST_NAME = "patreon.com";
	private static final String ID = "patreon";
	private static final String ICON_NAME = "patreon";
	
	private static PatreonFeedType instance;
	
	public PatreonFeedType() {
		super();
		this.setDisplayName(DISPLAY_NAME);
		this.setHostName(HOST_NAME);
		this.setId(ID);
		this.setFontAwesomeIconName(ICON_NAME);
	}
	
	public static PatreonFeedType getInstance() {
		if (instance==null) {
			instance = new PatreonFeedType();
		}
		return instance;
	}
	
}
