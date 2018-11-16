package com.soxfmr.jsloader.model;

public class JavaScriptFileInfo {

    private String location;
    private boolean loaded;
    private boolean enable;

    public JavaScriptFileInfo(String location) {
        this.location = location;
        this.loaded = false;
        this.enable = true;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public boolean isLoaded() {
        return loaded;
    }

    public void setLoaded(boolean loaded) {
        this.loaded = loaded;
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }
}
