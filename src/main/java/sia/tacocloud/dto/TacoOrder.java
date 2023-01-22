package sia.tacocloud.dto;

import lombok.Data;
import org.hibernate.validator.constraints.CreditCardNumber;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class TacoOrder implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long id;
    private Date placedAt;
    @NotBlank(message="Delivery name is required")
    @Size(max = 50, message = "Delivery name must be less than 50 characters")
    private String deliveryName;
    @NotBlank(message="Street is required")
    @Size(max = 50, message = "Delivery street must be less than 50 characters")
    private String deliveryStreet;
    @NotBlank(message="City is required")
    @Size(max = 50, message = "Delivery city must be less than 50 characters")
    private String deliveryCity;
    @NotBlank(message="State is required")
    @Size(min = 2, max = 2, message = "Delivery state must be equal to 2 characters")
    private String deliveryState;
    @NotBlank(message="Zip code is required")
    @Size(max = 10, message = "Delivery Zip must be <= 10 characters")
    private String deliveryZip;
//    Это предотвращает ошибки пользователя и  преднамеренный ввод
//    неверных данных, но не гарантирует, что номер кредитной карты
//    соответствует действующему счету и этот счет позволяет списывать
//    средства. Алгоритм Луна.
    @CreditCardNumber(message="Not a valid credit card number")
    private String ccNumber;
    @Pattern(regexp="^(0[1-9]|1[0-2])([\\/])([2-9][0-9])$",
            message="Must be formatted MM/YY")
    private String ccExpiration;
    @Digits(integer=3, fraction=0, message="Invalid CVV")
    private String ccCVV;
    private List<Taco> tacos = new ArrayList<>();


    public void addTaco(Taco taco) {
        this.tacos.add(taco);
    }
}
