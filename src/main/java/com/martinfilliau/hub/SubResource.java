package com.martinfilliau.hub;

import com.martinfilliau.hub.core.ClientVerify;
import com.martinfilliau.hub.core.SubRequestParameters;
import com.martinfilliau.hub.core.SubscriptionMode;
import com.yammer.dropwizard.client.JerseyClient;
import com.yammer.dropwizard.logging.Log;
import com.yammer.metrics.annotation.Timed;
import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.apache.commons.validator.routines.UrlValidator;
import redis.clients.jedis.Jedis;

/**
 * Resource when a Subscriber Sends a Subscription Request
 * @author martinfilliau
 */
@Path("/subscribe")
@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
public class SubResource {

    private static final Log LOG = Log.forClass(SubResource.class);

    private final Jedis jedis;
    private final JerseyClient jersey;
    
    public SubResource(Jedis jedis, JerseyClient jersey) {
        this.jedis = jedis;
        this.jersey = jersey;
    }
    
    /**
     * POST method when a Subscriber Sends a Subscription Request
     * @param callback The subscriber's callback URL where notifications should be delivered.
     * @param mode The literal string "subscribe" or "unsubscribe", depending on the goal of the request.
     * @param topic The topic URL that the subscriber wishes to subscribe to.
     * @param verify Keyword describing verification modes supported by this subscriber, as described below. This parameter may be repeated to indicate multiple supported modes.
     * @param lease (Optional) Number of seconds for which the subscriber would like to have the subscription active
     * @param secret (Optional) A subscriber-provided secret string that will be used to compute an HMAC digest for authorized content distribution
     * @param verify_token (Optional) A subscriber-provided opaque token that will be echoed back in the verification request to assist the subscriber in identifying which subscription request is being verified
     * @return 
     */
    @POST
    @Timed
    public Response subscribeCall(@FormParam(SubRequestParameters.HUB_CALLBACK) String callback,
                                        @FormParam(SubRequestParameters.HUB_MODE) String modeString,
                                        @FormParam(SubRequestParameters.HUB_TOPIC) String topic,
                                        @FormParam(SubRequestParameters.HUB_VERIFY) String verifyString,
                                        @DefaultValue("0") @FormParam(SubRequestParameters.HUB_LEASE_SECONDS) Integer lease,
                                        @DefaultValue("") @FormParam(SubRequestParameters.HUB_SECRET) String secret,
                                        @DefaultValue("") @FormParam(SubRequestParameters.HUB_VERIFY) String verify_token) {
        // Validations
        
        String[] schemes = {"http","https"};
        UrlValidator urlValidator = new UrlValidator(schemes);

        if(!urlValidator.isValid(callback)) {
            return Response.status(400).entity("400 Bad Request\n'hub.callback' is not a valid URL.").build();
        }
        
        if(!urlValidator.isValid(topic)) {
            return Response.status(400).entity("400 Bad Request\n'hub.topic' is not a valid URL.").build();            
        }
        
        SubscriptionMode mode;
        try {
            mode = Enum.valueOf(SubscriptionMode.class, modeString.toUpperCase());
        } catch (IllegalArgumentException iae) {
            return Response.status(400).entity("400 Bad Request\n'hub.mode' expects 'subscribe' or 'unsubscribe'.").build();
        }
        
        String feed = jedis.get("topic_" + topic);
        
        if(feed == null) {
            return Response.status(400).entity("400 Bad Request\nhub doesn't store topic " + topic).build();
        }
        
        ClientVerify verify;
        try {
            verify = Enum.valueOf(ClientVerify.class, verifyString.toUpperCase());
        } catch (IllegalArgumentException iae) {
            return Response.status(400).entity("400 Bad Request\n'hub.verify' expects 'sync' or 'async'.").build();
        }
        
        // Do subscribe or unsubscribe action
        
        if (mode.equals(SubscriptionMode.SUBSCRIBE)) {
            return doSubscription(callback, topic, verify, lease);
        } else if (mode.equals(SubscriptionMode.UNSUBSCRIBE)) {
            return doUnsubscription(callback, topic, verify);
        }
        
       return Response.status(400).entity("400 Bad Request").build();
    }

    public Response doSubscription(String callback, String topic, ClientVerify verify, Integer lease) {
        return Response.noContent().build();
    }
    
    public Response doUnsubscription(String callback, String topic, ClientVerify verify) {
        return Response.noContent().build();
    }
    
}
