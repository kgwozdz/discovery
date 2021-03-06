package com.proofpoint.discovery.monitor;

import com.proofpoint.stats.TimedStat;
import com.proofpoint.units.Duration;
import org.weakref.jmx.Managed;
import org.weakref.jmx.Nested;

import java.util.EnumMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import static com.proofpoint.discovery.monitor.DiscoveryEventType.REPLICATIONPULL;
import static com.proofpoint.discovery.monitor.DiscoveryEventType.REPLICATIONPUSH;
import static com.proofpoint.discovery.monitor.DiscoveryEventType.SERVICEQUERY;
import static com.proofpoint.discovery.monitor.DiscoveryEventType.DYNAMICANNOUNCEMENT;
import static com.proofpoint.discovery.monitor.DiscoveryEventType.DYNAMICANNOUNCEMENTDELETE;
import static com.proofpoint.discovery.monitor.DiscoveryEventType.STATICANNOUNCEMENT;
import static com.proofpoint.discovery.monitor.DiscoveryEventType.STATICANNOUNCEMENTLIST;
import static com.proofpoint.discovery.monitor.DiscoveryEventType.STATICANNOUNCEMENTDELETE;
import static com.proofpoint.discovery.monitor.DiscoveryEventType.STOREGET;
import static com.proofpoint.discovery.monitor.DiscoveryEventType.STOREPUT;

public class DiscoveryStats
{
    private final Map<DiscoveryEventType, Stats> eventTypeStats;

    public DiscoveryStats()
    {
        eventTypeStats = new EnumMap<DiscoveryEventType, Stats>(DiscoveryEventType.class);
        for (DiscoveryEventType type : DiscoveryEventType.values()) {
            eventTypeStats.put(type, new Stats());
        }
    }

    @Managed
    public long getServiceQuerySuccessCount()
    {
        return eventTypeStats.get(SERVICEQUERY).getSuccessCount();
    }

    @Managed
    public long getServiceQueryFailureCount()
    {
        return eventTypeStats.get(SERVICEQUERY).getFailureCount();
    }

    @Managed
    public long getStaticAnnouncementSuccessCount()
    {
        return eventTypeStats.get(STATICANNOUNCEMENT).getSuccessCount();
    }

    @Managed
    public long getStaticAnnouncementFailureCount()
    {
        return eventTypeStats.get(STATICANNOUNCEMENT).getFailureCount();
    }

    @Managed
    public long getStaticAnnouncementListSuccessCount()
    {
        return eventTypeStats.get(STATICANNOUNCEMENTLIST).getSuccessCount();
    }

    @Managed
    public long getStaticAnnouncementListFailureCount()
    {
        return eventTypeStats.get(STATICANNOUNCEMENTLIST).getFailureCount();
    }

    @Managed
    public long getStaticAnnouncementDeleteSuccessCount()
    {
        return eventTypeStats.get(STATICANNOUNCEMENTDELETE).getSuccessCount();
    }

    @Managed
    public long getStaticAnnouncementDeleteFailureCount()
    {
        return eventTypeStats.get(STATICANNOUNCEMENTDELETE).getFailureCount();
    }

    @Managed
    public long getDynamicAnnouncementSuccessCount()
    {
        return eventTypeStats.get(DYNAMICANNOUNCEMENT).getSuccessCount();
    }

    @Managed
    public long getDynamicAnnouncementFailureCount()
    {
        return eventTypeStats.get(DYNAMICANNOUNCEMENT).getFailureCount();
    }

    @Managed
    public long getDynamicAnnouncementDeleteSuccessCount()
    {
        return eventTypeStats.get(DYNAMICANNOUNCEMENTDELETE).getSuccessCount();
    }

    @Managed
    public long getDynamicAnnouncementDeleteFailureCount()
    {
        return eventTypeStats.get(DYNAMICANNOUNCEMENTDELETE).getFailureCount();
    }

    @Managed
    public long getStoreGetSuccessCount()
    {
        return eventTypeStats.get(STOREGET).getSuccessCount();
    }

