package com;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/contents/storage/**")
				.addResourceLocations("file:///" + UploadCon.getUploadDir());

		registry.addResourceHandler("/member/storage/**")
				.addResourceLocations("file:///" + UploadMem.getUploadDir());

		registry.addResourceHandler("/ckstorage/files/**")
				.addResourceLocations("file:///" + UploadCk.getUploadDir() + "/files/");

	}

}