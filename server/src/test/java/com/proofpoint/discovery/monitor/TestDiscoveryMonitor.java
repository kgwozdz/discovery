package com.proofpoint.discovery.monitor;

import com.proofpoint.event.client.InMemoryEventClient;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class TestDiscoveryMonitor
{
    private InMemoryEventClient eventClient;
    private DiscoveryStats stats;
    private DiscoveryMonitor monitor;
    private long startTime;
    private final String remoteAddress = "http://m0010111.lab.ppops.net";
    private final String requestUri = "http://localhost:4111";

    @BeforeMethod
    public void setup()
    {
        eventClient = new InMemoryEventClient();
        stats = new DiscoveryStats();
        monitor = new DiscoveryMonitor(eventClient, stats);
        startTime = System.nanoTime();
    }

    @Test
    public void testMonitorServiceQueryEvent()
    {
        monitor.monitorDiscoveryEvent(DiscoveryEventType.SERVICEQUERY, true, remoteAddress, requestUri, "", startTime);
        assertEquals(stats.getServiceQuerySuccessCount(), 1);
        assertEquals(stats.getServiceQueryProcessingTime().getCount(), 1);
        assertEquals(eventClient.getEvents().size(), 1);
        assertEquals(((DiscoveryEvent) eventClient.getEvents().get(0)).getType(), DiscoveryEventType.SERVICEQUERY.name());
    }

    @Test
    public void testMonitorStaticAnnouncementEvent()
    {
        monitor.monitorDiscoveryEvent(DiscoveryEventType.STATICANNOUNCEMENT, false, remoteAddress, requestUri, "", startTime);
        assertEquals(stats.getStaticAnnouncementFailureCount(), 1);
        assertEquals(stats.getStaticAnnouncementProcessingTime().getCount(), 1);
        assertEquals(eventClient.getEvents().size(), 1);
        assertEquals(((DiscoveryEvent) eventClient.getEvents().get(0)).getType(), DiscoveryEventType.STATICANNOUNCEMENT.name());
    }

    @Test
    public void testMonitorStaticAnnouncementListEvent()
    {
        monitor.monitorDiscoveryEvent(DiscoveryEventType.STATICANNOUNCEMENTLIST, false, remoteAddress, requestUri, "", startTime);
        assertEquals(stats.getStaticAnnouncementListFailureCount(), 1);
        assertEquals(stats.getStaticAnnouncementListProcessingTime().getCount(), 1);
        assertEquals(eventClient.getEvents().size(), 1);
        assertEquals(((DiscoveryEvent) eventClient.getEvents().get(0)).getType(), DiscoveryEventType.STATICANNOUNCEMENTLIST.name());
    }

    @Test
    public void testMonitorStaticAnnouncementDeleteEvent()
    {
        monitor.monitorDiscoveryEvent(DiscoveryEventType.STATICANNOUNCEMENTDELETE, true, remoteAddress, requestUri, "", startTime);
        assertEquals(stats.getStaticAnnouncementDeleteSuccessCount(), 1);
        assertEquals(stats.getStaticAnnouncementDeleteProcessingTime().getCount(), 1);
        assertEquals(eventClient.getEvents().size(), 1);
        assertEquals(((DiscoveryEvent) eventClient.getEvents().get(0)).getType(), DiscoveryEventType.STATICANNOUNCEMENTDELETE.name());
    }

    @Test
    public void testMonitorDynamicAnnouncementEvent()
    {
        monitor.monitorDiscoveryEvent(DiscoveryEventType.DYNAMICANNOUNCEMENT, false, remoteAddress, requestUri, "", startTime);
        assertEquals(stats.getDynamicAnnouncementFailureCount(), 1);
        assertEquals(stats.getDynamicAnnouncementProcessingTime().getCount(), 1);
        assertEquals(eventClient.getEvents().size(), 1);
        assertEquals(((DiscoveryEvent) eventClient.getEvents().get(0)).getType(), DiscoveryEventType.DYNAMICANNOUNCEMENT.name());
    }

    @Test
    public void testMonitorDynamicAnnouncementDeleteEvent()
    {
        monitor.monitorDiscoveryEvent(DiscoveryEventType.DYNAMICANNOUNCEMENTDELETE, true, remoteAddress, requestUri, "", startTime);
        assertEquals(stats.getDynamicAnnouncementDeleteSuccessCount(), 1);
        assertEquals(stats.getDynamicAnnouncementDeleteProcessingTime().getCount(), 1);
        assertEquals(eventClient.getEvents().size(), 1);
        assertEquals(((DiscoveryEvent) eventClient.getEvents().get(0)).getType(), DiscoveryEventType.DYNAMICANNOUNCEMENTDELETE.name());
    }

    @Test
    public void testMonitorStoreGetEvent()
    {
        monitor.monitorDiscoveryEvent(DiscoveryEventType.STOREGET, true, remoteAddress, requestUri, "", startTime);
        assertEquals(stats.getStoreGetSuccessCount(), 1);
        assertEquals(stats.getStoreGetProcessingTime().getCount(), 1);
        assertEquals(eventClient.getEvents().size(), 1);
        assertEquals(((DiscoveryEvent) eventClient.getEvents().get(0)).getType(), DiscoveryEventType.STOREGET.name());
    }

    @Test
    public void testMonitorStorePutEvent()
    {
        monitor.monitorDiscoveryEvent(DiscoveryEventType.STOREPUT, true, remoteAddress, requestUri, "", startTime);
        assertEquals(stats.getStorePutSuccessCount(), 1);
        assertEquals(stats.getStorePutProcessingTime().getCount(), 1);
        assertEquals(eventClient.getEvents().size(), 1);
        assertEquals(((DiscoveryEvent) eventClient.getEvents().get(0)).getType(), DiscoveryEventType.STOREPUT.name());
    }

    @Test
    public void testMonitorReplicationPullEvent()
    {
        monitor.monitorDiscoveryEvent(DiscoveryEventType.REPLICATIONPULL, true, remoteAddress, requestUri, "", startTime);
        assertEquals(stats.getReplicationPullSuccessCount(), 1);
        assertEquals(stats.getReplicationPullProcessingTime().getCount(), 1);
        assertEquals(eventClient.getEvents().size(), 1);
        assertEquals(((DiscoveryEvent) eventClient.getEvents().get(0)).getType(), DiscoveryEventType.REPLICATIONPULL.name());
    }

    @Test
    public void testMonitorReplicationPushEvent()
    {
        monitor.monitorDiscoveryEvent(DiscoveryEventType.REPLICATIONPUSH, true, remoteAddress, requestUri, "", startTime);
        assertEquals(stats.getReplicationPushSuccessCount(), 1);
        assertEquals(stats.getReplicationPushProcessingTime().getCount(), 1);
        assertEquals(eventClient.getEvents().size(), 1);
        assertEquals(((DiscoveryEvent) eventClient.getEvents().get(0)).getType(), DiscoveryEventType.REPLICATIONPUSH.name());
    }

    @Test
    public void testMonitorServiceQueryFailureEvent()
    {
        monitor.monitorDiscoveryFailureEvent(DiscoveryEventType.SERVICEQUERY, new NullPointerException(), requestUri);
        assertEquals(eventClient.getEvents().size(), 1);
        assertEquals(((DiscoveryFailureEvent) eventClient.getEvents().get(0)).getType(), DiscoveryEventType.SERVICEQUERY.name());
    }

    @Test
    public void testMonitorStaticAnnouncementFailureEvent()
    {
        monitor.monitorDiscoveryFailureEvent(DiscoveryEventType.STATICANNOUNCEMENT, new NullPointerException(), requestUri);
        assertEquals(eventClient.getEvents().size(), 1);
        assertEquals(((DiscoveryFailureEvent) eventClient.getEvents().get(0)).getType(), DiscoveryEventType.STATICANNOUNCEMENT.name());
    }

    @Test
    public void testMonitorStaticAnnouncementListFailureEvent()
    {
        monitor.monitorDiscoveryFailureEvent(DiscoveryEventType.STATICANNOUNCEMENTLIST, new NullPointerException(), requestUri);
        assertEquals(eventClient.getEvents().size(), 1);
        assertEquals(((DiscoveryFailureEvent) eventClient.getEvents().get(0)).getType(), DiscoveryEventType.STATICANNOUNCEMENTLIST.name());
    }

    @Test
    public void testMonitorStaticAnnouncementDeleteFailureEvent()
    {
        monitor.monitorDiscoveryFailureEvent(DiscoveryEventType.STATICANNOUNCEMENTDELETE, new NullPointerException(), requestUri);
        assertEquals(eventClient.getEvents().size(), 1);
        assertEquals(((DiscoveryFailureEvent) eventClient.getEvents().get(0)).getType(), DiscoveryEventType.STATICANNOUNCEMENTDELETE.name());
    }

    @Test
    public void testMonitorDynamicAnnouncementFailureEvent()
    {
        monitor.monitorDiscoveryFailureEvent(DiscoveryEventType.DYNAMICANNOUNCEMENT, new NullPointerException(), requestUri);
        assertEquals(eventClient.getEvents().size(), 1);
        assertEquals(((DiscoveryFailureEvent) eventClient.getEvents().get(0)).getType(), DiscoveryEventType.DYNAMICANNOUNCEMENT.name());
    }

    @Test
    public void testMonitorDynamicAnnouncementDeleteFailureEvent()
    {
        monitor.monitorDiscoveryFailureEvent(DiscoveryEventType.DYNAMICANNOUNCEMENTDELETE, new NullPointerException(), requestUri);
        assertEquals(eventClient.getEvents().size(), 1);
        assertEquals(((DiscoveryFailureEvent) eventClient.getEvents().get(0)).getType(), DiscoveryEventType.DYNAMICANNOUNCEMENTDELETE.name());
    }

    @Test
    public void testMonitorStoreGetFailureEvent()
    {
        monitor.monitorDiscoveryFailureEvent(DiscoveryEventType.STOREGET, new NullPointerException(), requestUri);
        assertEquals(eventClient.getEvents().size(), 1);
        assertEquals(((DiscoveryFailureEvent) eventClient.getEvents().get(0)).getType(), DiscoveryEventType.STOREGET.name());
    }

    @Test
    public void testMonitorStorePutFailureEvent()
    {
        monitor.monitorDiscoveryFailureEvent(DiscoveryEventType.STOREPUT, new NullPointerException(), requestUri);
        assertEquals(eventClient.getEvents().size(), 1);
        assertEquals(((DiscoveryFailureEvent) eventClient.getEvents().get(0)).getType(), DiscoveryEventType.STOREPUT.name());
    }

    @Test
    public void testMonitorReplicationPullFailureEvent()
    {
        monitor.monitorDiscoveryFailureEvent(DiscoveryEventType.REPLICATIONPULL, new NullPointerException(), requestUri);
        assertEquals(eventClient.getEvents().size(), 1);
        assertEquals(((DiscoveryFailureEvent) eventClient.getEvents().get(0)).getType(), DiscoveryEventType.REPLICATIONPULL.name());
    }

    @Test
    public void testMonitorReplicationPushFailureEvent()
    {
        monitor.monitorDiscoveryFailureEvent(DiscoveryEventType.REPLICATIONPUSH, new NullPointerException(), requestUri);
        assertEquals(eventClient.getEvents().size(), 1);
        assertEquals(((DiscoveryFailureEvent) eventClient.getEvents().get(0)).getType(), DiscoveryEventType.REPLICATIONPUSH.name());
    }
}
