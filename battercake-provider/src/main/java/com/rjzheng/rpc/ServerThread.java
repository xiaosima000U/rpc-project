package com.rjzheng.rpc;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Method;
import java.net.Socket;
import java.util.List;

public class ServerThread implements Runnable {

    private Socket client = null;

    private List<Object> serviceList = null;

    public ServerThread(Socket client, List<Object> service) {
        this.client = client;
        this.serviceList = service;
    }

    @Override
    public void run() {
        ObjectInputStream input = null;
        ObjectOutputStream output = null;
        try {
            input = new ObjectInputStream(client.getInputStream());
            output = new ObjectOutputStream(client.getOutputStream());
            // ��ȡ�ͻ���Ҫ�����Ǹ�service
            Class serviceClass = (Class) input.readObject();
            // �ҵ��÷�����
            Object obj = findService(serviceClass);
            if (obj == null) {
                output.writeObject(serviceClass.getName() + "����δ����");
            } else {
                //���÷�����ø÷��������ؽ��
                try {
                    String methodName = input.readUTF();
                    Class<?>[] parameterTypes = (Class<?>[]) input.readObject();
                    Object[] arguments = (Object[]) input.readObject();
                    Method method = obj.getClass().getMethod(methodName, parameterTypes);  
                    System.out.println(method.getName());
                    System.out.println(obj.getClass().getName());
                    
                    for(Object o:arguments){
                    	System.out.println(o.toString());
                    }
                    Object result = method.invoke(obj, arguments);  
                    output.writeObject(result); 
                } catch (Throwable t) {
                    output.writeObject(t);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                client.close();
                input.close();
                output.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

    }

    private Object findService(Class serviceClass) {
        // TODO Auto-generated method stub
        for (Object obj : serviceList) {
            boolean isFather = serviceClass.isAssignableFrom(obj.getClass());
            if (isFather) {
                return obj;
            }
        }
        return null;
    }

}