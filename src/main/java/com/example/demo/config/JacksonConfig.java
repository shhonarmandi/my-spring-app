package com.example.demo.config;

import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.cfg.CoercionAction;
import com.fasterxml.jackson.databind.cfg.CoercionInputShape;
import com.fasterxml.jackson.databind.type.LogicalType;

@Configuration
public class JacksonConfig {
  @Bean
  Jackson2ObjectMapperBuilderCustomizer strictJson() {
    return builder ->
        builder.postConfigurer(
            (ObjectMapper mapper) -> {
              // reject numbers/booleans for String fields
              mapper
                  .coercionConfigFor(LogicalType.Textual)
                  .setCoercion(CoercionInputShape.Integer, CoercionAction.Fail)
                  .setCoercion(CoercionInputShape.Float, CoercionAction.Fail)
                  .setCoercion(CoercionInputShape.Boolean, CoercionAction.Fail);
              // fail on unknown properties
              mapper.enable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
            });
  }
}
