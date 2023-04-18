package pl.allegro.automate;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.immutables.value.Value;

import java.util.List;

@JsonDeserialize(as = ImmutableAutomationFlow.class)
@Value.Immutable
public interface AutomationFlow {

    List<Step> steps();

    @JsonDeserialize(as = ImmutableStep.class)
    @Value.Immutable
    interface Step {

        Code code();

        List<Arg> args();

        enum Code {
            START_PROCESS
        }

        @JsonDeserialize(as = ImmutableArg.class)
        @Value.Immutable
        interface Arg {

            Type type();

            String value();

            enum Type {
                CONST
            }
        }
    }
}
