package com.example.account.service;

import com.example.account.event.BruteForceEvent;
import com.example.account.event.UserChangeEvent;
import com.example.account.models.entities.Event;
import com.example.account.models.entities.constants.EventAction;
import com.example.account.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

@Service
public class EventService {
    public static final String ANONYMOUS = "Anonymous";

    @Autowired
    EventRepository eventRepository;

    public List<Event> getAllEvents() {
        return eventRepository.getAllByOrderByDateAscIdAsc();
    }

    public void logLoginFailed(AuthenticationFailureBadCredentialsEvent event, HttpServletRequest request) {
        String path = path(request);
        String subject = subject(event.getAuthentication());

        saveEvent(EventAction.LOGIN_FAILED, event.getTimestamp(), subject, path, path);
    }

    public void logAccessDenied(HttpServletRequest request) {
        String path = path(request);
        String subject = subject();

        saveEvent(EventAction.ACCESS_DENIED, now(), subject, path, path);
    }

    public void logBruteForce(BruteForceEvent event, HttpServletRequest request) {
        String path = path(request);
        String subject = event.getSubject();

        saveEvent(EventAction.BRUTE_FORCE, event.getTimestamp(), subject, path, path);
    }

    public void logUserChange(UserChangeEvent event, HttpServletRequest request) {
        String path = path(request);
        String subject = event.getSubject();
        if (subject == null) {
            subject = subject();
        }

        saveEvent(event.getEventAction(), event.getTimestamp(), subject, event.getObject(), path);
    }

    public void saveEvent(
            EventAction action,
            long time,
            String subject,
            String object,
            String path
    ) {
        Event event = new Event();
        event.setDate(new Date(time));
        event.setAction(action);
        event.setObject(object);
        event.setSubject(subject);
        event.setPath(path);

        event = eventRepository.save(event);
    }

    String subject() {
        return subject(SecurityContextHolder.getContext().getAuthentication());
    }

    String subject(Authentication authentication) {
        if (authentication == null) {
            return ANONYMOUS;
        }
        String name = authentication.getName();
        return name == null || "anonymousUser".equals(name)
                ? ANONYMOUS
                : name;
    }

    static String path(HttpServletRequest request) {
        Object attr = request.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);
        if (attr != null) {
            return attr.toString();
        }
        return request.getRequestURI();
    }

    static long now() {
        return System.currentTimeMillis();
    }
}
