package controle;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import modelo.Endereco;
import modelo.Filial;
import modelo.Funcionario;
import service.FilialService;
import service.FuncionarioService;
import service.RelatorioService;

@ManagedBean
@ViewScoped
public class RelatorioBean {
	
	public RelatorioBean() {
        filial = new Filial();    
    }
	
	@EJB
	private FilialService filialService;
	
	@EJB
	private FuncionarioService funcionarioService;
	
	@EJB
	private RelatorioService relatorioService;
	
	private Funcionario funcionario = new Funcionario();
	private List<Funcionario> funcionarios = new ArrayList<Funcionario>();
	
	private Filial filial = new Filial();
	private List<Filial> filiais = new ArrayList<Filial>();
	private Long idFilial = 0L;
	
	private Double minSalario;
	private Double maxSalario;
	
	private void atualizarLista() {
		filiais = filialService.listAll();
	}
	
	@PostConstruct //método após a cosntrução da classe
	private void inicializar() {
		atualizarLista();
	}
	
	public void filtrar() {
		if (idFilial == 0) {
			funcionarios = relatorioService.listarFuncionario(minSalario, maxSalario);
		} else {
			funcionarios = relatorioService.listarFuncionarioFiltrado(idFilial, minSalario, maxSalario);
		}
		
		if (funcionarios.isEmpty()) {
	        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Não há funcionários para os critérios de busca selecionados.", null));
	    }
	}
	
	public String formatarCpf(String cpf) {
	    cpf = cpf.replaceAll("[^0-9]", "");

	    if (cpf.length() != 11) {
	        return cpf;
	    }

	    return cpf.substring(0, 3) + "." +
	           cpf.substring(3, 6) + "." +
	           cpf.substring(6, 9) + "-" +
	           cpf.substring(9);
	}
	
	public void listarFuncionarios() {
		funcionarios = funcionarioService.listAll();
	}
	
	public FilialService getFilialService() {
		return filialService;
	}
	
	public void setFilialService(FilialService filialService) {
		this.filialService = filialService;
	}
	
	public Filial getFilial() {
		return filial;
	}
	
	public void setFilial(Filial filial) {
		this.filial = filial;
	}
	
	public List<Filial> getFiliais() {
		return filiais;
	}
	
	public void setFiliais(List<Filial> filiais) {
		this.filiais = filiais;
	}
	
	public Funcionario getFuncionario() {
		return funcionario;
	}
	
	public void setFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
	}
	
	public List<Funcionario> getFuncionarios() {
		return funcionarios;
	}
	
	public void setFuncionarios(List<Funcionario> funcionarios) {
		this.funcionarios = funcionarios;
	}
	
	public Long getIdFilial() {
		return idFilial;
	}
	
	public void setIdFilial(Long idFilial) {
		this.idFilial = idFilial;
	}

	public Double getMinSalario() {
		return minSalario;
	}

	public void setMinSalario(Double minSalario) {
		this.minSalario = minSalario;
	}

	public Double getMaxSalario() {
		return maxSalario;
	}

	public void setMaxSalario(Double maxSalario) {
		this.maxSalario = maxSalario;
	}
	
}