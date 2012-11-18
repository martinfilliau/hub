package com.martinfilliau.hub.core;

/**
 * Parameters when a Subscriber Sends a Subscription Request
 * @author martinfilliau
 */
public interface SubRequestParameters {
    /**
     * REQUIRED. The subscriber's callback URL where notifications should be delivered.
     */
    public final static String HUB_CALLBACK = "hub.callback";
    
    /**
     * REQUIRED. The literal string "subscribe" or "unsubscribe", depending on the goal of the request.
     */
    public final static String HUB_MODE = "hub.mode";
    
    /**
     * REQUIRED. The topic URL that the subscriber wishes to subscribe to.
     */
    public final static String HUB_TOPIC = "hub.topic";
    
    /**
     * REQUIRED. Keyword describing verification modes supported by this subscriber, as described below. This parameter may be repeated to indicate multiple supported modes.
     */
    public final static String HUB_VERIFY = "hub.verify";
    
    /**
     * REQUIRED. A hub-generated, random string that MUST be echoed by the subscriber to verify the subscription.
     */
    public final static String HUB_CHALLENGE = "hub.challenge";
    
    /**
     * OPTIONAL. Number of seconds for which the subscriber would like to have the subscription active. If not present or an empty value, the subscription will be permanent (or active until automatic refreshing removes the subscription). Hubs MAY choose to respect this value or not, depending on their own policies. This parameter MAY be present for unsubscription requests and MUST be ignored by the hub in that case.
     */
    public final static String HUB_LEASE_SECONDS = "hub.lease_seconds";
    
    /**
     * OPTIONAL. A subscriber-provided secret string that will be used to compute an HMAC digest for authorized content distribution. If not supplied, the HMAC digest will not be present for content distribution requests. This parameter SHOULD only be specified when the request was made over HTTPS [RFC2818]. This parameter MUST be less than 200 bytes in length.
     */
    public final static String HUB_SECRET = "hub.secret";
    
    /**
     * OPTIONAL. A subscriber-provided opaque token that will be echoed back in the verification request to assist the subscriber in identifying which subscription request is being verified. If this is not included, no token will be included in the verification request.
     */
    public final static String HUB_VERIFY_TOKEN = "hub.verify_token";

}
