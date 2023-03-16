package pl.allegro.automate;

import dagger.BindsInstance;
import dagger.Component;
import pl.allegro.automate.adapter.logging.metrics.LoggingMetricsModule;
import pl.allegro.automate.adapter.os.OsModule;
import pl.allegro.automate.adapter.system.time.SystemTimeModule;
import pl.allegro.automate.gui.GuiModule;

@Component(modules = {
    GuiModule.class,
    LoggingMetricsModule.class,
    OsModule.class,
    SystemTimeModule.class
})
interface AutomateComponent {

    Automate automate();

    @Component.Builder
    interface Builder {

        @BindsInstance
        Builder saveScreenCaptures(boolean saveScreenCaptures);

        AutomateComponent build();
    }
}
