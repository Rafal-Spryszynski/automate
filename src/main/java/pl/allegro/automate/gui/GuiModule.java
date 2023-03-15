package pl.allegro.automate.gui;

import dagger.Binds;
import dagger.Module;

@Module
public interface GuiModule {

    @Binds
    LoadImageCommand bindLoadImageCommand(LoadImageFromDiskCommand command);

    @Binds
    TakeScreenCaptureCommand bindTakeScreenCaptureCommand(TakeDeviceScreenCaptureCommand command);
}
