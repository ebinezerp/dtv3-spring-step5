package com.stackroute.activitystream.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/*This class will contain the application-context for the application. 
 * Define the following annotations:
 * @Configuration - Annotating a class with the @Configuration indicates that the 
 *                  class can be used by the Spring IoC container as a source of 
 *                  bean definitions
 * @ComponentScan - this annotation is used to search for the Spring components amongst the application
 * @EnableTransactionManagement - Enables Spring's annotation-driven transaction management capability.
 * @EnableWebMvc -  Adding this annotation to an @Configuration class imports the Spring MVC 
 * 				   configuration from WebMvcConfigurationSupport              
 * 
 * */
@Configuration
@ComponentScan("com.stackroute.activitystream")
@EnableWebMvc
@EnableTransactionManagement
public class ApplicationContextConfig {

}