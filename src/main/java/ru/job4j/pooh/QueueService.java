package ru.job4j.pooh;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class QueueService implements Service {
    ConcurrentHashMap<String, ConcurrentLinkedQueue<String>> queue = new ConcurrentHashMap<>();

    @Override
    public Resp process(Req req) {
        Resp result = new Resp("Bad Request", "400");
        queue.putIfAbsent(req.getSourceName(), new ConcurrentLinkedQueue<>());
        if ("POST".equals(req.httpRequestType())) {
            queue.putIfAbsent(req.getSourceName(), new ConcurrentLinkedQueue<>());
            queue.get(req.getSourceName()).add(req.getParam());
            result = new Resp("Created", "201");
        }
        if ("GET".equals(req.httpRequestType())) {
            String param = queue.get(req.getSourceName()).poll();
            if (param != null) {
                result = new Resp(param, "200");
            } else {
                result = new Resp("Not Found", "404");
            }
        }
        return result;
    }
}