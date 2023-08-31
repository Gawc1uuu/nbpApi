package pl.kurs.serializers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import pl.kurs.model.Car;

import java.io.IOException;

public class CarSerializer extends StdSerializer<Car> {

    public CarSerializer(Class<Car> t) {
        super(t);
    }

    @Override
    public void serialize(Car car, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeStringField("brand", car.getProducer());
        jsonGenerator.writeNumberField("doorsNumber", car.getDoors());
        jsonGenerator.writeEndObject();
    }
}
