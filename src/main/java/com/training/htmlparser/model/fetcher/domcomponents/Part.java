package com.training.htmlparser.model.fetcher.domcomponents;

import lombok.Getter;

@Getter
public class Part {
    private final String part;
    private String name;
    private PartType type;

    private Part(String part) {
        this.part = part;
        autoType(part);
        autoName(part);
    }

    public static Part nextFrom(String htmlText) {
        if (htmlText.startsWith("<")) {
            int tagEnd = htmlText.indexOf('>') + 1;
            return new Part(htmlText.substring(0, tagEnd));
        } else if (htmlText.contains("<")) {
            int tagEnd = htmlText.indexOf('<');
            return new Part(htmlText.substring(0, tagEnd));
        } else {
            return new Part("");
        }
    }

    private void autoType(String part) {
        if (part.equals("<!DOCTYPE html>")) {
            this.type = PartType.ROOT_TAG;
        } else if (part.equals("")) {
            this.type = PartType.END;
        } else if (part.endsWith("/>")) {
            this.type = PartType.SELF_CLOSED_TAG;
        } else if (part.startsWith("</")) {
            this.type = PartType.CLOSE_TAG;
        } else if (!part.contains("<") && !part.contains(">")) {
            this.type = PartType.TEXT;
        } else {
            this.type = PartType.OPEN_TAG;
        }
    }

    private void autoName(String part) {
        if (this.type == PartType.TEXT) {
            this.name = part;
        } else if (this.type == PartType.ROOT_TAG) {
            this.name = "!DOCUMENT";
        } else if (this.type == PartType.END) {
            this.name = "!END";
        } else if (this.type == PartType.CLOSE_TAG) {
            this.name = part.substring(2).replace(">", "");
        } else {
            this.name = part.replaceAll(" .*>$", "").replace(">", "").substring(1);
        }
    }

    @Override
    public String toString() {
        return this.part;
    }
}
