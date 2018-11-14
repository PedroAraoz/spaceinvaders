package edu.austral.prog2_2018c2.rpc;

import javax.xml.ws.Endpoint;
import java.awt.event.KeyEvent;

//Endpoint publisher
public class HelloWorldPublisher{
 
	public static void main(String[] args) {
		String s = "space";
		Endpoint.publish("http://localhost:7779/ws/hello", new HelloWorldImpl());
	   //Endpoint.publish("http://localhost:7779/ws/hello", new HelloWorldImpl());
    }
 
}