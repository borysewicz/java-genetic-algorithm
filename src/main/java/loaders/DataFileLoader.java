package loaders;

import java.util.List;

public interface DataFileLoader<E> {

    List<E> load(String from);
}
