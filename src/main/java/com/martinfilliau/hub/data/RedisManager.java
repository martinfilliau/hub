package com.martinfilliau.hub.data;

import com.yammer.dropwizard.lifecycle.Managed;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 *
 * @author martinfilliau
 */
public class RedisManager implements Managed{

    private final JedisPool pool;
    
    public RedisManager(JedisPool pool) {
        this.pool = pool;
    }
    
    public void start() throws Exception {
    }

    public void stop() throws Exception {
        pool.destroy();
    }
    
}
