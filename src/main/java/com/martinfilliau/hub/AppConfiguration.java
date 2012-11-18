package com.martinfilliau.hub;

import com.yammer.dropwizard.client.JerseyClientConfiguration;
import com.yammer.dropwizard.config.Configuration;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.codehaus.jackson.annotate.JsonProperty;
import org.hibernate.validator.constraints.NotEmpty;

/**
 *
 * @author martinfilliau
 */
public class AppConfiguration extends Configuration {
    @NotEmpty
    @JsonProperty
    private String redisServer;
    
    @Valid
    @NotNull
    @JsonProperty
    private JerseyClientConfiguration httpClient = new JerseyClientConfiguration();

    public String getRedisServer() {
        return redisServer;
    }

    public JerseyClientConfiguration getJerseyClientConfiguration() {
        return httpClient;
    }
}
