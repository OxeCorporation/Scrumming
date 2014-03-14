package br.com.scrumming.domain;


public class UsuarioEmpresaUtil {

    public static UsuarioEmpresa criarFuncionario(Empresa empresa, Usuario usuario) {
        UsuarioEmpresa usuarioDaEmpresa = new UsuarioEmpresa();
        usuarioDaEmpresa.setEmpresa(empresa);
        usuarioDaEmpresa.setUsuario(usuario);
        usuarioDaEmpresa.setIsUsuarioEmpresa(1);
        return usuarioDaEmpresa;
    }
}
