package pl.allegro.automate.adapter;

import dagger.BindsInstance;
import dagger.Component;
import pl.allegro.automate.adapter.awt.gui.AwtGuiComponent;
import pl.allegro.automate.adapter.logging.metrics.LoggingMetricsModule;
import pl.allegro.automate.adapter.os.OsModule;
import pl.allegro.automate.adapter.system.flow.SystemFlowModule;
import pl.allegro.automate.flow.FlowModule;
import pl.allegro.automate.gui.GuiModule;

import java.time.Duration;

@Component(
    modules = {
        AutomateModule.class,
        FlowModule.class,
        GuiModule.class,
        LoggingMetricsModule.class,
        OsModule.class,
        SystemFlowModule.class
    },
    dependencies = AwtGuiComponent.class
)
interface AutomateComponent {

    Automate automate();

    @Component.Builder
    interface Builder {

        Builder awtGuiComponent(AwtGuiComponent awtGuiComponent);

        @BindsInstance
        Builder defaultSleepDuration(Duration duration);

        AutomateComponent build();
    }
}
