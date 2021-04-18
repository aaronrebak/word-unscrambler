package aaronrebak.wordunscramble.api.config;

import aaronrebak.wordunscramble.api.data.repository.DictionaryRepository;
import aaronrebak.wordunscramble.api.data.repository.DictionaryRepositoryInMemory;
import com.google.common.io.Files;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

@Configuration
public class DictionaryRepositoryBean {

  private final Resource resource;

  public DictionaryRepositoryBean(@Value("classpath:dictionary.txt") final Resource resource) {
    this.resource = resource;
  }

  @Bean
  public DictionaryRepository dictionaryRepository() throws IOException {
    final List<String> dictionary = Files
        .readLines(this.resource.getFile(), StandardCharsets.UTF_8);
    return new DictionaryRepositoryInMemory(dictionary);
  }

}
