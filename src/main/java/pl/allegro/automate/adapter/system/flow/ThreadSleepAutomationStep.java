package pl.allegro.automate.adapter.system.flow;

import io.vavr.control.Try;
import pl.allegro.automate.Exchange;
import pl.allegro.automate.flow.SleepAutomationStep;

import javax.inject.Inject;
import java.time.Duration;

class ThreadSleepAutomationStep implements SleepAutomationStep {

    @Inject
    ThreadSleepAutomationStep() {}

    @Override
    public void execute(Exchange exchange) {
        Duration duration = exchange.getSingleParam();
        sleep(duration);
    }

    @Override
    public void sleep(Duration duration) {
        Try.run(() -> Thread.sleep(duration.toMillis())).get();
    }
}
