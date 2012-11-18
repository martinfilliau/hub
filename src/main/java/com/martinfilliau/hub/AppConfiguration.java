package com.martinfilliau.hub;

import com.yammer.dropwizard.config.Configuration;
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

    public String getRedisServer() {
        return redisServer;
    }
}
