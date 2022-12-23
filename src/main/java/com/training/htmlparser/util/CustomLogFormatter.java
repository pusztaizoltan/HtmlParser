package com.training.htmlparser.util;

import java.time.LocalDateTime;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;

public class CustomLogFormatter extends Formatter {
    @Override
    public String format(LogRecord record) {
        String pattern = "%s Thread:%s Level:%s Message:%s\n";
        return String.format(pattern, LocalDateTime.now(), record.getThreadID(), record.getLevel(), record.getMessage());
    }
}
