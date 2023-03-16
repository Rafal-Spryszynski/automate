package pl.allegro.automate;

import dagger.BindsInstance;
import dagger.Component;
import pl.allegro.automate.adapter.awt.gui.AwtGuiModule;
import pl.allegro.automate.adapter.logging.metrics.LoggingMetricsModule;
import pl.allegro.automate.adapter.os.OsModule;
import pl.allegro.automate.adapter.system.flow.SystemFlowModule;
import pl.allegro.automate.adapter.system.time.SystemTimeModule;

import java.nio.file.Path;

@Component(modules = {
    AwtGuiModule.class,
    LoggingMetricsModule.class,
    OsModule.class,
    SystemFlowModule.class,
    SystemTimeModule.class
})
interface AutomateComponent {

    Automate automate();

    @Component.Builder
    interface Builder {

        @BindsInstance
        Builder imagesPath(Path path);

        @BindsInstance
        Builder saveScreenCaptures(boolean saveScreenCaptures);

        AutomateComponent build();
    }
}
