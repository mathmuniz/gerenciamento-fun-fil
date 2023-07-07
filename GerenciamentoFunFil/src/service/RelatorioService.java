package service;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;

import modelo.Filial;
import modelo.Funcionario;

@Stateless
public class RelatorioService extends GenericService<Funcionario>{
	public RelatorioService() {
		super(Funcionario.class);
	}
	
	public List<Funcionario> listarFuncionarioFiltrado(Long idFilial, Double minSalario, Double maxSalario) {
		final CriteriaBuilder cBuilder = getEntityManager().getCriteriaBuilder();
		final CriteriaQuery<Funcionario> cQuery = cBuilder.createQuery(Funcionario.class);
		final Root<Funcionario> rootFuncionario = cQuery.from(Funcionario.class);
		
		Join<Funcionario, Filial> joinFilial = rootFuncionario.join("filial");
		
		cQuery.select(rootFuncionario);
		cQuery.where(cBuilder.and(cBuilder.equal(joinFilial.get("id"), idFilial),
				cBuilder.between(rootFuncionario.get("salario").as(Double.class), minSalario, maxSalario)));
		cQuery.orderBy(cBuilder.desc(rootFuncionario.get("salario")));
		
		List<Funcionario> resultado = getEntityManager().createQuery(cQuery).getResultList();
		
		return resultado;
	}
	
	public List<Funcionario> listarFuncionario(Double minSalario, Double maxSalario) {
		final CriteriaBuilder cBuilder = getEntityManager().getCriteriaBuilder();
		final CriteriaQuery<Funcionario> cQuery = cBuilder.createQuery(Funcionario.class);
		final Root<Funcionario> rootFuncionario = cQuery.from(Funcionario.class);
		
		cQuery.select(rootFuncionario);
		cQuery.where(cBuilder.between(rootFuncionario.get("salario").as(Double.class), minSalario, maxSalario));
		cQuery.orderBy(cBuilder.desc(rootFuncionario.get("salario")));
		
		List<Funcionario> resultado = getEntityManager().createQuery(cQuery).getResultList();
		
		TypedQuery<Funcionario> query = getEntityManager().createQuery(cQuery);
		System.out.println("Consulta gerada: " + query.unwrap(org.hibernate.Query.class).getQueryString());
		
		return resultado;
	}
	
}