package pl.allegro.automate;

import dagger.BindsInstance;
import dagger.Component;
import pl.allegro.automate.adapter.time.SystemTimeModule;
import pl.allegro.automate.gui.GuiModule;
import pl.allegro.automate.metrics.MetricsModule;
import pl.allegro.automate.os.OsModule;

@Component(modules = {
    GuiModule.class,
    MetricsModule.class,
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
