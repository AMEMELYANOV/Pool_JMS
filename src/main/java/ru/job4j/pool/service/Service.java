package ru.job4j.pool.service;

import ru.job4j.pool.model.Req;
import ru.job4j.pool.model.Resp;

/**
 * Сервис по работе с сообщениями
 *
 * @author Alexander Emelyanov
 * @version 1.0
 */
public interface Service {

    /**
     * Выполняет обработку объекта запроса Req.
     * Возвращает объект ответа Resp.
     *
     * @param req объект запроса
     * @return объект ответа Resp
     */
    Resp process(Req req);
}