package br.com.scrumming.domain;

public class ContatoUtil {

    public static Contato criarPadrao() {
        Contato contato = new Contato();
        contato.setNome("Esdras");
        contato.setNumero(123456);
        contato.setSobreNome("Souto");
        return contato;
    }
}
