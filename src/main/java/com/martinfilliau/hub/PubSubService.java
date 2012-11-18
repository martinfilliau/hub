package com.martinfilliau.hub;

import com.yammer.dropwizard.Service;
import com.yammer.dropwizard.config.Environment;

/**
 *
 * @author martinfilliau
 */
public class PubSubService extends Service<AppConfiguration> {

    public static void main(String[] args) throws Exception {
        new PubSubService().run(args);
    }
    
    private PubSubService() {
        super("PubSubService");
    }
    
    @Override
    protected void initialize(AppConfiguration configuration, Environment e) throws Exception {
        final String template = configuration.getTemplate();
        final String defaultName = configuration.getDefaultName();
        e.addResource(new SubResource(template, defaultName));
    }
    
}
