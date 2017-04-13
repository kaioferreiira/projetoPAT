package br.pro.projeto.web.sistema.gestao.main;


import org.hibernate.Session;

import br.pro.projeto.web.sistema.gestao.util.HibernateUtil;


public class HibernateUtilTest {
	public static void main(String[] args) {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		sessao.close();
		HibernateUtil.getFabricaDeSessoes().close();
	}
}