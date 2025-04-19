package pl.allegro.automate.adapter.system.flow;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;
import pl.allegro.automate.AutomationFlow;
import pl.allegro.automate.AutomationStep;
import pl.allegro.automate.AutomationStepEnumKey;
import pl.allegro.automate.AutomationStepKey;
import pl.allegro.automate.flow.SleepAutomationStep;

@Module
public interface SystemFlowModule {

    @Binds
    SleepAutomationStep bindThreadSleepAutomationStep(ThreadSleepAutomationStep automationStep);

    @Binds
    @AutomationStepKey(SleepAutomationStep.class)
    @IntoMap
    AutomationStep bindSleepStep(ThreadSleepAutomationStep automationStep);

    @Binds
    @AutomationStepEnumKey(AutomationFlow.Step.Code.SLEEP)
    @IntoMap
    AutomationStep bindSleepAutomationStep(ThreadSleepAutomationStep automationStep);
}
