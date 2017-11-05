package ru.otus.hw;

import com.sun.management.GarbageCollectionNotificationInfo;

import javax.management.ListenerNotFoundException;
import javax.management.NotificationEmitter;
import javax.management.NotificationListener;
import javax.management.openmbean.CompositeData;
import java.lang.management.*;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class GcMonitor {

    private static Map<GcGeneration,Long> gcGen;

    private enum GcGeneration {
        NEW,OLD,UNKNOWN;
    }

    static {
        gcGen = new ConcurrentHashMap<>();
        gcGen.put(GcGeneration.NEW,0L);
    }

    private static NotificationListener gcHandler = (notification, handback ) -> {
        if (notification.getType().equals(GarbageCollectionNotificationInfo.GARBAGE_COLLECTION_NOTIFICATION)) {
        GarbageCollectionNotificationInfo gcInfo = GarbageCollectionNotificationInfo.from((CompositeData) notification.getUserData());

        GcGeneration gcg = parseGcAction(gcInfo.getGcAction());
        long duration = gcInfo.getGcInfo().getDuration();
        gcGen.merge(gcg,duration,(valOld, valNew) -> valOld + valNew);
    }};

    private static GcGeneration parseGcAction(String gcAction) {
        if (gcAction.equals("end of minor GC")) return GcGeneration.NEW;
        else if (gcAction.equals("end of major GC")) return GcGeneration.OLD;
        else return GcGeneration.UNKNOWN;
    }


    public static void printGcGenDuration(){
        System.out.println(ZonedDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm, dd-MM-yyyy").withZone(ZoneId.systemDefault())));
        gcGen.forEach((k, v)-> System.out.format("За последние 10 секунд на работу GC в поколении %s было затрачено %s ms%n",k,v));
        gcGen.clear();
    }

    /**
     * Запускает процесс мониторинга сборок мусора.
     */
    public static void startGCMonitor() {
        for(GarbageCollectorMXBean mBean: ManagementFactory.getGarbageCollectorMXBeans()) {
            ((NotificationEmitter) mBean).addNotificationListener(gcHandler, null, null);
        }
    }

    /**
     * Останавливает процесс мониторинга сборок мусора.
     */
    public static void stopGCMonitor() {
        for(GarbageCollectorMXBean mBean: ManagementFactory.getGarbageCollectorMXBeans()) {
            try {
                ((NotificationEmitter) mBean).removeNotificationListener(gcHandler);
            } catch(ListenerNotFoundException e){
                e.printStackTrace();
            }
        }
    }
}