package burp;

import com.soxfmr.jsloader.JavaScriptPayloadProcessor;
import com.soxfmr.jsloader.Loader;
import com.soxfmr.jsloader.NativeJavaScriptLoader;
import com.soxfmr.jsloader.PayloadInfo;

import java.awt.Component;
import java.util.ArrayList;
import java.util.List;

public class BurpExtender implements IBurpExtender, ITab {

    private static final String EXTENSION_NAME = "JavaScript Loader";

    private List<PayloadInfo> payloadInfoList = new ArrayList<PayloadInfo>();

    public void registerExtenderCallbacks(IBurpExtenderCallbacks callbacks) {
        Loader loader = new NativeJavaScriptLoader();

        callbacks.setExtensionName(EXTENSION_NAME);
        callbacks.registerIntruderPayloadProcessor(new JavaScriptPayloadProcessor(callbacks, loader, payloadInfoList));
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
