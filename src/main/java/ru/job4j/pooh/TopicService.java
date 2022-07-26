package ru.job4j.pooh;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class TopicService implements Service {
    private final ConcurrentHashMap<String, ConcurrentHashMap<String, ConcurrentLinkedQueue<String>>> topics =
            new ConcurrentHashMap<>();

    @Override
    public Resp process(Req req) {
        Resp result = new Resp("Bad Request", "400");
        topics.putIfAbsent(req.getSourceName(), new ConcurrentHashMap<>());
        if ("POST".equals(req.httpRequestType())) {
           var topic = topics.get(req.getSourceName());
           if (topic == null) {
               result = new Resp("", "404");
           } else {
               if (topic.values().size() > 0) {
                   result = new Resp("Created", "200");
               }
               for (ConcurrentLinkedQueue<String> value : topic.values()) {
                   value.add(req.getParam());
               }

           }
        }
        if ("GET".equals(req.httpRequestType())) {
            topics.get(req.getSourceName()).putIfAbsent(req.getParam(), new ConcurrentLinkedQueue<String>());
            String message = topics.get(req.getSourceName()).get(req.getParam()).poll();
            if (message == null) {
                result = new Resp("Topic Not Found", "404");
            } else {
                result = new Resp(message, "200");
            }
        }
        return result;
    }
}