package com.fiserv.preproposal.api.infrastrucutre.aid;

import com.fiserv.preproposal.api.infrastrucutre.aid.enums.ApplicationEnum;
import com.fiserv.preproposal.api.infrastrucutre.aid.enums.EventActivityEnum;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;

/**
 * The Class PropertiesUtil.
 */
public class ListUtil {

	@SafeVarargs
	@SuppressWarnings("varargs")
	public static <T> List<T> asList(T... a) {
		if(a == null)
			return null;
		return Arrays.asList(a);
	}
}
