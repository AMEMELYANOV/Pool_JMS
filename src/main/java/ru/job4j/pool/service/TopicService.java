package ru.job4j.pool.service;

import ru.job4j.pool.model.Req;
import ru.job4j.pool.model.Resp;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Реализация сервиса по работе с сообщениями
 * в виде topics
 *
 * @author Alexander Emelyanov
 * @version 1.0
 */
public class TopicService implements Service {
    private final ConcurrentHashMap<String, ConcurrentHashMap<String, ConcurrentLinkedQueue<String>>> topics =
            new ConcurrentHashMap<>();

    /**
     * Выполняет обработку объекта запроса Req.
     * Работа с сообщениями выполняется в зависимости от метода
     * запроса HTTP протокола, при POST методе, сообщение кладется
     * в topics, при GET достается. Возвращает объект ответа Resp.
     *
     * @param req объект запроса
     * @return объект ответа Resp
     */
    @Override
    public Resp process(Req req) {
        Resp result = new Resp("Bad Request", "400");
        topics.putIfAbsent(req.getSourceName(), new ConcurrentHashMap<>());
        if ("POST".equals(req.getHttpRequestType())) {
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
        if ("GET".equals(req.getHttpRequestType())) {
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