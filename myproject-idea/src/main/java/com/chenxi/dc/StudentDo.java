package com.chenxi.dc;

import javax.xml.bind.annotation.XmlRootElement;
 
 @XmlRootElement
  public class StudentDo {
     private int id;
     private String name;
     private String dept;
     
     public int getId() {
         return id;
     }
     
     public StudentDo() {
     }
     
     public StudentDo(int id, String name, String dept) {
         super();
         this.id = id;
         this.name = name;
         this.dept = dept;
     }
     public void setId(int id) {
         this.id = id;
     }
     public String getName() {
         return name;
     }
     public void setName(String name) {
         this.name = name;
     }
     public String getDept() {
         return dept;
     }
     public void setDept(String dept) {
         this.dept = dept;
     }
     
 }