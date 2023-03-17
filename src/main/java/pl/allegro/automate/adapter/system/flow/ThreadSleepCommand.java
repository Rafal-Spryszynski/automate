package pl.allegro.automate.adapter.system.flow;

import io.vavr.control.Try;
import pl.allegro.automate.flow.SleepCommand;

import javax.inject.Inject;
import java.time.Duration;

class ThreadSleepCommand implements SleepCommand {

    @Inject
    ThreadSleepCommand() {}

    @Override
    public void run(Object... arguments) {
        Duration duration = (Duration) arguments[0];
        Try.run(() -> Thread.sleep(duration.toMillis())).get();
    }
}
