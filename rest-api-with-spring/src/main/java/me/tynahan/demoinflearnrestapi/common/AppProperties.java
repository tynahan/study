package me.tynahan.demoinflearnrestapi.common;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotEmpty;

@Component
@ConfigurationProperties(prefix = "myapp")
@Getter @Setter
public class AppProperties {

    @NotEmpty
    private String adminUserName;
    @NotEmpty
    private String adminPassword;
    @NotEmpty
    private String userUserName;
    @NotEmpty
    private String userPassword;
    @NotEmpty
    private String clientId;
    @NotEmpty
    private String clientSecret;
}
