package br.pro.projeto.web.sistema.gestao.dao;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.junit.Ignore;
import org.junit.Test;

import java.lang.reflect.ParameterizedType;
import java.util.List;

import br.pro.projeto.web.sistema.gestao.util.HibernateUtil;

public class GenericDAO<Entidade> {

	/* usase-se esta pesquisa a partir do momento que for listar */
	@SuppressWarnings("unused")
	private Class<Entidade> classe;

	/*
	 * capturar a class, serve para fazer pesquisar, eu preciso saber de qual
	 * domain que vou implementar onde
	 */

	@SuppressWarnings("unchecked")
	public GenericDAO() {// construtor
		this.classe = (Class<Entidade>) ((ParameterizedType) getClass().getGenericSuperclass())
				.getActualTypeArguments()[0];
	}

	public void salvar(Entidade entidade) {
		/* Pegar a fabrica de sessao capturando uma sessao aberta */
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();

		/* Serve para controlar transaçoes, ou faz tudo ou nao faz nada! */
		Transaction transacao = null;

		try {
			/*
			 * para criar uma trasacao eu preciso de uma sessao aberta a apartir
			 * daqui se der alguma problema ele desfaz
			 */
			transacao = sessao.beginTransaction();

			/* usa a sessao para salvar e passa o domain generico */
			sessao.save(entidade);

			/* confirma a transacao */
			transacao.commit();
		}
		/* desfaz */
		catch (RuntimeException erro) {
			/* verifica se a transacao foi diferente de nula */
			if (transacao != null) {

				/* desfaz tudo ate o momento */
				transacao.rollback();
			}
			/*
			 * nao pode esquecer de da o throw, ele repropaga o erro para as
			 * camadas superiores
			 */
			throw erro;
		}
		/*
		 * fechar a sessao, tanto faz se der certou ou errado ele fecha a
		 * sessao, para nao gastar recursos do servidor
		 */
		finally {
			sessao.close();
		}
	}

	@SuppressWarnings("unchecked")
	public List<Entidade> listar() {

		/*
		 * Abrir a sessao, nao precisa de transacao porque nao vamos alterar
		 * nada
		 */
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		try {

			/*
			 * vai reaalizar uma consulta pela sessao aberta na classe que desejamos consultar
			 */
			Criteria consulta = sessao.createCriteria(classe);

			/*
			 * Uma variavel para guardar o resultado, list é uma funcao do
			 * proprio hibernate
			 */
			List<Entidade> resultado = consulta.list();

			/* retorna todos os estados que temos */
			return resultado;
		} catch (RuntimeException erro) {
			throw erro;
		} finally {
			sessao.close();
		}
	}

	@SuppressWarnings("unchecked")
	public Entidade buscar(Long codigo) {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		try {
			Criteria consulta = sessao.createCriteria(classe);

			/*
			 * pega o valor que o usuario digitou e compara com a chave primaria
			 * da nossa entidade
			 */
			consulta.add(Restrictions.idEq(codigo));

			/*
			 * é usado quando retorno somente um, se for mais tem que ser um
			 * List, ele precisa de uns casting
			 */
			Entidade resultado = (Entidade) consulta.uniqueResult();
			return resultado;
		} catch (RuntimeException erro) {
			throw erro;
		} finally {
			sessao.close();
		}
	}

	public void excluir(Entidade entidade) {
		/* Abre uma sessao */
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		/* Inicia uma transacao */
		Transaction transacao = null;

		try {
			/* tenta remover */
			transacao = sessao.beginTransaction();
			/* confirma a transacao */
			sessao.delete(entidade);
			transacao.commit();
		} catch (RuntimeException erro) {
			if (transacao != null) {
				/* se der erro, desfaz a transacao */
				transacao.rollback();
			}

			/* repropaga o erro */
			throw erro;
		} finally {
			/* fecha a sessao */
			sessao.close();
		}
	}

	@Test
	@Ignore
	public void editar(Entidade entidade) {
		/* Abre uma sessao */
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		/* Inicia uma transacao */
		Transaction transacao = null;

		try {
			transacao = sessao.beginTransaction();
			sessao.update(entidade);
			transacao.commit();
		} catch (RuntimeException erro) {
			if (transacao != null) {
				transacao.rollback();
			}
			throw erro;
		} finally {
			sessao.close();
		}
	}

}