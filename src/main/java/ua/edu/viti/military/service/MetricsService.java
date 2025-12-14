package ua.edu.viti.military.service;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class MetricsService {

    private final MeterRegistry meterRegistry;

    // Counters
    private final Counter itemsIssuedCounter;
    private final Counter itemsReturnedCounter;
    private final Counter itemsReceivedCounter;
    private final Counter itemsWrittenOffCounter;

    // Timers
    private final Timer movementOperationTimer;

    public MetricsService(MeterRegistry meterRegistry) {
        this.meterRegistry = meterRegistry;

        this.itemsIssuedCounter = Counter.builder("warehouse.items.issued")
                .description("Total number of items issued")
                .tag("type", "supply")
                .register(meterRegistry);

        this.itemsReturnedCounter = Counter.builder("warehouse.items.returned")
                .description("Total number of items returned")
                .tag("type", "supply")
                .register(meterRegistry);

        this.itemsReceivedCounter = Counter.builder("warehouse.items.received")
                .description("Total number of items received")
                .tag("type", "supply")
                .register(meterRegistry);

        this.itemsWrittenOffCounter = Counter.builder("warehouse.items.written_off")
                .description("Total number of items written off")
                .tag("type", "supply")
                .register(meterRegistry);

        this.movementOperationTimer = Timer.builder("warehouse.operations.movement.duration")
                .description("Time taken to process movement")
                .tag("operation", "movement")
                .register(meterRegistry);
    }

    public void incrementIssued() {
        itemsIssuedCounter.increment();
    }

    public void incrementReturned() {
        itemsReturnedCounter.increment();
    }

    public void incrementReceived() {
        itemsReceivedCounter.increment();
    }

    public void incrementWrittenOff() {
        itemsWrittenOffCounter.increment();
    }

    public void recordMovementTime(long time, TimeUnit unit) {
        movementOperationTimer.record(time, unit);
    }
}
