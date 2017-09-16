package lg.webapidemo.forum.store;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

public class DataStore<T extends DataStoreEntry> {

    private final Map<Integer, T> backingMap = new LinkedHashMap<>();

    public synchronized T get(Integer id) {
        return backingMap.get(id);
    }

    public synchronized void add(T entry) {
        Integer id = backingMap.size();
        entry.setId(id);
        backingMap.put(id, entry);
    }

    public synchronized void remove(Integer id) {
        backingMap.put(id, null);
    }

    public synchronized void clear() {
        backingMap.clear();
    }

    public synchronized Stream<T> valueStream() {
        return backingMap.values().stream().filter(Objects::nonNull);
    }

    public synchronized List<T> getAll() {
        return valueStream().collect(toList());
    }

    public synchronized T getLast() {
        Integer lastIndex = backingMap.size();
        T lastItem = null;
        while(lastItem == null && lastIndex >=0) {
            lastItem = backingMap.get(lastIndex);
            lastIndex--;
        }
        return lastItem;
    }

    public synchronized Integer size() {
        return getAll().size();
    }

}
