package com.soxfmr.jsloader;

import burp.IBurpExtenderCallbacks;
import burp.IExtensionHelpers;
import burp.IIntruderPayloadProcessor;
import com.soxfmr.jsloader.loader.Loader;
import com.soxfmr.jsloader.model.PayloadInfo;

import java.util.List;

public class JavaScriptPayloadProcessor implements IIntruderPayloadProcessor {

    private static final String PROCESSOR_NAME = "JavaScript Payload Processor";

    private static final String ARGUMENT_PLACEHOLDER = "\\$\\{\\{\\{.*?\\}\\}\\}";

    private IExtensionHelpers helpers;
    private IBurpExtenderCallbacks callbacks;
    private Loader loader;
    private List<PayloadInfo> payloadInfoList;

    public JavaScriptPayloadProcessor(IBurpExtenderCallbacks callbacks, Loader loader, List<PayloadInfo> payloadInfoList) {
        this.callbacks = callbacks;
        this.helpers = callbacks.getHelpers();
        this.loader = loader;
        this.payloadInfoList = payloadInfoList;
    }

    @Override
    public String getProcessorName() {
        return PROCESSOR_NAME;
    }

    @Override
    public byte[] processPayload(byte[] currentPayload, byte[] originalPayload, byte[] baseValue) {
        String expression;
        String argument = helpers.bytesToString(helpers.urlDecode(currentPayload));
        String payloadName = helpers.bytesToString(helpers.urlDecode(baseValue));

        for (PayloadInfo info : payloadInfoList) {
            if (info.getName().equals(payloadName)) {
                expression = evaluate(info.getExpression(), argument);
                argument = loader.execute(expression);
                break;
            }
        }

        return helpers.stringToBytes(helpers.urlEncode(argument));
    }

    private String evaluate(String expression, String argument) {
        return expression.replaceAll(ARGUMENT_PLACEHOLDER, argument);
    }

}
