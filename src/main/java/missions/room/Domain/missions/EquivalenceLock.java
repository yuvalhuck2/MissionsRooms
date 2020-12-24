package missions.room.Domain.missions;

import java.util.HashSet;
import java.util.Set;

public class EquivalenceLock<T>
{

    private final Set<T> slots = new HashSet<T>();

    public void lock(final T ticket) throws InterruptedException
    {
        synchronized (slots)
        {
            while (slots.contains(ticket))
            {
                slots.wait();
            }

            slots.add(ticket);
        }
    }

    public void release(final T ticket)
    {
        synchronized (slots)
        {
            slots.remove(ticket);
            slots.notifyAll();
        }
    }
}

