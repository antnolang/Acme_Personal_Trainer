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

import domain.Session;

@Component
@Transactional
public class SessionToStringConverter implements Converter<Session, String> {

	@Override
	public String convert(final Session session) {
		String result;

		if (session == null)
			result = null;
		else
			result = String.valueOf(session.getId());

		return result;
	}

}
