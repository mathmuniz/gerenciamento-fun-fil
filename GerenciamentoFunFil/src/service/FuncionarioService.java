package service;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;

import modelo.Filial;
import modelo.Funcionario;

@Stateless
public class FuncionarioService extends GenericService<Funcionario>{
	
	public FuncionarioService() {
		super(Funcionario.class);
	}
	
	 	@PersistenceContext
	    private EntityManager entityManager;
	    
	 	public List<Funcionario> listarFuncionarioPorNome(String nome){
			final CriteriaBuilder cBuilder = getEntityManager().getCriteriaBuilder();
			final CriteriaQuery<Funcionario> cQuery = cBuilder.createQuery(Funcionario.class);
			final Root<Funcionario> rootAtendimento = cQuery.from(Funcionario.class);
			
			final Expression<String> expdescricao = rootAtendimento.get("nome");
			
			cQuery.select(rootAtendimento);
			cQuery.where(cBuilder.like(expdescricao, "%"+ nome +"%"));
			
			List<Funcionario> resultado = getEntityManager().createQuery(cQuery).getResultList();
			
			return resultado;
			
		}
	 	
}
