package pl.allegro.automate;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.immutables.value.Value;
import pl.allegro.automate.gui.ImmutableScreenLocation;

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
            MOUSE_CLICK,
            START_PROCESS
        }

        @JsonDeserialize(as = ImmutableArg.class)
        @Value.Immutable
        interface Arg {

            Type type();

            @JsonTypeInfo(
                use = Id.NAME,
                property = "type",
                defaultImpl = String.class
            )
            @JsonSubTypes({
                @JsonSubTypes.Type(value = ImmutableScreenLocation.class, name = "location")
            })
            Object value();

            enum Type {
                CONST
            }
        }
    }
}
