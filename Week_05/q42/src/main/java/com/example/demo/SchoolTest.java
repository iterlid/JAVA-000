package com.example.demo;

import com.example.demo.entity.School;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SchoolTest {

    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        School school = (School) context.getBean("school");

        System.out.println(school.getTeacher());
    }
}
