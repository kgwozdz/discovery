package com.proofpoint.discovery.monitor;

import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.proofpoint.event.client.EventField;
import com.proofpoint.event.client.EventType;
import com.proofpoint.units.Duration;

@EventType("platform:type=discovery,name=discovery")
public class DiscoveryEvent
{
    private final DiscoveryEventType type;
    private final boolean success;
    private final String remoteAddress;
    private final String requestUri;
    private final String requestBodyJson;
    private final Duration processingDuration;

    public DiscoveryEvent(DiscoveryEventType type, boolean success, String remoteAddress,
            String requestUri, String requestBodyJson, Duration processingDuration)
    {
        Preconditions.checkArgument(!Strings.isNullOrEmpty(requestUri));

        this.type = Preconditions.checkNotNull(type);
        this.success = success;
        this.remoteAddress = remoteAddress;
        this.requestUri = requestUri;
        this.requestBodyJson = requestBodyJson;
        this.processingDuration = Preconditions.checkNotNull(processingDuration);
    }

    @EventField
    public String getType()
    {
        return type.name();
    }

    @EventField
    public boolean isSuccess()
    {
        return success;
    }

    @EventField
    public String getRemoteAddress()
    {
        return remoteAddress;
    }

    @EventField
    public String getRequestUri()
    {
        return requestUri;
    }

    @EventField
    public String getRequestBodyJson()
    {
        return requestBodyJson;
    }

    @EventField
    public double getProcessingDuration()
    {
        return processingDuration.toMillis();
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        DiscoveryEvent that = (DiscoveryEvent) o;

        if (success != that.success) {
            return false;
        }
        if (!processingDuration.equals(that.processingDuration)) {
            return false;
        }
        if (remoteAddress != null ? !remoteAddress.equals(that.remoteAddress) : that.remoteAddress != null) {
            return false;
        }
        if (requestBodyJson != null ? !requestBodyJson.equals(that.requestBodyJson) : that.requestBodyJson != null) {
            return false;
        }
        if (!requestUri.equals(that.requestUri)) {
            return false;
        }
        if (type != that.type) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode()
    {
        int result = type.hashCode();
        result = 31 * result + (success ? 1 : 0);
        result = 31 * result + (remoteAddress != null ? remoteAddress.hashCode() : 0);
        result = 31 * result + requestUri.hashCode();
        result = 31 * result + (requestBodyJson != null ? requestBodyJson.hashCode() : 0);
        result = 31 * result + processingDuration.hashCode();
        return result;
    }

    @Override
    public String toString()
    {
        return Objects.toStringHelper(this.getClass())
                .add("type", type)
                .add("success", success)
                .add("remoteAddress", remoteAddress)
                .add("requestUri", requestUri)
                .add("requestBodyJson", requestBodyJson)
                .add("processingDuration", processingDuration).toString();
    }
}
