package com.martinfilliau.hub;

import com.google.common.base.Optional;
import com.martinfilliau.hub.core.ClientVerify;
import com.martinfilliau.hub.core.SubRequestParameters;
import com.martinfilliau.hub.core.SubscriptionMode;
import com.yammer.metrics.annotation.Timed;
import java.util.concurrent.atomic.AtomicLong;
import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Resource when a Subscriber Sends a Subscription Request
 * @author martinfilliau
 */
@Path("/subscribe")
@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
public class SubResource {
        
    private final String template;
    private final String defaultName;
    private final AtomicLong counter;

    public SubResource(String template, String defaultName) {
        this.template = template;
        this.defaultName = defaultName;
        this.counter = new AtomicLong();
    }

    @GET
    @Timed
    public Subscription sayHello(@QueryParam("name") Optional<String> name) {
        return new Subscription(counter.incrementAndGet(),
                String.format(template, name.or(defaultName)));
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
    public Response doSubscription(@FormParam(SubRequestParameters.HUB_CALLBACK) String callback,
                                        @FormParam(SubRequestParameters.HUB_MODE) String modeString,
                                        @FormParam(SubRequestParameters.HUB_TOPIC) String topic,
                                        @FormParam(SubRequestParameters.HUB_VERIFY) String verifyString,
                                        @DefaultValue("0") @FormParam(SubRequestParameters.HUB_LEASE_SECONDS) Integer lease,
                                        @DefaultValue("") @FormParam(SubRequestParameters.HUB_SECRET) String secret,
                                        @DefaultValue("") @FormParam(SubRequestParameters.HUB_VERIFY) String verify_token) {
        
        ClientVerify verify;
        try {
            verify = Enum.valueOf(ClientVerify.class, verifyString.toUpperCase());
        } catch (IllegalArgumentException iae) {
            return Response.status(400).entity("400 Bad Request\n'hub.verify' expects 'sync' or 'async'.").build();
        }
             
        SubscriptionMode mode;
        try {
            mode = Enum.valueOf(SubscriptionMode.class, modeString.toUpperCase());
        } catch (IllegalArgumentException iae) {
            return Response.status(400).entity("400 Bad Request\n'hub.mode' expects 'subscribe' or 'unsubscribe'.").build();
        }
        
        // check if feed exists
        
        return Response.noContent().build();
    }

}
