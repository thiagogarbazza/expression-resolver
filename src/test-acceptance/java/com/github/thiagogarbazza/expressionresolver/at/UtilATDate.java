package com.github.thiagogarbazza.expressionresolver.at;

import java.time.LocalDate;
import java.util.Collection;
import java.util.TreeSet;

import static com.github.thiagogarbazza.expressionresolver.util.LocalDateUtil.toLocalDate;
import static org.apache.commons.lang3.StringUtils.contains;
import static org.apache.commons.lang3.StringUtils.trimToNull;

public class UtilATDate {

  private static final String COLLECTION_SEPARATOR = ",";

  public static LocalDate stringToDate(final String date) {
    if (contains(date, "is today")) {
      return LocalDate.now();
    }

    return toLocalDate(trimToNull(date));
  }

  public static Collection<LocalDate> stringToDates(final String dates) {
    Collection<LocalDate> cDates = new TreeSet<>();

    for (String date : dates.split(COLLECTION_SEPARATOR)) {
      cDates.add(stringToDate(date));
    }

    return cDates;
  }
}
