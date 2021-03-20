package com.chenxi.webapi;

import java.util.HashMap;
import java.util.List;
 
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

import redis.clients.jedis.Jedis;

import com.chenxi.utils.RedisUtil;

@Path("/redis")
public class RedisDemo {
	 @GET
     @Path("/connect")
     @Produces({ MediaType.APPLICATION_JSON})
     public String RedisConnect() {
         //连接本地的 Redis 服务
         Jedis jedis = new Jedis("localhost",6378);

         //Jedis jedis = new Jedis("localhost");//不指定端口 默认是6379
         ////权限认证
         //jedis.auth("abcd@1234");
         System.out.println("连接成功");
         //查看服务是否运行
         return "服务正在运行: "+jedis.ping();
     }

    @GET
    @Path("/test")
    @Produces({ MediaType.APPLICATION_JSON})
    public String RedisTest() {

        RedisUtil.testString();
        RedisUtil.testMap();

        //查看服务是否运行
        return "test redis";
    }
}
