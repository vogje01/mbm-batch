package com.example.batchprocessing;

import com.momentum.batch.client.jobs.common.logging.BatchLogger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;

public class PersonItemProcessor implements ItemProcessor<Person, Person> {

	@Autowired
	private BatchLogger batchLogger;

	private static final Logger log = LoggerFactory.getLogger(PersonItemProcessor.class);

	@Override
	public Person process(final Person person) throws Exception {
		final String firstName = person.getFirstName().toUpperCase();
		final String lastName = person.getLastName().toUpperCase();

		final Person transformedPerson = new Person(firstName, lastName);

		log.info("Converting (" + person + ") into (" + transformedPerson + ")");

		batchLogger.info("Converting (" + person + ") into (" + transformedPerson + ")");

		return transformedPerson;
	}

}
