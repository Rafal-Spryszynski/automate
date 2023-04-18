package pl.allegro.automate.adapter.system.process;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;
import pl.allegro.automate.AutomationFlow;
import pl.allegro.automate.AutomationStep;
import pl.allegro.automate.AutomationStepEnumKey;
import pl.allegro.automate.AutomationStepKey;
import pl.allegro.automate.system.process.StartProcessAutomationStep;

@Module
public interface SystemModule {

    @Binds
    @AutomationStepKey(StartProcessAutomationStep.class)
    @IntoMap
    AutomationStep startProcessAutomationStep(StartSystemProcessAutomationStep automationStep);

    @Binds
    @AutomationStepEnumKey(AutomationFlow.Step.Code.START_PROCESS)
    @IntoMap
    AutomationStep bindStartProcessAutomationStep(StartSystemProcessAutomationStep automationStep);
}
