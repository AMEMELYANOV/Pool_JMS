package ru.job4j.pool.service;

import ru.job4j.pool.model.Req;
import ru.job4j.pool.model.Resp;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Реализация сервиса по работе с сообщениями
 * в виде queue
 *
 * @author Alexander Emelyanov
 * @version 1.0
 */
public class QueueService implements Service {
    ConcurrentHashMap<String, ConcurrentLinkedQueue<String>> queue = new ConcurrentHashMap<>();

    /**
     * Выполняет обработку объекта запроса Req.
     * Работа с сообщениями выполняется в зависимости от метода
     * запроса HTTP протокола, при POST методе, сообщение кладется
     * в queue, при GET достается. Возвращает объект ответа Resp.
     *
     * @param req объект запроса
     * @return объект ответа Resp
     */
    @Override
    public Resp process(Req req) {
        Resp result = new Resp("Bad Request", "400");
        queue.putIfAbsent(req.getSourceName(), new ConcurrentLinkedQueue<>());
        if ("POST".equals(req.getHttpRequestType())) {
            queue.putIfAbsent(req.getSourceName(), new ConcurrentLinkedQueue<>());
            queue.get(req.getSourceName()).add(req.getParam());
            result = new Resp("Created", "201");
        }
        if ("GET".equals(req.getHttpRequestType())) {
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