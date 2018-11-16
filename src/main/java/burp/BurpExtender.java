package burp;

import com.soxfmr.jsloader.JavaScriptPayloadProcessor;
import com.soxfmr.jsloader.loader.Loader;
import com.soxfmr.jsloader.loader.NativeJavaScriptLoader;

import javax.swing.*;
import java.awt.Component;

public class BurpExtender implements IBurpExtender, ITab {

    private static final String EXTENSION_NAME = "JavaScript Loader";

    public void registerExtenderCallbacks(IBurpExtenderCallbacks callbacks) {
        Loader loader = new NativeJavaScriptLoader();

        callbacks.setExtensionName(EXTENSION_NAME);
        callbacks.registerIntruderPayloadProcessor(new JavaScriptPayloadProcessor(callbacks, loader, null));
    }

    @Override
    public String getTabCaption() {
        return EXTENSION_NAME;
    }

    @Override
    public Component getUiComponent() {
        return null;
    }
}
