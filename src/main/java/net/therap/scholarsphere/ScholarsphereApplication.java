package net.therap.scholarsphere;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

@SpringBootApplication
public class ScholarsphereApplication implements WebMvcConfigurer {

    private final LocaleChangeInterceptor localeChangeInterceptor;

    public ScholarsphereApplication(LocaleChangeInterceptor localeChangeInterceptor) {
        this.localeChangeInterceptor = localeChangeInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry interceptorRegistry) {
        interceptorRegistry.addInterceptor(localeChangeInterceptor);
    }

    public static void main(String[] args) {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasenames("resources/messages");
        messageSource.setDefaultEncoding("UTF-8");

        SpringApplication.run(ScholarsphereApplication.class, args);
    }

}
