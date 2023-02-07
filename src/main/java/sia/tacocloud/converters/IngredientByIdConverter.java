package sia.tacocloud.converters;

import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import sia.tacocloud.dto.Ingredient;
import sia.tacocloud.repository.IngredientRepository;

import javax.validation.constraints.NotNull;

// To convert ingredient.id into ingredient in view
@Component
@RequiredArgsConstructor
public class IngredientByIdConverter implements Converter<String, Ingredient> {
    private final IngredientRepository ingredientRepo;

    @Override
    public Ingredient convert(@NotNull String id) {
        return ingredientRepo.findById(id).orElse(null);
    }
}
