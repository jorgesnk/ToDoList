package br.senai.sp.informatica.tofolist.modelo;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


import org.springframework.security.authentication.encoding.Md5PasswordEncoder;

@Entity
public class Usuario {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(unique=true)
	private String login; 
	
	private String senha;
	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String loguin) {
		this.login = loguin;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		Md5PasswordEncoder encoder=new Md5PasswordEncoder();
		String md5 =encoder.encodePassword(this.senha, null);
		this.senha=md5;
	}

	
	
}
