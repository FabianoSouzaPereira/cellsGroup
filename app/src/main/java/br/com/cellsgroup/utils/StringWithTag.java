package br.com.cellsgroup.utils;

public abstract class StringWithTag {
    public String string;
    public Object tag;

    public StringWithTag(Object tag, String string) {
        this.string = string;
        this.tag = tag;
    }

    @Override
    public String toString() {
        return string + "(" + tag + ")";
    }
}
