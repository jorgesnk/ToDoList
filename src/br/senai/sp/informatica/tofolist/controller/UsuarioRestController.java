package br.senai.sp.informatica.tofolist.controller;


import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;




import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.auth0.jwt.JWTSigner;

import br.senai.sp.informatica.tofolist.dao.UsuarioDAO;
import br.senai.sp.informatica.tofolist.modelo.Usuario;



@RestController
public class UsuarioRestController {
	public static final String SECRET="todolist";
	public static final String ISSUER="trade force";
	
	@Autowired
	private UsuarioDAO dao;
	
	@RequestMapping(value="/usuario", method=RequestMethod.POST,
			consumes=MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<Usuario> Cadastrar(@RequestBody Usuario usuario){
			
			try {
				dao.iserir(usuario);
				URI location=new URI("/usuario"+usuario.getId());
				return ResponseEntity.created(location).body(usuario);
			} catch (URISyntaxException e) {
				e.printStackTrace();
				return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
			}
		
	}
	
	@RequestMapping(value="/login", method=RequestMethod.POST
			,consumes=MediaType.APPLICATION_JSON_UTF8_VALUE
			,produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<String> logar(@RequestBody Usuario usuario){
		try {
			usuario =dao.logar(usuario);
			if(usuario != null){
				long iat =System.currentTimeMillis()/1000;
				long exp= iat +60;
				JWTSigner singer=new JWTSigner(SECRET);
				HashMap<String, Object> claims=new HashMap<>();
				claims.put("iat",iat );
				claims.put("exp",exp );
				claims.put("iss",ISSUER );
				claims.put("id_usuario",usuario.getId() );
				//gerar token
				String jwt= singer.sign(claims);
				JSONObject token=new JSONObject();
				token.put("token", jwt);
				return ResponseEntity.ok(token.toString());
				
				
				
			}
			else{
				return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
			}
			
			
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	

}
