package sia.tacocloud.repository;

import org.springframework.data.repository.CrudRepository;
import sia.tacocloud.dto.Ingredient;

public interface IngredientRepository extends CrudRepository<Ingredient, String> {

}
