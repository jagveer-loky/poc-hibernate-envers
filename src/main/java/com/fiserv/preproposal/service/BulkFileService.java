package com.fiserv.preproposal.service;

import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BulkFileService {

    private static final Logger LOG = LogManager.getLogger(BulkFileService.class);

    public void readBulk(){
        System.out.println("Hello World");
        System.out.println("Hello World 2");
    }


}
