package org.valerochka1337.service.utils;

import org.springframework.stereotype.Component;

@Component
@FunctionalInterface
public interface RequestMessageFormatter {
  public String format(String message);
}
