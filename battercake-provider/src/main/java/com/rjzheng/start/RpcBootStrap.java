package com.rjzheng.start;

import com.rjzheng.rpc.RpcProvider;
import com.rjzheng.service.BatterCakeService;
import com.rjzheng.service.impl.BatterCakeServiceImpl;

public class RpcBootStrap {
    public static void main(String[] args) throws Exception {
        BatterCakeService batterCakeService =new BatterCakeServiceImpl();
        //����������ķ���ע����20006�˿�
        RpcProvider.export(20006,batterCakeService);
    }
}