package br.pro.projeto.web.sistema.gestao.dao;

import java.util.List;

import org.junit.Ignore;
import org.junit.Test;

import br.pro.projeto.web.sistema.gestao.domain.Estado;

public class EstadoDAOTest {
	
	@Test
	@Ignore
	public void salvar(){
		Estado estado = new Estado();
		estado.setNome("Minas Gerais");
		estado.setSigla("MG");
		
		EstadoDAO estadoDAO = new EstadoDAO();
		estadoDAO.salvar(estado);
	}
	
	
	@Test
	@Ignore
	public void listar() {
		/*Chamar o estadoDAO*/
		EstadoDAO estadoDAO = new EstadoDAO();
		/*Criar uma lista oque o meu listar retorna um resultado */
		List<Estado> resultado = estadoDAO.listar();

		/*total de registros que existem no banco */
		System.out.println("Total de Registros Encontrados: " + resultado.size());

		for (Estado estado : resultado) {
			System.out.println(estado.getCodigo() + " - " + estado.getSigla() + " - " + estado.getNome());
		}
	}


	@Test
	@Ignore
	public void buscar(){
		/* Cria uma variavel to tipo Long */
		Long codigo = 2L;
		
		/*Cria uma variavel do tipo estadoDAO, isso faz ela ter acesso aos metodos da classe EstadoDAO*/
		EstadoDAO estadoDAO = new EstadoDAO();
		
		/* Manda buscar pelo codigo */
		Estado estado = estadoDAO.buscar(codigo);
		
		/* */
		if(estado == null){
			System.out.println("Nenhum registro encontrado");
		}else{
			System.out.println("Registro encontrado:");
			System.out.println(estado.getCodigo() + " - " + estado.getSigla() + " - " + estado.getNome());
		}
	}
	
	
	@Test
	@Ignore
	public void excluir(){
		/* Cria uma variavel to tipo Long */
		Long codigo = 7L;
		/*Cria uma variavel do tipo estadoDAO, isso faz ela ter acesso aos metodos da classe EstadoDAO*/
		EstadoDAO estadoDAO = new EstadoDAO();
		/* Manda buscar pelo codigo */
		Estado estado = estadoDAO.buscar(codigo);
		
		/* se estado for null imprime na tela */
		if(estado == null){
			System.out.println("Nenhum registro encontrado");
		}else{
			estadoDAO.excluir(estado);
			System.out.println("Registro removido:");
			System.out.println(estado.getCodigo() + " - " + estado.getSigla() + " - " + estado.getNome());
		}
	}
	
	@Test
	@Ignore
	public void editar(){
		Long codigo = 7L;
		EstadoDAO estadoDAO = new EstadoDAO();
		Estado estado = estadoDAO.buscar(codigo);
		
		if(estado == null){
			System.out.println("Nenhum registro encontrado");
		}else{
			//System.out.println("Registro editado - Antes:");
			//System.out.println(estado.getCodigo() + " - " + estado.getSigla() + " - " + estado.getNome());
			
			/*Comando para mudar os dados */
			estado.setNome("Mato Grosso do Sul");
			estado.setSigla("MS");
			/* chama o update */
			estadoDAO.editar(estado);
			
			//System.out.println("Registro editado - Depois:");
			//System.out.println(estado.getCodigo() + " - " + estado.getSigla() + " - " + estado.getNome());
		}
	}
	
}
