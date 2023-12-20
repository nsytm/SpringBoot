// package com.example.all.config;
//
// import com.fasterxml.jackson.databind.JsonDeserializer;
// import com.fasterxml.jackson.databind.JsonSerializer;
// import com.fasterxml.jackson.databind.ObjectMapper;
// import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
// import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
// import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
// import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
// import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
// import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
// import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
// import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
// import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
//
// import java.time.LocalDate;
// import java.time.LocalDateTime;
// import java.time.LocalTime;
// import java.time.format.DateTimeFormatter;
// import java.util.HashMap;
// import java.util.Map;
//
// /**
//  * @author pgl
//  * @description 描述
//  * @date 2023/8/15
//  */
// @Configuration
// public class LocalDateTimeSerializerConfig {
//     private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
//     private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
//     private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm:ss");
//
//     @Bean
//     public Jackson2ObjectMapperBuilderCustomizer jackson2ObjectMapperBuilderCustomizer() {
//         System.out.println("配置类型执行了!");
//         Map<Class<?>, JsonSerializer<?>> serializers = new HashMap<>(3);
//         serializers.put(LocalDateTime.class, new LocalDateTimeSerializer(DATE_TIME_FORMATTER));
//         serializers.put(LocalDate.class, new LocalDateSerializer(DATE_FORMATTER));
//         serializers.put(LocalTime.class, new LocalTimeSerializer(TIME_FORMATTER));
//         Map<Class<?>, JsonDeserializer<?>> deserializers = new HashMap<>(3);
//         deserializers.put(LocalDateTime.class, new LocalDateTimeDeserializer(DATE_TIME_FORMATTER));
//         deserializers.put(LocalDate.class, new LocalDateDeserializer(DATE_FORMATTER));
//         deserializers.put(LocalTime.class, new LocalTimeDeserializer(TIME_FORMATTER));
//         return builder -> builder.serializersByType(serializers).deserializersByType(deserializers);
//     }
//
//     @Bean
//     public ObjectMapper objectMapper() {
//         ObjectMapper objectMapper = new ObjectMapper();
//         // objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
//         // objectMapper.disable(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE);
//         JavaTimeModule javaTimeModule = new JavaTimeModule();
//
//         javaTimeModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(DATE_TIME_FORMATTER));
//         // javaTimeModule.addSerializer(LocalDate.class,new LocalDateSerializer(DateTimeFormatter.ofPattern(Constants.DateTime.DEFAULT_DATE_FORMAT)));
//         // javaTimeModule.addSerializer(LocalTime.class,new LocalTimeSerializer(DateTimeFormatter.ofPattern(Constants.DateTime.DEFAULT_TIME_FORMAT)));
//
//         javaTimeModule.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(DATE_TIME_FORMATTER));
//         // javaTimeModule.addDeserializer(LocalDate.class,new LocalDateDeserializer(DateTimeFormatter.ofPattern(Constants.DateTime.DEFAULT_DATE_FORMAT)));
//         // javaTimeModule.addDeserializer(LocalTime.class,new LocalTimeDeserializer(DateTimeFormatter.ofPattern(Constants.DateTime.DEFAULT_TIME_FORMAT)));
//         objectMapper.registerModule(javaTimeModule).registerModule(new ParameterNamesModule());
//         return objectMapper;
//     }
//
// }
