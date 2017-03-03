package com.kth.sda.team.integration;
/**
 * The garage door class to control open and close the garage door.
 * @author tmpuser-10229
 *
 */
public class GarageDoor {
	/**
	 * Open the garage door.
	 */
	
	private static GarageDoor garageDoor=null;
	
	public static GarageDoor getInstanceOfGarageDoor(){
		if(garageDoor==null)
		{
			 garageDoor=new GarageDoor();
		}
		return garageDoor;
		
	}
	public void open() {
		System.out.println("Garage Door is OPEN");
		
	}
	/**
	 * close the garage door.
	 */
	public void close(){
		System.out.println("Garage Door is CLOSED");
	}

}
