package com.rjzheng.rpc;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.net.Socket;

import com.rjzheng.service.BatterCakeService;

public class ProxyHandler implements InvocationHandler {

    private String ip;
    private int port;

    public ProxyHandler(String ip, int port) {
        // TODO Auto-generated constructor stub
        this.ip = ip;
        this.port = port;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // TODO Auto-generated method stub
        Socket socket = new Socket(this.ip, this.port);
        ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
        ObjectInputStream input = new ObjectInputStream(socket.getInputStream());
        try {
            output.writeObject(proxy.getClass().getInterfaces()[0]);
            output.writeUTF(method.getName());
            output.writeObject(method.getParameterTypes());
            output.writeObject(args);
            System.out.println(proxy.getClass().getInterfaces()[0]);
            System.out.println(method.getName());
            System.out.println(method.getParameters().toString());
            System.out.println(args.toString());
            output.flush();
            Object result = input.readObject();
            System.out.println(result);
            if(result instanceof Throwable) {
                throw (Throwable) result;
            }
                return result;
        } finally {
            socket.shutdownOutput();
        }
    }

}