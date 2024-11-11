package com.agrotech.service;

import com.fazecast.jSerialComm.SerialPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class TimeSyncService {
    private static final Logger logger = LoggerFactory.getLogger(TimeSyncService.class);
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private final SerialPort serialPort;
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    public TimeSyncService(SerialPort serialPort) {
        this.serialPort = serialPort;
    }

    public void startTimeSync() {
        //Send initial time sync
        sendCurrentTime();

        //Schedule periodic time syncs every minute
        scheduler.scheduleAtFixedRate(
                this::sendCurrentTime,
                1,
                60,
                TimeUnit.SECONDS
        );
    }

    private void sendCurrentTime() {
        try {
            String timestamp = LocalDateTime.now().format(formatter);
            String message = "T:" + timestamp + "\n";
            serialPort.writeBytes(message.getBytes(), message.length());
            logger.debug("Time synced: {}", timestamp);
        } catch (Exception e) {
            logger.error("Error syncing time", e);
        }
    }

    public void stop() {
        scheduler.shutdown();
        try {
            if(!scheduler.awaitTermination(800, TimeUnit.MICROSECONDS)) {
                scheduler.shutdownNow();
            }
        } catch (InterruptedException e) {
            scheduler.shutdownNow();
        }
    }

}
