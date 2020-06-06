package com.momentum.batch.client.common.job.writer.writer;

import com.momentum.batch.domain.PrimaryKeyIdentifier;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.json.JacksonJsonObjectMarshaller;
import org.springframework.batch.item.json.builder.JsonFileItemWriterBuilder;
import org.springframework.core.io.FileSystemResource;

public class JsonWriter<T extends PrimaryKeyIdentifier> extends AbstractTopicWriter<T> {

    public JsonWriter() {
    }

    public ItemWriter getWriter(String fileName) {
        return new JsonFileItemWriterBuilder<T>().jsonObjectMarshaller(new JacksonJsonObjectMarshaller<>())
                .resource(new FileSystemResource(fileName))
                .name("jsonFileItemWriter")
                .build();
    }
}
