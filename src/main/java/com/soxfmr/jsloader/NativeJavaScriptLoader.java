package com.soxfmr.jsloader;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class NativeJavaScriptLoader implements Loader {

    private int numOfWorker;
    private ScriptEngine engine;

    public NativeJavaScriptLoader() {
        ScriptEngineManager manager = new ScriptEngineManager();
        engine = manager.getEngineByName("JavaScript");
    }

    public void addReference(final String location, final String charset) {
        Thread thread = new Thread() {

            public void run() {
                URL url;
                URLConnection conn;
                BufferedInputStream is = null;

                String encoding, content;

                int read;
                byte[] bytes = new byte[1024];
                ByteArrayOutputStream baos = new ByteArrayOutputStream();

                try {
                    url = new URL(location);
                    conn = url.openConnection();

                    // Read byte-by-byte
                    is = new BufferedInputStream(conn.getInputStream());
                    while ((read = is.read(bytes, 0, bytes.length)) != -1) {
                        baos.write(bytes, 0, read);
                    }

                    // Adjust the encoding
                    encoding = charset != null ? charset : conn.getContentEncoding();
                    content = new String(baos.toByteArray(), encoding);

                    // Evaluate code
                    addPlainCode(content);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    // Release connection
                    try {
                        if (is != null) {
                            is.close();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                synchronized (NativeJavaScriptLoader.this) {
                    numOfWorker -= 1;
                }
            }

        };

        numOfWorker += 1;
        thread.start();
    }

    public boolean isLoading() {
        return numOfWorker > 0;
    }

    public void addPlainCode(String code) {
        try {
            engine.eval(code);
        } catch (ScriptException e) {
            e.printStackTrace();
        }
    }

    public String execute(String expression) {
        String result = "";

        try {
            result = (String) engine.eval(expression);
        } catch (ScriptException e) {
            e.printStackTrace();
        }

        return result;
    }

    public static void main(String args[]) {
        NativeJavaScriptLoader loader = new NativeJavaScriptLoader();
        // loader.addPlainCode("function add(i) { return i * i; }");
        loader.addPlainCode("var window = {};");
        loader.addReference("http://192.168.32.1:8000/index.js", "utf-8");
        loader.addReference("https://raw.githubusercontent.com/HuangZhiAn/HmdmPortals/master/src/main/webapp/static/js/security.js", "utf-8");
        loader.addReference("https://seleniumhq.github.io/selenium/docs/api/java/script.js", "utf-8");
        while (true) {
            if (! loader.isLoading()) {
                break;
            }

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        String result = loader.execute("var key = window.RSAUtils.getKeyPair('010001', '', '00c99f8aab91bccb525d6ea16c40a8eb31dfa9594d1b487167a96d24c8e2d48ed1e9d759f3d1425728016cfffb9b42311108dc6b1f749e6902d6bc072d0533adc489e87eecd980590460fe62290b6a6210e5584919bf85a9cb7b02ebb3ef89aa0a61120dc3cfac1af480905bf269890654cc94e44512b2adc2bb6054cd15971061');window.RSAUtils.encryptedString(key, 'admin');");
        System.out.println(result);
    }

}
