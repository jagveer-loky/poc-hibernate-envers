package com.fiserv.preproposal.api.application.resource;

import com.fiserv.preproposal.api.domain.service.ProposalService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping("/proposals")
public class ProposalResource {

    /**
     *
     */
    private final ProposalService proposalService;

    /**
     * @param ids Set<Long>
     * @return Boolean
     */
    @PostMapping("reload/link-payments")
    public Boolean reloadLinkPayments(@RequestBody final Set<Long> ids) {
        return proposalService.reloadLinkPayments(ids);
    }

}
