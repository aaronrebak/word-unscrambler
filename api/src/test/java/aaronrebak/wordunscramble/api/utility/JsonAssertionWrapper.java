package aaronrebak.wordunscramble.api.utility;

import static com.trickl.assertj.core.api.JsonAssertions.assertThat;
import static com.trickl.assertj.core.api.JsonAssertions.json;

import com.trickl.assertj.core.api.json.JsonAssert;

public final class JsonAssertionWrapper {

  private JsonAssertionWrapper() {

  }

  public static JsonAssert thenJson(final String json) {
    return assertThat(json(json));
  }

}
