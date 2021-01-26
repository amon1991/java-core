package com.amon.javacore.rpc.thrift.client;

import com.amon.javacore.rpc.thrift.HelloWorldService;
import com.amon.javacore.rpc.thrift.Request;
import com.amon.javacore.rpc.thrift.RequestException;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;

/**
 * @author yaming.chen@siemens.com
 * Created by chenyaming on 2021/1/26.
 */
public class HelloClient {

    public static final String SERVER_IP = "localhost";
    public static final int SERVER_PORT = 8090;
    public static final int TIMEOUT = 30000;

    /**
     * @param userName
     */
    public void startClient(String userName) {
        TTransport transport = null;
        try {
            transport = new TSocket(SERVER_IP, SERVER_PORT, TIMEOUT);
            // 协议要和服务端一致
            TProtocol protocol = new TBinaryProtocol(transport);
            // TProtocol protocol = new TCompactProtocol(transport);
            // TProtocol protocol = new TJSONProtocol(transport);
            HelloWorldService.Client client = new HelloWorldService.Client(protocol);
            transport.open();
            Request request = new Request();
            request.setName("amon");
            //request.setAge(10);
            String result = client.sayHello(request);
            System.out.println("Thrify client result =: " + result);
        } catch (RequestException e) {
            System.out.println("ErrorMsg:" + e.getCode() + " / " + e.getReson());
            e.printStackTrace();
        } catch (TTransportException e) {
            e.printStackTrace();
        } catch (TException e) {
            e.printStackTrace();
        } finally {
            if (null != transport) {
                transport.close();
            }
        }
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        HelloClient client = new HelloClient();
        client.startClient("china");
    }


}
