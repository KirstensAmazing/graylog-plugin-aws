package com.graylog2.input.cloudtrail.json;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.Maps;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CloudTrailRecord implements Serializable {
    @JsonProperty("eventVersion")
    public String eventVersion;
    @JsonProperty("eventTime")
    public String eventTime;

    @JsonProperty("userIdentity")
    public CloudTrailUserIdentity userIdentity;

    @JsonProperty("eventSource")
    public String eventSource;
    @JsonProperty("eventName")
    public String eventName;
    @JsonProperty("awsRegion")
    public String awsRegion;
    @JsonProperty("sourceIPAddress")
    public String sourceIPAddress;
    @JsonProperty("userAgent")
    public String userAgent;
    @JsonProperty("requestID")
    public String requestID;
    @JsonProperty("eventID")
    public String eventID;
    @JsonProperty("eventType")
    public String eventType;
    @JsonProperty("recipientAccountId")
    public String recipientAccountId;
    @JsonProperty("requestParameters")
    public Map<String, Object> requestParameters;

    public Map<String, Object> additionalFieldsAsMap() {
        Map<String, Object> m = Maps.newHashMap();

        m.put("event_source", eventSource);
        m.put("event_name", eventName);
        m.put("aws_region", awsRegion);
        m.put("source_address", sourceIPAddress);
        m.put("user_agent", userAgent);
        m.put("request_id", requestID);
        m.put("event_id", eventID);
        m.put("event_type", eventType);
        m.put("recipient_account_id", recipientAccountId);

        if (userIdentity != null) {
            m.putAll(userIdentity.additionalFieldsAsMap());
        }

        return m;
    }

    public String getFullMessage() {
        if (requestParameters != null && !requestParameters.isEmpty()) {
            // Le pretty print.
            return Arrays.toString(requestParameters.entrySet().toArray());
        }

        return null;
    }

    public String getConstructedMessage() {
        return eventSource + ":" + eventName + " in " + awsRegion + " by " + sourceIPAddress + " / " + userIdentity.userName;
    }

}
