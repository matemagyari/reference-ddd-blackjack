package org.home.blackjack.core;

import org.springframework.context.support.ClassPathXmlApplicationContext;


public class Main {

	public static void main(String[] args) throws Exception {
		
		ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:META-INF/applicationContext-blackjack-core.xml");
		applicationContext.registerShutdownHook();
		applicationContext.start();
		
		System.err.println("hey");

		/*
		Server server = new Server(8080);

		// Create the 'root' Spring application context

		final ServletHolder servletHolder = new ServletHolder(new DefaultServlet());

		final ServletContextHandler context = new ServletContextHandler();

		context.setContextPath("/");

		context.addServlet(servletHolder, "/*");

		context.addEventListener(new ContextLoaderListener());

		context.setInitParameter("contextClass", AnnotationConfigWebApplicationContext.class.getName());

		context.setInitParameter("contextConfigLocation", AppConfig.class.getName());

		server.setHandler(context);

		WebSocketServerContainerInitializer.configureContext(context);

		server.start();

		server.join();
		*/

	}

}
