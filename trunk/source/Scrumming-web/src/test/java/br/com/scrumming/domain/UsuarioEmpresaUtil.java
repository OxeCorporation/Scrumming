package br.com.scrumming.domain;


public class UsuarioEmpresaUtil {

    public static UsuarioEmpresa criarFuncionario(Empresa empresa, Usuario usuario) {
        UsuarioEmpresa usuarioDaEmpresa = new UsuarioEmpresa();
        usuarioDaEmpresa.setEmpresa(empresa);
        usuarioDaEmpresa.setUsuario(usuario);
        usuarioDaEmpresa.setAtivo(true);
        return usuarioDaEmpresa;
    }
}
