import java.util.ArrayList;
import java.util.List;

/**
 * Класс отвечающий за распределение вилок.
 * Поскольку вилки у нас строго между местами, то проще
 * контролировать места: два соседа не могут есть одновременно.
 * Т.е. если соседи слева и справа не едят, то обе вилки свободны.
 */
public class Table {

    private final List<Boolean> places;     // какие места находятся в процессе приёма пищи

    public Table()
    {
        places = new ArrayList<>();
    }

    /**
     * Резервирует место
     */
    public int getPlace()
    {
        places.add(false);
        return places.size()-1;
    }

    /**
     * Попытка получить вилки для определенного места за столом
     * @param place Номер места
     */
    public synchronized boolean getForks(int place)
    {
        if (checkPlace(place))
        {
            places.set(place, true);    // едок на месте <place> начинает есть, то есть занял обе вилки
//            System.out.println("---- table: " + places);
            return true;
        }
        return false;
    }

    /**
     * Место освободилось
     */
    public synchronized void freeForks(int place)
    {
        if (place >= 0 && place < places.size())
            places.set(place, false);
    }

    /**
     * Проверка на свободные места справа и слева
     */
    private boolean checkPlace(int n)
    {
        if (n >= places.size() || n < 0)
            return false;
        int left = n > 0 ? n-1 : places.size()-1;
        int right = n >= places.size()-1 ? 0 : n+1;

        return !places.get(left) && !places.get(right);
    }

}
