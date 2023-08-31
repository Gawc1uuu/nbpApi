package pl.kurs.utils;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import pl.kurs.model.Car;
import pl.kurs.serializers.CarDeserializer;
import pl.kurs.serializers.CarSerializer;

import java.text.SimpleDateFormat;

public enum ObjectMapperHolder {

    INSTANCE;

    private final ObjectMapper mapper;

    ObjectMapperHolder() {
        this.mapper = create();
    }

    private static ObjectMapper create() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        mapper.setDateFormat(new SimpleDateFormat("dd-MM-yyyy"));

        mapper.registerModule(new JavaTimeModule());

        CarSerializer cs = new CarSerializer(Car.class);
        SimpleModule sm1 = new SimpleModule("CarSerializer", new Version(1, 0, 0,
                null, null, null));
        sm1.addSerializer(Car.class, cs);
        mapper.registerModule(sm1);

        CarDeserializer cd = new CarDeserializer(Car.class);
        SimpleModule sm2 = new SimpleModule("CarDeserializer", new Version(1, 0, 0,
                null, null, null));
        sm2.addDeserializer(Car.class, cd);
        mapper.registerModule(sm2);

        return mapper;
    }

    public ObjectMapper getMapper() {
        return mapper;
    }
}
