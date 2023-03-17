package pl.allegro.automate;

import dagger.MapKey;

@MapKey
public @interface AutomationStepKey {

    Class<? extends AutomationStep> value();
}
