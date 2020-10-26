package br.com.isaccanedo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;

@SpringBootApplication
public class HttpieSpringbootApplication {

	@Autowired
	private FuncionarioRepository employeeRepository;

	public static void main(String[] args) {
		SpringApplication.run(HttpieSpringbootApplication.class, args);
	}

	@PostConstruct
	public void init() // Método para Insert no Database
	{
		employeeRepository.save(new Funcionario("Isac", "Canedo", "Engenheiro de Software"));
	}
}
