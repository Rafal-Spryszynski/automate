package pl.allegro.automate.adapter.system.process;

import io.vavr.control.Try;
import pl.allegro.automate.Exchange;
import pl.allegro.automate.system.process.StartProcessAutomationStep;

import javax.inject.Inject;
import java.nio.file.Path;
import java.nio.file.Paths;

class StartSystemProcessAutomationStep implements StartProcessAutomationStep {

    @Inject
    StartSystemProcessAutomationStep() {}

    @Override
    public void execute(Exchange exchange) {
        String path = exchange.getSingleParam();
        startProcess(Paths.get(path));
    }

    @Override
    public void startProcess(Path path) {
        Try.run(() ->
            new ProcessBuilder(path.toString())
                .start()
        ).get();
    }
}
