package br.com.springboot.murilohenriquedev.curso_springboot.controllers;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.springboot.murilohenriquedev.curso_springboot.model.Usuario;
import br.com.springboot.murilohenriquedev.curso_springboot.repository.UsuarioRepository;

/**
 *
 * A sample greetings controller to return greeting text
 */
@RestController
public class GreetingsController {
	
	@Autowired //Injeção de depedencia
	private UsuarioRepository usuarioRepository;

    /**
     *
     * @param name the name to greet
     * @return greeting text
     */
    @RequestMapping(value = "/mostrarnome/{name}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public String greetingText(@PathVariable String name) {
        return "Curso Spring Boot API: " + name + "!";
    }
    
    @RequestMapping(value= "/olamundo/{valor}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public String retornaOlaMundo(@PathVariable String valor) {
    	Usuario usuario = new Usuario();
    	usuario.setNome(valor);
    	usuarioRepository.save(usuario);
    	return "Olá Mundo: " + valor;
    }
    
    @GetMapping(value = "listatodos") /*primeiro metodo de API*/
    @ResponseBody /*Retorna os dados para o corpo da resposta - json*/
    public ResponseEntity<List<Usuario>> listaUsuario(){
    	
    List<Usuario> usuarios =usuarioRepository.findAll();/*Executa a consulta no bd e lista pega todos*/
    
    return new ResponseEntity<List<Usuario>>(usuarios, HttpStatus.OK);/*Retorna a lista em Json*/
    };
    
    @PostMapping(value = "salvar")//mapeia a url
    @ResponseBody //descricao da resposta
    public ResponseEntity<Usuario> salvar(@RequestBody Usuario usuario){ //recebe os dados para salvar
    	Usuario user = usuarioRepository.save(usuario);
    	
    	return new ResponseEntity<Usuario>(user, HttpStatus.CREATED);
    }
    
    @DeleteMapping(value = "delete")//mapeia a url
    @ResponseBody //descricao da resposta
    public ResponseEntity<String> delete(@RequestParam Long id){ //recebe os dados para deletar
    	usuarioRepository.deleteById(id);
    	
    	return new ResponseEntity<String>("Usuário deletado com sucesso!", HttpStatus.OK);
    }
    
    @GetMapping(value = "buscaruserid")//mapeia a url
    @ResponseBody //descricao da resposta
    public ResponseEntity<Usuario> buscaruserid(@RequestParam(name = "id") Long id){ //recebe os dados para listar
    	Usuario user = usuarioRepository.findById(id).get(); //retornando usuario pelo id
    	
    	return new ResponseEntity<Usuario>(user, HttpStatus.OK);
    }
    
    @PutMapping(value = "atualizar")//mapeia a url
    @ResponseBody //descricao da resposta
    public ResponseEntity<?> atualizar(@RequestBody Usuario usuario){ //recebe os dados para atualizar
    	
    	if(usuario.getId() == null) {
    		return new ResponseEntity<String>("Id não foi informado.", HttpStatus.OK);
    	}
    	Usuario user = usuarioRepository.saveAndFlush(usuario);
    	
    	return new ResponseEntity<Usuario>(user, HttpStatus.OK);
    }
    
    @GetMapping(value = "buscarPorNome")//mapeia a url
    @ResponseBody //descricao da resposta
    public ResponseEntity<List<Usuario>> buscarPorNome(@RequestParam(name = "nome") String nome){ //recebe os dados para listar
    	List<Usuario> user = usuarioRepository.buscarPorNome(nome.trim().toUpperCase()); //retornando usuario por pedaço do nome
    	
    	return new ResponseEntity<List<Usuario>>(user, HttpStatus.OK);
    }
    
}
