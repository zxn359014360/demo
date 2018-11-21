package com.freemarker;

import freemarker.template.TemplateException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.ui.freemarker.FreeMarkerConfigurationFactory;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@Configuration
public class FreeMarkerConfig {

    @Bean
    public ViewResolver viewResolver() {
        SimpleFreeMarkerViewResolver resolver = new SimpleFreeMarkerViewResolver();
        resolver.setCache(false);
        resolver.setViewClass(FreeMarkerView.class);
        resolver.setContentType("text/html; charset=UTF-8");
        resolver.setSuffix(".html");
        resolver.setExposeSpringMacroHelpers(true);
        resolver.setExposeRequestAttributes(false);
        resolver.setExposeSessionAttributes(true);
        return resolver;
    }

    @Bean
    public FreeMarkerConfigurer freemarkerConfig() throws IOException, TemplateException {
        FreeMarkerConfigurationFactory factory = new FreeMarkerConfigurationFactory();
        factory.setTemplateLoaderPaths("/view");
        factory.setDefaultEncoding("UTF-8");

        FreeMarkerConfigurer result = new FreeMarkerConfigurer();

        freemarker.template.Configuration configuration = factory.createConfiguration();
        configuration.setClassicCompatible(true);
        result.setConfiguration(configuration);

        Properties settings = new Properties();
        settings.put("template_update_delay", "5");
        settings.put("tag_syntax", "auto_detect");
        settings.put("default_encoding", "UTF-8");
        settings.put("url_escaping_charset", "UTF-8");
        settings.put("locale", "zh_CN");
        settings.put("number_format", "0.##########");
        settings.put("datetime_format", "yyyy-MM-dd HH:mm:ss");
        settings.put("date_format", "yyyy-MM-dd");
        settings.put("time_format", "HH:mm:ss");
        settings.put("classic_compatible", true);
        settings.put("template_exception_handler", "ignore");
        settings.put("whitespace_stripping", true);
        settings.put("boolean_format", "true,false");
        result.setFreemarkerSettings(settings);

        Map map = new HashMap(2);
        map.put("xml_escape", "fmXmlEscape");
        map.put("html_tag", "htmlTag");
        factory.setFreemarkerVariables(map);
        return result;
    }


}
