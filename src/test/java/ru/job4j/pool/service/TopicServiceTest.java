package ru.job4j.pool.service;

import org.junit.Test;
import ru.job4j.pool.model.Req;
import ru.job4j.pool.model.Resp;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

/**
 * Тест класс реализации сервисного слоя
 *
 * @see ru.job4j.pool.service.TopicService
 * @author Alexander Emelyanov
 * @version 1.0
 */
public class TopicServiceTest {

    /**
     * Выполняется сохранение объекта в topic, и обработки двух запросов
     * на его получение из topic. Первый запрос вернет данные, второй, что
     * тема не найдена.
     */
    @Test
    public void whenTopic() {
        TopicService topicService = new TopicService();
        String paramForPublisher = "temperature=18";
        String paramForSubscriber1 = "client407";
        String paramForSubscriber2 = "client6565";

        topicService.process(
                new Req("GET", "topic", "weather", paramForSubscriber1)
        );

        Resp resp = topicService.process(
                new Req("POST", "topic", "weather", paramForPublisher)
        );

        Resp result1 = topicService.process(
                new Req("GET", "topic", "weather", paramForSubscriber1)
        );
        Resp result2 = topicService.process(
                new Req("GET", "topic", "weather", paramForSubscriber2)
        );
        assertThat(result1.getText(), is("temperature=18"));
        assertThat(result2.getText(), is("Topic Not Found"));
    }

    /**
     * Выполняется сохранение объекта в topic, и проверка отсутствия созданной индивидуальной
     * очереди после вызова процесс с объектом запроса с методом POST.
     */
    @Test
    public void whenPostTopic() {
        TopicService topicService = new TopicService();
        String paramForPublisher = "temperature=18";
        String paramForSubscriber = "client407";

        topicService.process(
                new Req("POST", "topic", "weather", paramForPublisher)
        );

        Resp result1 = topicService.process(
                new Req("GET", "topic", "weather", paramForSubscriber)
        );
        assertThat(result1.getText(), is("Topic Not Found"));
    }
}