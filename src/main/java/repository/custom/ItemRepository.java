package repository.custom;

import model.Item;
import repository.CrudRepository;

import java.util.List;

public interface ItemRepository extends CrudRepository<Item,String> {

    List<String> getItemCodes();
}
