package com.fiserv.preproposal.api.application.resource;

import com.fiserv.preproposal.api.domain.service.ProposalService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

import static com.fiserv.preproposal.api.application.runnable.ThreadsComponent.execute;

@RestController
@RequiredArgsConstructor
@RequestMapping("links-payments")
public class ProposalResource {

    /**
     *
     */
    private final ProposalService proposalService;

    /**
     * @param ids Set<Long>
     * @return Boolean
     */
    @PostMapping("reload")
    public Boolean reloadLinkPayments(@RequestBody final Set<Long> ids) {
        execute(() -> proposalService.reloadLinkPayments(ids));
        return true;
    }

    /**
     * @return Set<Long>
     */
    @GetMapping
    public Set<Long> getLinksPaymentsToReload() {
        return proposalService.getLinksPaymentsToReload();
    }

    /**
     * @return Boolean
     */
    @GetMapping("reload")
    public Boolean getAndReloadLinksPayments() {
        execute(proposalService::getAndReloadLinksPayments);
        return true;
    }

}
