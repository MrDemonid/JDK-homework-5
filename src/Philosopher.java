import java.util.Random;
import java.util.concurrent.CountDownLatch;

public class Philosopher extends Thread {

    private static final int MIN_SLEEP_TIME = 200;
    private static final int MAX_SLEEP_TIME = 5000;
    private static final int MIN_EAT_TIME = 1000;
    private static final int MAX_EAT_TIME = 5000;

    private CountDownLatch latch;
    private Table table;                // стол
    private int place;                  // место философа за столом

    private int numOfEat;
    Random random;

    public Philosopher(String name, CountDownLatch latch, Table table)
    {
        super(name);
        this.latch = latch;
        this.table = table;
        numOfEat = 0;
        random = new Random(System.currentTimeMillis());
        place = table.getPlace();           // садимся за стол
        System.out.println(name + " сел на место №" + place);
    }

    @Override
    public void run()
    {
        while (!interrupted() && numOfEat < 3)
        {
            try {
                doThink();
                doEat();
            } catch (InterruptedException e)
            {
                break;
            }
        }
        System.out.println("Философ " + Thread.currentThread().getName() + " наелся");
        latch.countDown();
    }

    /**
     * Философ размышляет
     */
    private void doThink() throws InterruptedException
    {
        System.out.println(Thread.currentThread().getName() + " размышляет.");
        sleep(random.nextInt(MIN_SLEEP_TIME-1, MAX_SLEEP_TIME));
    }

    /**
     * Философ ест
     */
    private void doEat() throws InterruptedException
    {
        if (table.getForks(place))
        {
            System.out.println(Thread.currentThread().getName() + "(" + place + ")" + " ест");
            sleep(random.nextInt(MIN_EAT_TIME-1, MAX_EAT_TIME));
            table.freeForks(place);
            numOfEat++;
        }
    }
}
