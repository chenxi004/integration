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
import java.sql.*;

import com.chenxi.dc.WebSiteDo;
import com.chenxi.utils.MySqlJdbc;

@Path("/mysql")
public class MySqlDemo {
	 @GET
     @Path("/website/list")
     @Produces(MediaType.APPLICATION_JSON)
     public List<WebSiteDo> getMetadata() {
		 String sql = "SELECT id, name, url FROM websites";
		 HashMap<String, String> paras = new HashMap<String, String>();
		 List<WebSiteDo> websites = MySqlJdbc.executeQuery(sql,WebSiteDo.class, paras);

         return websites;
     }
     
}
