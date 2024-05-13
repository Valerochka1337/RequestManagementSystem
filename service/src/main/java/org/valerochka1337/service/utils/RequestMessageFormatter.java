package org.valerochka1337.service.utils;


@FunctionalInterface
public interface RequestMessageFormatter {
  public String format(String message);
}
