package ua.edu.viti.military.listener;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import ua.edu.viti.military.event.SupplyMovementEvent;
import ua.edu.viti.military.service.MetricsService;

@Component
@Slf4j
@RequiredArgsConstructor
public class SupplyMovementEventListener {

    private final MetricsService metricsService;

    @EventListener
    public void handleSupplyMovementEvent(SupplyMovementEvent event) {
        log.info("Supply movement recorded: ID={}, Item={}, Amount={}, Type={}, Time={}",
                event.getMovementId(), event.getItemId(), event.getAmount(), event.getType(), event.getTimestamp());

        switch (event.getType()) {
            case ISSUE:
                metricsService.incrementIssued();
                break;
            case RETURN:
                metricsService.incrementReturned();
                break;
            case RECEIPT:
                metricsService.incrementReceived();
                break;
            case WRITE_OFF:
                metricsService.incrementWrittenOff();
                break;
        }
    }
}
