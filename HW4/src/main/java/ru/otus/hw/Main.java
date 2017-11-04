package ru.otus.hw;

import java.lang.reflect.Array;
import java.util.*;

import static java.lang.System.*;

public class Main {

    /*
 -agentlib:jdwp=transport=dt_socket,address=14000,server=y,suspend=n
 -Xms512m
 -Xmx512m
 -XX:MaxMetaspaceSize=256m
 -XX:+UseConcMarkSweepGC
 -XX:+CMSParallelRemarkEnabled
 -XX:+UseCMSInitiatingOccupancyOnly
 -XX:CMSInitiatingOccupancyFraction=70
 -XX:+ScavengeBeforeFullGC
 -XX:+CMSScavengeBeforeRemark
 -XX:+UseParNewGC
 -verbose:gc
 -XX:+PrintTenuringDistribution
 -Xloggc:./logs/gc_pid_%p.log
 -XX:+PrintGCDateStamps
 -XX:+PrintGCDetails
 -XX:+UseGCLogFileRotation
 -XX:NumberOfGCLogFiles=10
 -XX:GCLogFileSize=1MFF
 -Dcom.sun.management.jmxremote.port=15000
 -Dcom.sun.management.jmxremote.authenticate=false
 -Dcom.sun.management.jmxremote.ssl=false
 -XX:+HeapDumpOnOutOfMemoryError
 -XX:HeapDumpPath=./dumps/

 jps -- list vms
 jstack <pid> >> threaddumps.log -- get dump from pid
 jinfo -- list VM parameters
 jhat / jvisualvm -- analyze heap dump
 */

    static List<String> lists = new ArrayList<>();
    private static volatile boolean stopWhile = false;

    public static void main(String... args) throws InterruptedException {
        Thread th = new Thread(new Runnable() {
            @Override
            public void run() {
                int count = 0;
                while (true) {
                    try {
                        Thread.sleep(10000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if (count == 50) {
                        MemoryUtil.printUsage(false);
                        stopWhile = true;
                        break;
                    }
                    MemoryUtil.printUsage(false);
                    count++;
                }
            }
        });
        th.start();

        while (true) {
            Thread.sleep(10);
            String[]  arr = new String[100000000];
            Arrays.fill(arr, UUID.randomUUID().toString());
            Collections.addAll(Arrays.asList(arr));
            if (stopWhile) break;
        }

        out.println(lists);
        out.println(lists.get(0));
    }
}
