package pl.allegro.automate.adapter.system.console;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;
import pl.allegro.automate.AutomationStep;
import pl.allegro.automate.AutomationStepKey;
import pl.allegro.automate.system.console.ConsoleAutomationStep;

@Module
public interface ConsoleModule {

    @Binds
    @AutomationStepKey(ConsoleAutomationStep.class)
    @IntoMap
    AutomationStep consoleAutomationStep(SystemConsoleAutomationStep automationStep);
}
