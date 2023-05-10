package ru.job4j.pool.service;

import org.junit.Test;
import ru.job4j.pool.model.Req;
import ru.job4j.pool.model.Resp;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

/**
 * Тест класс реализации сервисного слоя
 *
 * @see ru.job4j.pool.service.QueueService
 * @author Alexander Emelyanov
 * @version 1.0
 */
public class QueueServiceTest {

    /**
     * Выполняется сохранения объекта в queue и обработка запроса
     * на его получения из queue с удачным результатом.
     */
    @Test
    public void whenPostThenGetQueue() {
        QueueService queueService = new QueueService();
        String paramForPostMethod = "temperature=18";

        queueService.process(
                new Req("POST", "queue", "weather", paramForPostMethod)
        );

        Resp result = queueService.process(
                new Req("GET", "queue", "weather", null)
        );
        assertThat(result.getText(), is("temperature=18"));
    }

    /**
     * Выполняется проверка получения объекта из queue, если queue пустая.
     */
    @Test
    public void whenGetEmptyQueue() {
        QueueService queueService = new QueueService();

        Resp result = queueService.process(
                new Req("GET", "queue", "weather", null)
        );
        assertThat(result.getText(), is("Not Found"));
    }
}