package com.martinfilliau.hub;

import com.martinfilliau.hub.data.RedisManager;
import com.martinfilliau.hub.health.RedisHealthCheck;
import com.yammer.dropwizard.Service;
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
        e.addResource(new SubResource(pool.getResource()));
        e.addHealthCheck(new RedisHealthCheck(pool.getResource()));
    }
    
}
