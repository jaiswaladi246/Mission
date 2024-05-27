package ca.sheridancollege.beans;

import lombok.Data;

/**
 * This class is for the beans and saves the field.
 * 
 * @author Mihye
 */
@Data
public class Mission {
	private Long id;
	private String agent;
	private String title;
	private String gadget1;
	private String gadget2;
	
	// constructor with fields for later use in the test
	/*
	public Mission(Long id, String agent, String title, String gadget1, 
			String gadget2) {
		this.id = id;
		this.agent = agent;
		this.title = title;
		this.gadget1 = gadget1;
		this.gadget2 = gadget2;
	}	
	*/
}
