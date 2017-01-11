这是一个使用log4j的示例项目。
注意：log4j.properties文件要放在类路径下才能起作用。
使用spring的Log4jConfigListener监听器可以实现日志级别动态改变，同时也可配置log4j.properties文件的位置。
一个常用的spring的配置
<context-param>
		<param-name>log4jConfigLocation</param-name>
		<param-value>classpath:log4j.properties</param-value>
	</context-param>
	<context-param>
		<param-name>log4jRefreshInterval</param-name>
		<param-value>60000</param-value>
	</context-param>
	<context-param>
		<param-name>contextConfigLocation</param-name>
		<param-value>classpath:spring/spring-all.xml</param-value>
	</context-param>

	<listener>
		<listener-class>org.springframework.web.util.Log4jConfigListener</listener-class>
	</listener>

	<listener>
		<listener-class>org.springframework.web.context.ContextLoaderListener</listener-class>
	</listener>

	<filter>
		<filter-name>characterEncodingFilter</filter-name>
		<filter-class>org.springframework.web.filter.CharacterEncodingFilter</filter-class>
		<init-param>
			<param-name>encoding</param-name>
			<param-value>UTF-8</param-value>
		</init-param>
		<init-param>
			<param-name>ForceEncoding</param-name>
			<param-value>true</param-value>
		</init-param>
	</filter>
	<filter-mapping>
		<filter-name>characterEncodingFilter</filter-name>
		<url-pattern>/*</url-pattern>
	</filter-mapping>
	
	在服务器上观察日志找问题时，接口调用的入参中常含有文件的base64，大量的这种无意义报文打印在屏幕上面，有碍观察。LogUtil工具类可以将这种过长的入参替换掉。