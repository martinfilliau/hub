package com.martinfilliau.hub;

/**
 *
 * @author martinfilliau
 */
public class Subscription {
    
    private long count;
    
    private String name;
    
    private String url;

    public Subscription(long count, String name) {
        this.count = count;
        this.name = name;
        this.url = "";
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }
    
    
    
}
