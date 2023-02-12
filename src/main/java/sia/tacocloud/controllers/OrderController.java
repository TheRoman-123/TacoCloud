package sia.tacocloud.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import sia.tacocloud.dto.TacoOrder;
import sia.tacocloud.entities.User;
import sia.tacocloud.repository.OrderRepository;
import sia.tacocloud.repository.UserRepository;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/orders")
@SessionAttributes("tacoOrder")
public class OrderController {
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    @GetMapping("/current")
    public String orderForm() {
        return "orderForm";
    }

    /*  Method #1:
@PostMapping
public String processOrder(@Valid TacoOrder order, Errors errors,
 SessionStatus sessionStatus,
 Principal principal) {
 ...
 User user = userRepository.findByUsername(
 principal.getName());
 order.setUser(user);
 ...
}
Это решение прекрасно работает, но захламляет код, реализующий
прикладную логику, деталями, имеющими отношение к безопасности.

    * Method #2:
@PostMapping
public String processOrder(@Valid TacoOrder order, Errors errors,
 SessionStatus sessionStatus,
 Authentication authentication) {
 ...
 User user = (User) authentication.getPrincipal();
 order.setUser(user);
 ...
}
getPrincipal() returns an Object
*/
    @PostMapping
    public String processOrder(@Valid TacoOrder order, Errors errors,
                               SessionStatus sessionStatus,
                               @AuthenticationPrincipal User user) {
        if (errors.hasErrors()) {
            return "orderForm";
        }
        order.setUser(user);
        orderRepository.save(order);
        sessionStatus.setComplete();
        return "redirect:/";
    }
/* Method #4:
Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
User user = (User) authentication.getPrincipal();
Несмотря на то, что это решение изобилует кодом, связанным с безопасностью, у него есть одно преимущество:
его можно использовать в любом месте приложения, а не только в методах контроллеров. Это делает его пригодным
для использования на более низких уровнях кода.
*/


//  AccessDeniedException если нет указанной привилегии
//  При перехвате п.у. -> страница с HttpStatus 403 или перенаправление на страницу входа.
//  Чтобы аннотация работала, добавляем @EnableGlobalMethodSecurity к классу конфигурации Security.
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteAllOrders() {
        orderRepository.deleteAll();
    }

//  Аннотация проверяет права пользователя после получения возвращаемого объекта.
//  Действует так же, как @PreAuthorize
    @PostAuthorize("hasRole('ADMIN') || " +
            "returnObject.user.username == authentication.name")
    public TacoOrder getOrder(long id) {
        return orderRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }
}
