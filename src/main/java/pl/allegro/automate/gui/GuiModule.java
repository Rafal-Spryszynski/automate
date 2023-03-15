package pl.allegro.automate.gui;

import dagger.Binds;
import dagger.Module;

@Module
public interface GuiModule {

    @Binds
    LoadImageCommand bindLoadImageCommand(LoadImageFromDiskCommand command);

    @Binds
    SaveImageCommand bindSaveImageCommand(SaveImageToDiskCommand command);

    @Binds
    TakeScreenCaptureCommand bindTakeScreenCaptureCommand(TakeDeviceScreenCaptureCommand command);
}
