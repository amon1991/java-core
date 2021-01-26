package com.amon.javacore.rpc.thrift;

import org.apache.thrift.TException;

/**
 * @author yaming.chen@siemens.com
 * Created by chenyaming on 2021/1/26.
 */
public class HelloWorldImpl implements HelloWorldService.Iface {

    @Override
    public String sayHello(Request request) throws RequestException, TException {

        if (!request.isSetAge()) {
            RequestException e = new RequestException(100);
            e.setReson("need to set age");
            throw e;
        } else {
            return "Hi," + request.getName() + " / " + request.getAge() + " welcome to thrift world";
        }

    }

}
