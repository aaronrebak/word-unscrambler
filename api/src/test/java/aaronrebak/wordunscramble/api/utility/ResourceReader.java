package aaronrebak.wordunscramble.api.utility;

import static java.nio.charset.StandardCharsets.UTF_8;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UncheckedIOException;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.util.FileCopyUtils;

public final class ResourceReader {

  private ResourceReader() {

  }

  public static String readFileToString(final String path) {
    final ResourceLoader resourceLoader = new DefaultResourceLoader();
    final Resource resource = resourceLoader.getResource(path);
    return asString(resource);
  }

  private static String asString(final Resource resource) {
    try (final Reader reader = new InputStreamReader(resource.getInputStream(), UTF_8)) {
      return FileCopyUtils.copyToString(reader);
    } catch (final IOException exception) {
      throw new UncheckedIOException(exception);
    }
  }

}
