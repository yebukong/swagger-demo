package swagger.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Swagger初始化配置
 * 
 * @author Mine
 * @date 2019/04/10 00:04:99
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig implements WebMvcConfigurer {
//	继承WebMvcConfigurationSupport-->不需要返回逻辑视图,可以选择继承此类 ps:继承WebMvcConfigurationSupport会发现Spring Boot的WebMvc自动配置失效(WebMvcAutoConfiguration自动化配置)，会导致无法视图解析器无法解析并返回到对应的视图
//	实现WebMvcCofigurer-->返回逻辑视图,可以选择实现此方法,重写addInterceptor方法	
	
	@Bean
	public Docket customDocket() {
		return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("swagger.demo.controller"))
                .paths(PathSelectors.any())//过滤的路径
                .build();
	}

	private ApiInfo apiInfo() {
		Contact contact = new Contact("yebukong", "https://yebukong.com/", "yebukong@qq.com");
		return new ApiInfoBuilder()
				.title("swagger-demo API接口")
				.description("简单的swagger UI用法展示")
				.contact(contact)
				.version("0.0.1")
				.build();
	}

	/** 
	 * 资源处理器
     * 支持的默认路径 org.springframework.boot.autoconfigure.web.ResourceProperties.CLASSPATH_RESOURCE_LOCATIONS
	 * 添加Swagger2静态文件映射
	 **/
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
		registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
	}
}
