package pl.allegro.automate.adapter;

import dagger.BindsInstance;
import dagger.Component;
import pl.allegro.automate.Automate;
import pl.allegro.automate.adapter.awt.gui.AwtGuiComponent;
import pl.allegro.automate.adapter.flow.file.reader.AutomationFlowFileReader;
import pl.allegro.automate.adapter.json.jackson.JacksonJsonModule;
import pl.allegro.automate.adapter.logging.metrics.LoggingMetricsModule;
import pl.allegro.automate.adapter.system.console.ConsoleModule;
import pl.allegro.automate.adapter.system.flow.SystemFlowModule;
import pl.allegro.automate.adapter.system.process.SystemModule;
import pl.allegro.automate.flow.FlowModule;
import pl.allegro.automate.gui.GuiModule;
import pl.allegro.automate.record.AutomationRecorder;

import java.nio.file.Path;
import java.time.Duration;

@Component(
    modules = {
        AutomateModule.class,
        FlowModule.class,
        GuiModule.class,
        JacksonJsonModule.class,
        LoggingMetricsModule.class,
        ConsoleModule.class,
        SystemFlowModule.class,
        SystemModule.class
    },
    dependencies = AwtGuiComponent.class
)
interface AutomateComponent {

    AutomationFlowFileReader automationFlowFileReader();

    Automate automate();

    AutomationRecorder automationRecorder();

    @Component.Builder
    interface Builder {

        Builder awtGuiComponent(AwtGuiComponent awtGuiComponent);

        @BindsInstance
        Builder defaultSleepDuration(Duration duration);

        @BindsInstance
        Builder filesPath(Path filesPath);

        @BindsInstance
        Builder automationFileName(String automationFileName);

        AutomateComponent build();
    }
}
