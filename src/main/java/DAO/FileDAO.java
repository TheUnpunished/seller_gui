package DAO;

import Model.Entity;

import java.util.List;
import java.util.Optional;

public interface FileDAO<T> {
    public void save(List<Entity> models);
    public List<Optional> findAll();
}
