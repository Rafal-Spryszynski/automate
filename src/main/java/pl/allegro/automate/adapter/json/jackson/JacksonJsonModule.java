package pl.allegro.automate.adapter.json.jackson;

import com.fasterxml.jackson.databind.ObjectMapper;
import dagger.Module;
import dagger.Provides;

@Module
public interface JacksonJsonModule {

    @Provides
    static ObjectMapper objectMapper() {
        return new ObjectMapper();
    }
}
