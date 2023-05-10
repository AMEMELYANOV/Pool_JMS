package ru.job4j.pool;

import ru.job4j.pool.model.Req;
import ru.job4j.pool.model.Resp;
import ru.job4j.pool.service.QueueService;
import ru.job4j.pool.service.Service;
import ru.job4j.pool.service.TopicService;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Основной класс для запуска приложения
 *
 * @author Alexander Emelyanov
 * @version 1.0
 */
public class PoolServer {
    private final HashMap<String, Service> modes = new HashMap<>();

    /**
     * Содержит основной цикл приложения. Создает сервер-сокет
     * и прослушивает локальный порт 9000 в цикле, пока сервер-сокет
     * не будет закрыт. Из данных в запросе формируется объект Req.
     * В зависимости от режима работы вызывается конкретная реализация
     * метода process, который формирует объект ответа Resp и
     * направляется в виде ответа от сервера.
     */
    public void start() {
        modes.put("queue", new QueueService());
        modes.put("topic", new TopicService());
        ExecutorService pool = Executors.newFixedThreadPool(
                Runtime.getRuntime().availableProcessors()
        );
        try (ServerSocket server = new ServerSocket(9000)) {
            while (!server.isClosed()) {
                Socket socket = server.accept();
                pool.execute(() -> {
                    try (OutputStream out = socket.getOutputStream();
                        InputStream input = socket.getInputStream()) {
                        byte[] buff = new byte[1_000_000];
                        int total = input.read(buff);
                        String content = new String(Arrays.copyOfRange(buff, 0, total), StandardCharsets.UTF_8);
                        Req req = Req.of(content);
                        Resp resp = modes.get(req.getPoolMode()).process(req);
                        String ls = System.lineSeparator();
                        out.write(("HTTP/1.1 " + resp.getStatus() + ls + ls).getBytes());
                        out.write((resp.getText().concat(ls)).getBytes());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Выполняет запуск приложения
     *
     * @param args аргументы командной строки
     */
    public static void main(String[] args)  {
        new PoolServer().start();
    }
}