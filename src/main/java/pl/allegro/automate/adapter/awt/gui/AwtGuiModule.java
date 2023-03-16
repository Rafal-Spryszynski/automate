package pl.allegro.automate.adapter.awt.gui;

import dagger.Binds;
import dagger.Module;
import pl.allegro.automate.gui.LoadImageCommand;
import pl.allegro.automate.gui.SendMouseClickCommand;
import pl.allegro.automate.gui.TakeScreenCaptureCommand;

@Module
public interface AwtGuiModule {

    @Binds
    LoadImageCommand bindLoadImageCommand(LoadImageFromDiskCommand command);

    @Binds
    TakeScreenCaptureCommand bindTakeScreenCaptureCommand(TakeDeviceScreenCaptureCommand command);

    @Binds
    SendMouseClickCommand bindSendMouseClickCommand(SendDeviceMouseClickCommand command);
}
