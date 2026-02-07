package net.mixednutz.api.patreon.model;

import java.io.Serializable;

import net.mixednutz.api.model.IPost;
import net.mixednutz.api.patreon.client.PostAdapter;

public class PostForm implements IPost {
	
	String text;
	
//	Reply inReplyTo;
		
	//Post Builder
	String textPart;
	String urlPart;
	String[] tagsPart;
	
	PostAdapter postAdapter;
	
	public PostForm() {
		super();
	}
	
	public PostForm(PostAdapter postAdapter) {
		this.postAdapter = postAdapter;
	}

	public PostForm(String text) {
		super();
		this.text = text;
	}

	@Override
	public void setInReplyTo(Serializable inReplyToId) {
		
	}

	@Override
	public void setTags(String[] tags) {
		this.tagsPart = tags;
	}
	
	public void setComposeBody(String text) {
		setText(text);
	}

	@Override
	public void setText(String text) {
		this.textPart = text;
		
	}

	@Override
	public void setUrl(String url) {
		this.urlPart = url;
	}
	
}
