package repository;

import java.util.List;

//Common Functionalities here.
//Strategy Design Pattern
public interface CrudRepository<T,ID> extends SuperRepository{
    boolean create (T t);
    boolean update (T t);
    boolean deleteById(ID id);
    T getById(ID id);
    List<T> getAll();
}
