package DAO;

import java.util.List;
import java.util.Optional;

public interface CrudDAO<T> {
    Optional<T> find(T model);
    List<T> findAll();
    void delete(T model);
    void update (T model);
    void save(T model);
}
