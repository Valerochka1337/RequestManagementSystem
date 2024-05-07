package org.valerochka1337.service.utils;

import org.springframework.stereotype.Component;

@Component()
public class DashedRequestMessageFormatterImpl implements RequestMessageFormatter {

  @Override
  public String format(String message) {
    StringBuilder resultString = new StringBuilder();
    for (int i = 0; i < message.length() - 1; i++) {
      resultString.append(message.charAt(i));
      resultString.append('-');
    }
    resultString.append(message.charAt(message.length() - 1));

    return resultString.toString();
  }
}
