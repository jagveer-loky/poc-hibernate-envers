package com.fiserv.preproposal.api.domain.repository.report;

import com.fiserv.preproposal.api.domain.entity.TypeReport;

import java.util.Collection;

public interface IInputReport {

    String getRequester();

    Collection<String> getFields();
}
