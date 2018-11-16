package com.soxfmr.jsloader.loader;

public interface Loader {

    void addReference(String location, String charset);
    void addPlainCode(String code);
    String execute(String expression);

}
