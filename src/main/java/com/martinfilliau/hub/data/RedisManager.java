package com.martinfilliau.hub.data;

import com.yammer.dropwizard.lifecycle.Managed;
import redis.clients.jedis.JedisPool;

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
