package demo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.web.HttpMessageConverters;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.JsonViewResponseBodyAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

@Configuration
@ComponentScan
@EnableAutoConfiguration
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Configuration
    public static class MvcConfig extends WebMvcConfigurerAdapter{

    	@Override
    	public void configureHandlerExceptionResolvers(
    			List<HandlerExceptionResolver> exceptionResolvers) {
    		exceptionResolvers.add(customHandlerMethodExceptionResolver());
    	}

    	@Autowired
    	private HttpMessageConverters messageConverters;

    	@Bean
    	public HandlerExceptionResolver customHandlerMethodExceptionResolver() {
    		CustomHandlerMethodExceptionResolver handlerMethodExceptionResolver = new CustomHandlerMethodExceptionResolver();
    		handlerMethodExceptionResolver.setMessageConverters(messageConverters.getConverters());
			List<ResponseBodyAdvice<?>> interceptors = new ArrayList<ResponseBodyAdvice<?>>();
			interceptors.add(new JsonViewResponseBodyAdvice());
			handlerMethodExceptionResolver.setResponseBodyAdvice(interceptors);
			return handlerMethodExceptionResolver;
    	}
    }
}
