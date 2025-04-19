package pl.allegro.automate;

import io.vavr.control.Option;

import java.util.LinkedList;
import java.util.List;

public class Exchange {

    private final List<Object> inputs = new LinkedList<>();

    void addInput(Object value) {
        inputs.add(value);
    }

    public void expectNoParams() {
        if (!inputs.isEmpty()) {
            throw new RuntimeException("Expected no parameters got " + inputs);
        }
    }

    public <T> T getSingleParam() {
        if (inputs.size() != 1) {
            throw new RuntimeException("Expected 1 parameter got " + inputs.size() + " " + inputs);
        }
        return getNextParam();
    }

    public <T> T getNextParam() {
        return (T) inputs.remove(0);
    }

    public <T> Option<T> getLastOptionalParam() {
        if (inputs.size() != 1) {
            throw new RuntimeException("Expected last parameter got " + inputs);
        }
        return getNextOptionalParam();
    }

    public <T> Option<T> getNextOptionalParam() {
        return Option.when(!inputs.isEmpty(), () -> inputs.remove(0))
            .map(a -> (T) a);
    }

    public <T> io.vavr.collection.List<T> getAllParams(Class<T> tClass) {
        return inputs.stream()
            .map(tClass::cast)
            .collect(io.vavr.collection.List.collector());
    }

    @Override
    public String toString() {
        return inputs.toString();
    }
}
