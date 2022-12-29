package com.training.htmlparser.model.fetcher.domcomponents;

import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Custom DOM element in contrast of standard DOM include
 * - text-content as child of open-tag element with "!TEXT" name
 * - close-tag as last child of open-tag element with "_!CLOSED" name-suffix
 * - in line format-tag as child of open-tag element
 */
public class Element {
    private static final Logger LOGGER = Logger.getLogger(Element.class.getName());
    private final List<Element> children = new LinkedList<>();
    private final Element parent;
    private String name;
    private String text;

    public Element(Element parent, String htmlText) {
        this.parent = parent;
        String trimmedHtmlText = trimToElement(htmlText);
        Part nextPart = Part.nextFrom(trimmedHtmlText);
        synchronized (this) {
            LOGGER.log(Level.INFO, logMessage(nextPart, trimmedHtmlText));
        }
        trimmedHtmlText = trimmedHtmlText.substring(nextPart.getPart().length());
        switch (nextPart.getType()) {
            case ROOT_TAG, OPEN_TAG -> {
                this.name = nextPart.getName();
                this.addChildFrom(trimmedHtmlText);
            }
            case SELF_CLOSED_TAG -> {
                this.name = nextPart.getName();
                this.parent.addChildFrom(trimmedHtmlText);
            }
            case TEXT -> {
                this.name = "!TEXT";
                this.text = nextPart.getPart();
                this.parent.addChildFrom(trimmedHtmlText);
            }
            case CLOSE_TAG -> {
                this.name = nextPart.getName() + "_!CLOSED";
                this.parent.parent.addChildFrom(trimmedHtmlText);
            }
            case END -> this.parent.children.remove(this);
        }
    }

    private void addChildFrom(String trimmedHtmlText) {
        Element child = new Element(this, trimmedHtmlText);
        this.children.add(child);
    }

    private String trimToElement(String htmlText) {
        return htmlText.replaceAll("^[ \n]*", "");
    }

    public String nodeName() {
        return this.name;
    }

    public boolean hasText() {
        return this.text != null;
    }

    public String getText() {
        return this.text;
    }

    public List<Element> getChildren() {
        return this.children;
    }

    private String logMessage(Part nextPart, String trimmedHtmlText) {
        StringBuilder message = new StringBuilder("\u001B[32m" + "\n|\t\t");
        String progress = trimmedHtmlText.substring(0, Math.min(75, trimmedHtmlText.length())).replaceAll("\n", "\n|\t\t");
        String report = String.format("\n| NEXT_PART: %s\n| TYPE:      %s\n| PARENT:    %s",
                nextPart.getPart().substring(0, Math.min(40, nextPart.getPart().length())).replace("\n", " ").replace("  ", " ").trim(),
                nextPart.getType(),
                (parent == null ? "ROOT" : this.parent.name));
        message.append(progress);
        message.append("...");
        message.append(report);
        message.append("\u001B[0m");
        return message.toString();
    }
}
