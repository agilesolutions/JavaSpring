package com.agilesolutions.mvc;

import lombok.extern.slf4j.Slf4j;
import org.apache.avro.Schema;
import org.apache.avro.io.DatumReader;
import org.apache.avro.io.Decoder;
import org.apache.avro.io.DecoderFactory;
import org.apache.avro.specific.SpecificDatumReader;
import org.apache.avro.specific.SpecificRecordBase;
import org.apache.kafka.common.errors.SerializationException;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Arrays;

@Slf4j
public class AvroDeserializer<T extends SpecificRecordBase> implements Deserializer<T> {

    private final boolean useBinaryEncoding;

    public AvroDeserializer(boolean useBinaryEncoding) {
        this.useBinaryEncoding = useBinaryEncoding;
    }

    public boolean isUseBinaryEncoding() {
        return useBinaryEncoding;
    }

    @Override
    public T deserialize(Class<? extends T> clazz, byte[] data) throws SerializationException {
        try {
            T result = null;
            if (data != null) {
                if (log.isDebugEnabled()) {
                    log.debug("data='({})", new String(data));
                }
                Class<? extends SpecificRecordBase> specificRecordClass =
                        (Class<? extends SpecificRecordBase>) clazz;
                Schema schema = specificRecordClass.newInstance().getSchema();
                DatumReader<T> datumReader =
                        new SpecificDatumReader<>(schema);
                Decoder decoder = useBinaryEncoding ?
                        DecoderFactory.get().binaryDecoder(data, null) :
                        DecoderFactory.get().jsonDecoder(schema, new ByteArrayInputStream(data));;

                result = datumReader.read(null, decoder);
                if (log.isDebugEnabled()) {
                    log.debug("deserialized data={}:{}", clazz.getName(), result);
                }
            }
            return result;
        } catch (InstantiationException | IllegalAccessException | IOException e) {
            throw new SerializationException("Can't deserialize data '" + Arrays.toString(data) + "'", e);
        }
    }
}