    @Managed
    public long getStoreGetFailureCount()
    {
        return eventTypeStats.get(STOREGET).getFailureCount();
    }

    @Managed
    public long getStorePutSuccessCount()
    {
        return eventTypeStats.get(STOREPUT).getSuccessCount();
    }

    @Managed
    public long getStorePutFailureCount()
    {
        return eventTypeStats.get(STOREPUT).getFailureCount();
    }

    @Managed
    public long getReplicationPullSuccessCount()
    {
        return eventTypeStats.get(REPLICATIONPULL).getSuccessCount();
    }

    @Managed
    public long getReplicationPullFailureCount()
    {
        return eventTypeStats.get(REPLICATIONPULL).getFailureCount();
    }

    @Managed
    public long getReplicationPushSuccessCount()
    {
        return eventTypeStats.get(REPLICATIONPUSH).getSuccessCount();
    }

    @Managed
    public long getReplicationPushFailureCount()
    {
        return eventTypeStats.get(REPLICATIONPUSH).getFailureCount();
    }

    @Managed
    @Nested
    public TimedStat getServiceQueryProcessingTime()
    {
        return eventTypeStats.get(SERVICEQUERY).getProcessingTime();
    }

    @Managed
    @Nested
    public TimedStat getStaticAnnouncementProcessingTime()
    {
        return eventTypeStats.get(STATICANNOUNCEMENT).getProcessingTime();
    }

    @Managed
    @Nested
    public TimedStat getStaticAnnouncementListProcessingTime()
    {
        return eventTypeStats.get(STATICANNOUNCEMENTLIST).getProcessingTime();
    }

    @Managed
    @Nested
    public TimedStat getStaticAnnouncementDeleteProcessingTime()
    {
        return eventTypeStats.get(STATICANNOUNCEMENTDELETE).getProcessingTime();
    }

    @Managed
    @Nested
    public TimedStat getDynamicAnnouncementProcessingTime()
    {
        return eventTypeStats.get(DYNAMICANNOUNCEMENT).getProcessingTime();
    }

    @Managed
    @Nested
    public TimedStat getDynamicAnnouncementDeleteProcessingTime()
    {
        return eventTypeStats.get(DYNAMICANNOUNCEMENTDELETE).getProcessingTime();
    }

    @Managed
    @Nested
    public TimedStat getStoreGetProcessingTime()
    {
        return eventTypeStats.get(STOREGET).getProcessingTime();
    }

    @Managed
    @Nested
    public TimedStat getStorePutProcessingTime()
    {
        return eventTypeStats.get(STOREPUT).getProcessingTime();
    }

    @Managed
    @Nested
    public TimedStat getReplicationPullProcessingTime()
    {
        return eventTypeStats.get(REPLICATIONPULL).getProcessingTime();
    }

    @Managed
    @Nested
    public TimedStat getReplicationPushProcessingTime()
    {
        return eventTypeStats.get(REPLICATIONPUSH).getProcessingTime();
    }

    public void addStats(DiscoveryEventType type, boolean success, long startTime)
    {
        eventTypeStats.get(type).incrementCount(success);
        eventTypeStats.get(type).addProcessingTime(startTime);
    }

    private static class Stats
    {
        private final AtomicLong successCount;
        private final AtomicLong failureCount;
        private final TimedStat processingTime;

        public Stats()
        {
            this.successCount = new AtomicLong();
            this.failureCount = new AtomicLong();
            this.processingTime = new TimedStat();
        }

        public long getSuccessCount()
        {
            return successCount.get();
        }

        public long getFailureCount()
        {
            return failureCount.get();
        }

        public TimedStat getProcessingTime()
        {
            return processingTime;
        }

        public void incrementCount(boolean success)
        {
            if (success) {
                successCount.getAndIncrement();
            }
            else {
                failureCount.getAndIncrement();
            }
        }

        public void addProcessingTime(long startTime)
        {
            processingTime.addValue(Duration.nanosSince(startTime));
        }
    }
}
