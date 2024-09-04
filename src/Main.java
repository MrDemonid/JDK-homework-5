/*
1. Пять безмолвных философов сидят вокруг круглого стола, перед каждым
философом стоит тарелка спагетти.
2. Вилки лежат на столе между каждой парой ближайших философов.
3. Каждый философ может либо есть, либо размышлять.
4. Философ может есть только тогда, когда держит две вилки — взятую справа и
слева.
5. Философ не может есть два раза подряд, не прервавшись на размышления
(можно не учитывать)
Описать в виде кода такую ситуацию. Каждый философ должен поесть три раза
 */

import java.util.concurrent.CountDownLatch;

public class Main {
    public static void main(String[] args) {

        Main m = new Main();
        m.run();
    }

    private void run()
    {
        CountDownLatch activePhilosopher = new CountDownLatch(5);
        Table table = new Table();

        new Philosopher("Аристотель", activePhilosopher, table).start();
        new Philosopher("Сократ", activePhilosopher, table).start();
        new Philosopher("Платон", activePhilosopher, table).start();
        new Philosopher("Декарт", activePhilosopher, table).start();
        new Philosopher("Кант", activePhilosopher, table).start();

        // ждем завершения работы всех потоков
        try {
            activePhilosopher.await();
        } catch (InterruptedException ignored) {}

        System.out.println("Все философы наелись.");
    }

}