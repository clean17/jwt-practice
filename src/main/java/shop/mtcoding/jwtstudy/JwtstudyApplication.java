package shop.mtcoding.jwtstudy;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import lombok.RequiredArgsConstructor;
import shop.mtcoding.jwtstudy.model.User;
import shop.mtcoding.jwtstudy.model.UserRepository;

@SpringBootApplication
public class JwtstudyApplication {

	@Bean
	CommandLineRunner initDatabase(UserRepository userRepository){ // IoC 에 등록할때 레파지토리가 있으므로 파라미터에 넣으면 끝
		return (args)->{
			userRepository.save(User.builder()
						  .username("ssar")
						  .password("1234")
						  .email("ssar@nate.com")
						  .role("user")
						  .build());
			userRepository.save(User.builder()
						  .username("admin")
						  .password("1234")
						  .email("admin@nate.com")
						  .role("admin")
						  .build());
		};
	}
	public static void main(String[] args) {
		SpringApplication.run(JwtstudyApplication.class, args);
	}

}
