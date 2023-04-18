package pl.allegro.automate;

import dagger.MapKey;

@MapKey
public @interface AutomationStepEnumKey {

    AutomationFlow.Step.Code value();
}
