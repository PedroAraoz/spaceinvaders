package edu.austral.prog2_2018c2.rpc;

import javax.jws.WebService;

//Service Implementation
@WebService(endpointInterface = "edu.austral.prog2_2018c2.rpc.HelloWorld")
public class HelloWorldImpl implements HelloWorld{

	@Override
	public String getHelloWorldAsString(String name) {
		return "hola " + name + "!";
	}
	
	@Override
	public String space() {
		return "space";
	}
	
}