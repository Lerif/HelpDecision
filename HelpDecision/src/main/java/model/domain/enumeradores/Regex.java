package model.domain.enumeradores;

public enum Regex {
	COMECO("\\|\\-\\>"), FINAL("\\|\\<\\-"), DOISARGS("[0-9]+,[0-9]+\\)\\s+[a-zA-Z]+\\s+\\([0-9]+,[0-9]+"), NOMEFUNC(
			"[a-zA-Z]+[_[a-zA-Z]+]*[\\s[a-zA-Z]+[_[a-zA-Z]+]*]+"), UMARG("\\([0-9]+,[0-9]+\\)"), DATEFORMAT(
					"dd/MM/yyyy HH:mm:ss:SSS"), FECHALETRAABRE(
							"\\)\\s+[a-zA-Z]+\\s+\\("), NUMVIRNUM("[0-9]+,[0-9]+"), NAONUMERO("^[0-9]");

	String exp;

	Regex(String e) {
		exp = e;
	}

	public String valor() {
		return exp;
	}

}
