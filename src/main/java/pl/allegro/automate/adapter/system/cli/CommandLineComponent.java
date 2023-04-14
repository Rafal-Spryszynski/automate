package pl.allegro.automate.adapter.system.cli;

import dagger.BindsInstance;
import dagger.Component;

@Component(modules = CommandLineModule.class)
public interface CommandLineComponent {

    ArgsParser argsParser();

    @Component.Factory
    interface Factory {

        CommandLineComponent create(@BindsInstance String[] args);
    }
}
