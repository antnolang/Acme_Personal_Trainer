/*
 * BoxToStringConverter.java
 * 
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Trainer;

@Component
@Transactional
public class TrainerToStringConverter implements Converter<Trainer, String> {

	@Override
	public String convert(final Trainer trainer) {
		String result;

		if (trainer == null)
			result = null;
		else
			result = String.valueOf(trainer.getId());

		return result;
	}

}
