package com.chenxi.webapi;

import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

@Path("/tread")
public class ThreadDemo {
    @GET
    @Path("/test")
    @Produces({ MediaType.APPLICATION_JSON})
    public String test() {
        //MyThread mythread = new MyThread();
        //Thread t1 = new Thread(mythread);
        //t1.start();
        //查看服务是否运行
        return "多线程测试: ";
    }
}
