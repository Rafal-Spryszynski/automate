package pl.allegro.automate;

import dagger.Component;
import pl.allegro.automate.gui.GuiModule;
import pl.allegro.automate.metrics.MetricsModule;
import pl.allegro.automate.os.OsModule;

@Component(modules = {
    GuiModule.class,
    MetricsModule.class,
    OsModule.class
})
interface AutomateComponent {

    Automate automate();
}
