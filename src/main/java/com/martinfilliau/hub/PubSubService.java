package com.martinfilliau.hub;

import com.martinfilliau.hub.data.RedisManager;
import com.martinfilliau.hub.health.RedisHealthCheck;
import com.yammer.dropwizard.Service;
import com.yammer.dropwizard.client.JerseyClient;
import com.yammer.dropwizard.client.JerseyClientFactory;
import com.yammer.dropwizard.config.Environment;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

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
        final String redisServer = configuration.getRedisServer();
        JedisPool pool = new JedisPool(new JedisPoolConfig(), redisServer);
        e.manage(new RedisManager(pool));
        final JerseyClientFactory factory = new JerseyClientFactory(configuration.getJerseyClientConfiguration());
        final JerseyClient jerseyClient = factory.build(e);
        e.addResource(new SubResource(pool.getResource(), jerseyClient));
        e.addHealthCheck(new RedisHealthCheck(pool.getResource()));
    }
    
}
