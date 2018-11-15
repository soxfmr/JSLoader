package com.soxfmr.jsloader;

public interface Loader {

    void addReference(String location, String charset);
    void addPlainCode(String code);
    String execute(String expression);

}
