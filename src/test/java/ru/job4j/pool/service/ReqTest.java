package ru.job4j.pool.service;

import org.junit.Test;
import ru.job4j.pool.model.Req;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

/**
 * Тест класс проверки создания объекта запроса
 *
 * @see ru.job4j.pool.model.Req
 * @author Alexander Emelyanov
 * @version 1.0
 */
public class ReqTest {

    /**
     * Выполняется проверка создания объекта запроса Req, если
     * переданный запрос содержит POST метод и вызывает режим работы queue.
     */
    @Test
    public void whenQueueModePostMethod() {
        String ls = System.lineSeparator();
        String content = "POST /queue/weather HTTP/1.1" + ls
                + "Host: localhost:9000" + ls
                + "User-Agent: curl/7.72.0" + ls
                + "Accept: */*" + ls
                + "Content-Length: 14" + ls
                + "Content-Type: application/x-www-form-urlencoded" + ls + ls
                + "temperature=18" + ls;
        Req req = Req.of(content);
        assertThat(req.getHttpRequestType(), is("POST"));
        assertThat(req.getPoolMode(), is("queue"));
        assertThat(req.getSourceName(), is("weather"));
        assertThat(req.getParam(), is("temperature=18"));
    }

    /**
     * Выполняется проверка создания объекта запроса Req, если
     * переданный запрос содержит GET метод и вызывает режим работы queue.
     */
    @Test
    public void whenQueueModeGetMethod() {
        String ls = System.lineSeparator();
        String content = "GET /queue/weather HTTP/1.1" + ls
                + "Host: localhost:9000" + ls
                + "User-Agent: curl/7.72.0" + ls
                + "Accept: */*" + ls + ls + ls;
        Req req = Req.of(content);
        assertThat(req.getHttpRequestType(), is("GET"));
        assertThat(req.getPoolMode(), is("queue"));
        assertThat(req.getSourceName(), is("weather"));
        assertThat(req.getParam(), is(""));
    }

    /**
     * Выполняется проверка создания объекта запроса Req, если
     * переданный запрос содержит POST метод и вызывает режим работы topic.
     */
    @Test
    public void whenTopicModePostMethod() {
        String ls = System.lineSeparator();
        String content = "POST /topic/weather HTTP/1.1" + ls
                + "Host: localhost:9000" + ls
                + "User-Agent: curl/7.72.0" + ls
                + "Accept: */*" + ls
                + "Content-Length: 14" + ls
                + "Content-Type: application/x-www-form-urlencoded" + ls
                + "" + ls
                + "temperature=18" + ls;
        Req req = Req.of(content);
        assertThat(req.getHttpRequestType(), is("POST"));
        assertThat(req.getPoolMode(), is("topic"));
        assertThat(req.getSourceName(), is("weather"));
        assertThat(req.getParam(), is("temperature=18"));
    }

    /**
     * Выполняется проверка создания объекта запроса Req, если
     * переданный запрос содержит GET метод и вызывает режим работы topic.
     */
    @Test
    public void whenTopicModeGetMethod() {
        String ls = System.lineSeparator();
        String content = "GET /topic/weather/client407 HTTP/1.1" + ls
                + "Host: localhost:9000" + ls
                + "User-Agent: curl/7.72.0" + ls
                + "Accept: */*" + ls + ls + ls;
        Req req = Req.of(content);
        assertThat(req.getHttpRequestType(), is("GET"));
        assertThat(req.getPoolMode(), is("topic"));
        assertThat(req.getSourceName(), is("weather"));
        assertThat(req.getParam(), is("client407"));
    }

    /**
     * Выполняется проверка создания объекта запроса Req, если
     * переданный запрос вызывает выброс исключения.
     */
    @Test(expected = IllegalArgumentException.class)
    public void whenNotGetOrPostMethodThrowException() {
        String ls = System.lineSeparator();
        String content = "PUT /topic/weather/client407 HTTP/1.1" + ls
                + "Host: localhost:9000" + ls
                + "User-Agent: curl/7.72.0" + ls
                + "Accept: */*" + ls + ls + ls;
        Req.of(content);
    }
}