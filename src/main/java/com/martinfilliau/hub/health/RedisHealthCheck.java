package com.martinfilliau.hub.health;

import com.yammer.metrics.core.HealthCheck;
import redis.clients.jedis.Jedis;

/**
 *
 * @author martinfilliau
 */
public class RedisHealthCheck extends HealthCheck {

    private Jedis jedis;
    
    public RedisHealthCheck(Jedis jedis) {
        super("RedisHealthCheck");
        this.jedis = jedis;
    }
    
    @Override
    protected Result check() throws Exception {
        if(jedis.isConnected()) {
            return Result.healthy();
        } else {
            return Result.unhealthy("Not connected to Redis.");
        }        
    }
    
}
