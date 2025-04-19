package pl.allegro.automate;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.immutables.value.Value;
import pl.allegro.automate.gui.ImmutableScreenLocation;

import java.time.Duration;
import java.util.List;
import java.util.Optional;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_ABSENT;

@JsonDeserialize(as = ImmutableAutomationFlow.class)
@Value.Immutable
public interface AutomationFlow {

    List<Step> steps();

    @JsonDeserialize(as = ImmutableStep.class)
    @Value.Immutable
    interface Step {

        Code code();

        List<Arg> args();

        @JsonInclude(NON_ABSENT)
        Optional<String> label();

        enum Code {
            MOUSE_CLICK,
            START_PROCESS,
            TYPE_CHARS,
            SLEEP,
        }

        @JsonDeserialize(as = ImmutableArg.class)
        @Value.Immutable
        interface Arg {

            @Value.Default
            default Type type() {
                return Type.CONST;
            }

            @JsonTypeInfo(
                use = Id.NAME,
                property = "type",
                defaultImpl = String.class
            )
            @JsonSubTypes({
                @JsonSubTypes.Type(value = ImmutableScreenLocation.class, name = "location"),
                @JsonSubTypes.Type(value = Duration.class, name = "duration"),
            })
            @Value.Parameter
            Object value();

            enum Type {
                CONST
            }
        }
    }
}
