package com.example.demo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.boot.autoconfigure.jackson.JacksonProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.annotation.Order;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import javax.annotation.Resource;
import java.io.IOException;
import java.time.format.DateTimeFormatter;

/**
 * @author yuanlb
 * @since 2022/10/25
 */
@Configuration
public class JacksonConfig {

    @Resource
    private JacksonProperties jacksonProperties;


    @Bean
    @Order(1000)
    public Jackson2ObjectMapperBuilderCustomizer jackson2ObjectMapperBuilderCustomizer() {
        String dateTimeFormat = this.jacksonProperties.getDateFormat();

        if (StringUtils.isBlank(dateTimeFormat)) {
            dateTimeFormat = "yyyy-MM-dd HH:mm:ss";
        }

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(dateTimeFormat);
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return (jacksonObjectMapperBuilder) -> {
            jacksonObjectMapperBuilder.serializers(new JsonSerializer[]{new LocalDateTimeSerializer(dateTimeFormatter), new LocalDateSerializer(dateFormatter)});
            jacksonObjectMapperBuilder.deserializers(new JsonDeserializer[]{new LocalDateTimeDeserializer(dateTimeFormatter), new LocalDateDeserializer(dateFormatter)});
            jacksonObjectMapperBuilder.serializerByType(String.class, new JsonSerializer<String>() {
                public void serialize(String s, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
                    jsonGenerator.writeString(StringUtils.trim(s));
                }
            });
            jacksonObjectMapperBuilder.deserializerByType(String.class, new JsonDeserializer<String>() {
                public String deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
                    return StringUtils.trim(jsonParser.getText());
                }
            });
        };
    }

    @Bean
    @Primary
    public ObjectMapper marathonJacksonJsonObjectMapper(Jackson2ObjectMapperBuilder builder) {
        ObjectMapper mapper = builder.createXmlMapper(false).build();
        //"" or null ???????????????
        mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
        //???????????????,??????????????????(?????????????????????????????????????????????,??????????????????setter???handler????????????????????????)???????????????????????????
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        /** ???objectMapper??????????????????SerializerModifier???Factory */
        mapper.setSerializerFactory(mapper.getSerializerFactory()
                .withSerializerModifier(new MyBeanSerializerModifier()));

        SerializerProvider serializerProvider = mapper.getSerializerProvider();
        serializerProvider.setNullValueSerializer(new CustomizeNullJsonSerializer
                .NullObjectJsonSerializer());

        JacksonUtil.setSharedObjectMapper(mapper);
        return mapper;
    }
}
