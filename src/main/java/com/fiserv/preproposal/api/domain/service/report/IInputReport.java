package com.fiserv.preproposal.api.domain.service.report;

import java.util.Collection;

public interface IInputReport {

    String getRequester();

    Collection<String> getFields();
}
