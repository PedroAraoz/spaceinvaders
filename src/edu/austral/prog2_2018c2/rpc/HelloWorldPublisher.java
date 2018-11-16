package edu.austral.prog2_2018c2.rpc;

import javax.xml.ws.Endpoint;
import java.awt.event.KeyEvent;

//Endpoint publisher
public class HelloWorldPublisher{
 
	public static void main(String[] args) {
		String s = "hello";
		HelloWorldImpl a = new HelloWorldImpl();
		a.writeMessage(s);
		Endpoint.publish("http://localhost:7779/ws/hello", a);
	   //Endpoint.publish("http://localhost:7779/ws/hello", new HelloWorldImpl());
    }
 
}