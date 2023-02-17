package sia.tacocloud.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;
import org.springframework.data.rest.core.annotation.RestResource;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@ToString
@RestResource(rel="tacos", path="tacos")
@RequiredArgsConstructor
@Entity
public class Taco {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Date createdAt = new Date();
    @NotNull
    @Size(min=5, message="Name must be at least 5 characters long")
    private String name;
    @Size(min=1, message="You must choose at least 1 ingredient")
    @ManyToMany()
    @ToString.Exclude
    private List<Ingredient> ingredients = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "taco_order_id")
    private TacoOrder tacoOrder;

    public void addIngredient(Ingredient ingredient) {
        this.ingredients.add(ingredient);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Taco taco = (Taco) o;
        return id != null && Objects.equals(id, taco.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

//    Example:
//    curl "localhost:8080/data-api/tacos?sort=createdAt,desc&page=0&size=12"
}
