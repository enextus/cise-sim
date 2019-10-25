package eu.cise.dispatcher.soap;

import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.interceptor.Interceptor;
import org.apache.cxf.message.Message;


public class SoapInterceptor implements Interceptor {


    @Override
    public void handleMessage(Message message) throws Fault {
        System.out.println("xxxx"+message.getId());
    }

    @Override
    public void handleFault(Message message) {
        System.out.println("xxxx"+message.getId());
    }
}
