package ru.ospos.npf.commons;

public class SomeConfig {

    private final String url;

    public SomeConfig(String url) {
        this.url = url;
    }

    public String check() {
        return url;
    }
}
