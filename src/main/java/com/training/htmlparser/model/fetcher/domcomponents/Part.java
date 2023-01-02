package com.training.htmlparser.model.fetcher.domcomponents;

import lombok.Getter;

@Getter
public class Part {
    private final String part;
    private String name;
    private PartType type;

    private Part(String part) {
        this.part = part;
        autoInit(part);

    }
    // todo does it need syncronization?
    public synchronized static Part nextFrom(String htmlText) {
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

    private void autoInit(String part) {
        if (part.equals("<!DOCTYPE html>")) {
            this.type = PartType.ROOT_TAG;
            this.name = "!DOCUMENT";
        } else if (part.equals("")) {
            this.type = PartType.END;
            this.name = "!END";
        } else if (!part.contains("<") && !part.contains(">")) {
            this.type = PartType.TEXT;
            this.name = "!TEXT";
        } else {
            String baseName = part.replaceAll("( .*>$)|( />$)|(>$)", "").replace(">", "").substring(1);
            if (part.startsWith("</")) {
                this.type = PartType.CLOSE_TAG;
                this.name = baseName + "_!CLOSED";
            } else if (part.endsWith("/>")){
                this.type = PartType.SELF_CLOSED_TAG;
                this.name = baseName + "_SELF!CLOSED";
            } else {
                this.type = PartType.OPEN_TAG;
                this.name = baseName;
            }
        }
    }

    @Override
    public String toString() {
        return this.part;
    }
}
