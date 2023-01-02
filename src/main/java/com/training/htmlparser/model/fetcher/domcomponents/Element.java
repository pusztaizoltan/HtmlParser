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
    private final Part part;

    public Element(Element parent, String htmlText) {
        String trimmedHtmlText = trimToElement(htmlText);
        this.parent = parent;
        this.part = Part.nextFrom(trimmedHtmlText);
        synchronized (this) {
            LOGGER.log(Level.INFO, logMessage(this.part, trimmedHtmlText));
        }
        trimmedHtmlText = trimmedHtmlText.substring(this.part.getPart().length());
        switch (this.part.getType()) {
            case ROOT_TAG, OPEN_TAG -> this.addChildFrom(trimmedHtmlText);
            case SELF_CLOSED_TAG, TEXT -> this.parent.addChildFrom(trimmedHtmlText);
            case CLOSE_TAG -> this.parent.parent.addChildFrom(trimmedHtmlText);
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
        return this.part.getName();
    }

    public boolean hasText() {
        return this.part.getType() == PartType.TEXT;
    }

    public String getText() {
        return hasText() ? this.part.getPart() : null;
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
                (parent == null ? "ROOT" : this.parent.nodeName()));
        message.append(progress);
        message.append("...");
        message.append(report);
        message.append("\u001B[0m");
        return message.toString();
    }
}
