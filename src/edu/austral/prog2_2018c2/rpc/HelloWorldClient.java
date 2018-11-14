package edu.austral.prog2_2018c2.rpc;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import java.net.URL;

public class HelloWorldClient{
 	public static void main(String[] args) throws Exception {
 	    URL url = new URL("http://localhost:7779/ws/hello");
 	    //URL url = new URL("http://33106e36.ngrok.io/ws/hello?wsdl");
 
        //1st argument service URI, refer to wsdl document above
	    //2nd argument is service name, refer to wsdl document above
        QName qname = new QName("http://rpc.prog2_2018c2.austral.edu/", "HelloWorldImplService");
 
        Service service = Service.create(url, qname);
        HelloWorld hello = service.getPort(HelloWorld.class);
 
       // System.out.println(hello.getHelloWorldAsString("Messi"));
        System.out.println(hello.space());
        System.out.println();
 
    }
 
}