package com.rjzheng.start;

import com.rjzheng.rpc.RpcConsumer;
import com.rjzheng.service.BatterCakeService;
public class RpcTest {

    public static void main(String[] args) {
        BatterCakeService batterCakeService=RpcConsumer.getService(BatterCakeService.class, "127.0.0.1", 20006);
        String result=batterCakeService.sellBatterCake("Ë«µ°");
        System.out.println(result);
    }
}