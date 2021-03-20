package com.chenxi.webapi;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
 
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

import org.apache.log4j.Logger;

import com.chenxi.dc.StudentDo;
 
 
 @Path("/")
 public class RestWsDemo {
     private static Logger logger = Logger.getLogger(RestWsDemo.class);
     private static int index = 1;
     private static Map<Integer,StudentDo> studentList = new HashMap<Integer, StudentDo>();
     
     public RestWsDemo() {
         if(studentList.size()==0) {
             studentList.put(index, new StudentDo(index++, "Frank",  "CS"));
             studentList.put(index, new StudentDo(index++, "Jersey", "Math"));
         }
     }

     //GET注解设置接受请求类型为GET
     @GET
     // 与Produces对应的是@Consumes
     @Produces(MediaType.TEXT_PLAIN)
     //用作测试服务启动情况
     public String getMessage() {
         return "This is the default page of my resufull project!";
     }

     @GET
     @Path("students/{studentid}")
     @Produces(MediaType.APPLICATION_JSON)
     public StudentDo getMetadata(@PathParam("studentid") int studentid) {
         if(studentList.containsKey(studentid))
             return studentList.get(studentid);
         else
             return new StudentDo(0, "Nil", "Nil");
     }
     
     @GET
     @Path("students/list")
     @Produces(MediaType.APPLICATION_JSON)
     public List<StudentDo> getAllStudents() {
         List<StudentDo> students = new ArrayList<StudentDo>();
         students.addAll(studentList.values());
         return students;
     }
     
     @POST
     @Path("students/add")
     @Produces("text/plain")
     public String addStudent(@FormParam("name") String name,
                              @FormParam("dept") String dept) {
         studentList.put(index, new StudentDo(index++, name, dept));
         return String.valueOf(index-1);
     }
     
     @DELETE
     @Path("students/delete/{studentid}")
     @Produces("text/plain")
     public String removeStudent(@PathParam("studentid") int studentid) {
         logger.info("Receieving quest for deleting student: " + studentid);
         
         StudentDo removed = studentList.remove(studentid);
         if(removed==null) return "failed!";
         else   return "true";
     }    
     
     @PUT
     @Path("students/put")
     @Produces("text/plain")
     public String putStudent(@QueryParam("studentid") int studentid,
                              @QueryParam("name") String name,
                              @QueryParam("dept") String dept
                              ) {
         logger.info("Receieving quest for putting student: " + studentid);
         if(!studentList.containsKey(studentid))
             return "failed!";
         else
             studentList.put(studentid, new StudentDo(studentid, name, dept));
         
         return String.valueOf(studentid);
     }    
 }