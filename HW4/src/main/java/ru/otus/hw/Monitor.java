package ru.otus.hw;

import com.sun.management.GarbageCollectionNotificationInfo;

import javax.management.ListenerNotFoundException;
import javax.management.NotificationEmitter;
import javax.management.NotificationListener;
import javax.management.openmbean.CompositeData;
import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.stream.Collectors;

public class Monitor {

    private static List<Statistic> stats = new Vector<>();


    private static NotificationListener gcHandler = (notification, handback ) -> {
        if (notification.getType().equals(GarbageCollectionNotificationInfo.GARBAGE_COLLECTION_NOTIFICATION)) {
        GarbageCollectionNotificationInfo gcInfo = GarbageCollectionNotificationInfo.from((CompositeData) notification.getUserData());
            Statistic newStat = Statistic.newBuilder()
                    .setDateTime(ZonedDateTime.now())
                    .setDuration(gcInfo.getGcInfo().getDuration())
                    .setGcName(gcInfo.getGcName())
                    .setGeneration(parseGcAction(gcInfo.getGcAction()))
                    .build();
            stats.add(newStat);
    }};

    private static Generation parseGcAction(String gcAction) {
        if (gcAction.equals("end of minor GC")) return Generation.NEW;
        else if (gcAction.equals("end of major GC")) return Generation.OLD;
        else return Generation.UNKNOWN;
    }


    public synchronized static void printGcGenDuration(){
        System.out.println("----------------------------------------------------------------------------------------------------------------");
        System.out.println("-------------------------------------"+ ZonedDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss, dd-MM-yyyy").withZone(ZoneId.systemDefault()))+ "-------------------------------------");
        System.out.print("В эту минуту были использованы следующие сборщики мусора: ");
        stats.stream().map(Statistic::getGcName).distinct().forEach(t-> System.out.print(t + ", "));
        System.out.println();
        Map<String, List<Statistic>> gcStatMap = stats.stream().collect(Collectors.groupingBy(Statistic::getGcName));
        gcStatMap.forEach((k, v)->{
            System.out.println("Время работы сборщика " + k +" = " + v.stream().map(Statistic::getDuration).mapToLong(Long::longValue).sum() + "ms");
        });
        gcStatMap.forEach((k, v)->{
            System.out.println("Количество вызовов сборщика " + k +" = " + v.stream().count());
        });
        stats.stream().collect(Collectors.groupingBy(Statistic::getGeneration)).forEach((k, v)->{
            System.out.println("Время работы gc в поколении " + k +" = " + v.stream().map(Statistic::getDuration).mapToLong(Long::longValue).sum() + "ms");
        });
        System.out.println("----------------------------------------------------------------------------------------------------------------");
        stats.clear();
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