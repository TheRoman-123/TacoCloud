package sia.tacocloud.repository;

import org.springframework.data.repository.CrudRepository;
import sia.tacocloud.dto.Ingredient;

import java.util.UUID;

public interface IngredientRepository
        extends CrudRepository<Ingredient, UUID> {

}
