package sia.tacocloud.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import sia.tacocloud.entities.User;
import sia.tacocloud.repository.UserRepository;

import java.security.SecureRandom;

@Configuration
public class SecurityConfig {
//  Помимо всего прочего, с помощью HttpSecurity можно:
//  + потребовать выполнения определенных условий безопасности перед обслуживанием запроса;
//  + отправить пользователю свою страницу входа;
//  + предоставить пользователям возможности выйти из приложения;
//  + настроить защиту от подделки межсайтовых запросов.
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
            .authorizeRequests()    // стр 159 книги
                .antMatchers("/design", "/orders").hasRole("USER")
                .antMatchers("/", "/**").permitAll()
            .and()
                .formLogin()
                .loginPage("/login")
//                .loginProcessingUrl("/authenticate")  Здесь мы указали, что запросы на вход будут иметь путь /authen
//                .usernameParameter("user")    ticate, а имя пользователя и пароль будут передаваться в полях user
//                .passwordParameter("pwd")     и pwd.
//                .defaultSuccessUrl("/design"[, true]) Страница, на которую надо переходить после успешной аутентификации,
//  если пользователь изначально запросил страницу входа или в корневой путь п.у. По умолчанию после успешного входа
//  пользователь будет перенаправляться непосредственно на страницу, которую он пытался открыть.
//  При указании true, переход на /design после успешного входа осуществляется принудительно.
                .and()
            .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        int strength = 10;
        return new BCryptPasswordEncoder(strength, new SecureRandom());
    }

    @Bean
    public UserDetailsService userDetailsService(UserRepository userRepo) {
        return username -> {
            User user = userRepo.findByUsername(username);
            if (user != null) return user;
            throw new UsernameNotFoundException("User ‘" + username + "’ not found");
        };
    }
}
