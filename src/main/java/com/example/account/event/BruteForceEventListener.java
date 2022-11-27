package com.example.account.event;
import com.example.account.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
public class BruteForceEventListener implements ApplicationListener<BruteForceEvent> {
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private EventService eventService;

    @Override
    public void onApplicationEvent(BruteForceEvent event) {
        eventService.logBruteForce(event, request);
    }
}
