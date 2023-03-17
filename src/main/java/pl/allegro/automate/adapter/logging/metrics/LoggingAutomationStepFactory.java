package pl.allegro.automate.adapter.logging.metrics;

import org.apache.commons.lang3.ArrayUtils;
import pl.allegro.automate.AutomationStep;
import pl.allegro.automate.metrics.Metrics;

import javax.inject.Inject;
import java.lang.reflect.Proxy;

import static org.apache.commons.lang3.ArrayUtils.toArray;

public class LoggingAutomationStepFactory {

    private final Metrics metrics;

    @Inject
    LoggingAutomationStepFactory(Metrics metrics) {
        this.metrics = metrics;
    }

    public AutomationStep decorate(Class<? extends AutomationStep> automationStepKey, AutomationStep automationStep) {
        String automationStepName = automationStep.getClass().getSimpleName();
        return (AutomationStep) Proxy.newProxyInstance(
            this.getClass().getClassLoader(),
            toArray(automationStepKey),
            (proxy, method, args) ->
                metrics.measure(
                    "{0} {1}",
                    () -> method.invoke(automationStep, args),
                    automationStepName,
                    ArrayUtils.toString(args)
                )
        );
    }
}
