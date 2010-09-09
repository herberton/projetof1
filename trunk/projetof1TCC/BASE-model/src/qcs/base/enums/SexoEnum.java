package qcs.base.enums;

public enum SexoEnum {


	M("Masculino"),F("Feminino");

	private String descricao;

	private SexoEnum(String descricao){
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
}