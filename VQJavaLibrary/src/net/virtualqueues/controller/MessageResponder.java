package net.virtualqueues.controller;

import java.io.Serializable;

public interface MessageResponder {
	String getType();
	void handleMessage(Serializable data);
	
}
