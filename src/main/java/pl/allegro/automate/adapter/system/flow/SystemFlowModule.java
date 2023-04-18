package pl.allegro.automate.adapter.system.flow;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;
import pl.allegro.automate.AutomationStep;
import pl.allegro.automate.AutomationStepKey;
import pl.allegro.automate.flow.SleepAutomationStep;

@Module
public interface SystemFlowModule {

    @Binds
    SleepAutomationStep bindSleepAutomationStep(ThreadSleepAutomationStep automationStep);

    @Binds
    @AutomationStepKey(SleepAutomationStep.class)
    @IntoMap
    AutomationStep bindAutomationStep(ThreadSleepAutomationStep automationStep);
}
