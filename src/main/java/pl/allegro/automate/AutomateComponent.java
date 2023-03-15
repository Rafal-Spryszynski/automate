package pl.allegro.automate;

import dagger.Component;
import pl.allegro.automate.os.OsModule;

@Component(modules = OsModule.class)
interface AutomateComponent {

    Automate automate();
}
