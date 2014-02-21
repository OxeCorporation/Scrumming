/*******************************************************************************
                         CRIAÇÃO DO BANCO
*******************************************************************************/

DROP DATABASE Scrumming_DB;
CREATE DATABASE Scrumming_DB;

/*******************************************************************************
                         CRIAÇÃO DAS TABELAS
*******************************************************************************/

DROP TABLE Usuario;
GO;
CREATE TABLE Usuario (
  PK_usuario INTEGER UNSIGNED NOT NULL AUTO_INCREMENT,
  login VARCHAR(20) UNIQUE NOT NULL,
  senha VARCHAR(10) NOT NULL,
  nome VARCHAR(50) NULL,
  data_cadastro DATE NOT NULL,
  situacao_usuario INTEGER NOT NULL,
  email VARCHAR(50) UNIQUE NOT NULL,
  PRIMARY KEY(PK_usuario),
  INDEX (email, login)
);

DROP TABLE Sprint;
GO;
CREATE TABLE Sprint (
  PK_sprint INTEGER UNSIGNED NOT NULL AUTO_INCREMENT,
  descricao VARCHAR(20) NOT NULL,
  data_inicio DATE NOT NULL,
  data_fim DATE NOT NULL,
  situacao_sprint INTEGER NOT NULL,
  PRIMARY KEY(PK_sprint)
);

DROP TABLE Projeto;
GO;
CREATE TABLE Projeto (
  PK_projeto INTEGER UNSIGNED NOT NULL AUTO_INCREMENT,
  nome TEXT NOT NULL,
  descricao VARCHAR(255) NULL,
  data_inicio DATE NOT NULL,
  data_fim DATE NOT NULL,
  data_cadastro DATE NOT NULL,
  situacao_projeto INTEGER NOT NULL,
  PRIMARY KEY(PK_projeto)
);

DROP TABLE ItemBacklog;
GO;
CREATE TABLE ItemBacklog (
  PK_backlog INTEGER UNSIGNED NOT NULL AUTO_INCREMENT,
  Projeto_PK_projeto INTEGER UNSIGNED NOT NULL,
  nome VARCHAR(20) NOT NULL,
  descricao VARCHAR(255) NULL,
  valor_negocio DOUBLE NULL,
  story_points DOUBLE NULL,
  roi DOUBLE NULL,
  situacao_backlog INTEGER NOT NULL,
  PRIMARY KEY(PK_backlog),
  INDEX ItemBacklog_FKIndex1(Projeto_PK_projeto),
  FOREIGN KEY(Projeto_PK_projeto)
    REFERENCES Projeto(PK_projeto)
      ON DELETE NO ACTION
      ON UPDATE NO ACTION
);

DROP TABLE Tarefa;
GO;
CREATE TABLE Tarefa (
  PK_tarefa INTEGER UNSIGNED NOT NULL AUTO_INCREMENT,
  ItemBacklog_PK_backlog INTEGER UNSIGNED NOT NULL,
  Usuario_PK_usuario INTEGER UNSIGNED NOT NULL,
  descricao VARCHAR(255) NOT NULL,
  situacao_tarefa INTEGER NOT NULL,
  tempo_estimado INTEGER NOT NULL,
  PRIMARY KEY(PK_tarefa),
  INDEX Tarefa_FKIndex2(Usuario_PK_usuario),
  INDEX Tarefa_FKIndex3(ItemBacklog_PK_backlog),
  FOREIGN KEY(Usuario_PK_usuario)
    REFERENCES Usuario(PK_usuario)
      ON DELETE NO ACTION
      ON UPDATE NO ACTION,
  FOREIGN KEY(ItemBacklog_PK_backlog)
    REFERENCES ItemBacklog(PK_backlog)
      ON DELETE NO ACTION
      ON UPDATE NO ACTION
);

DROP TABLE ItemBacklog_has_Sprint;
GO;
CREATE TABLE ItemBacklog_has_Sprint (
  ItemBacklog_PK_backlog INTEGER UNSIGNED NOT NULL,
  Sprint_PK_sprint INTEGER UNSIGNED NOT NULL,
  PRIMARY KEY(ItemBacklog_PK_backlog, Sprint_PK_sprint),
  INDEX ItemBacklog_has_Sprint_FKIndex1(ItemBacklog_PK_backlog),
  INDEX ItemBacklog_has_Sprint_FKIndex2(Sprint_PK_sprint),
  FOREIGN KEY(ItemBacklog_PK_backlog)
    REFERENCES ItemBacklog(PK_backlog)
      ON DELETE NO ACTION
      ON UPDATE NO ACTION,
  FOREIGN KEY(Sprint_PK_sprint)
    REFERENCES Sprint(PK_sprint)
      ON DELETE NO ACTION
      ON UPDATE NO ACTION
);

DROP TABLE Projeto_has_Usuario;
GO;
CREATE TABLE Projeto_has_Usuario (
  Projeto_PK_projeto INTEGER UNSIGNED NOT NULL,
  Usuario_PK_usuario INTEGER UNSIGNED NOT NULL,
  perfil_usuario INTEGER NOT NULL,
  PRIMARY KEY(Projeto_PK_projeto, Usuario_PK_usuario),
  INDEX Projeto_has_Usuario_FKIndex1(Projeto_PK_projeto),
  INDEX Projeto_has_Usuario_FKIndex2(Usuario_PK_usuario),
  FOREIGN KEY(Projeto_PK_projeto)
    REFERENCES Projeto(PK_projeto)
      ON DELETE NO ACTION
      ON UPDATE NO ACTION,
  FOREIGN KEY(Usuario_PK_usuario)
    REFERENCES Usuario(PK_usuario)
      ON DELETE NO ACTION
      ON UPDATE NO ACTION
);

DROP TABLE Usuario_has_Tarefa;
GO;
CREATE TABLE Usuario_has_Tarefa (
  Tarefa_PK_tarefa INTEGER UNSIGNED NOT NULL,
  Usuario_PK_usuario INTEGER UNSIGNED NOT NULL,
  tempo_reportado INTEGER NULL DEFAULT 0,
  tempo_restante INTEGER NULL DEFAULT 0,
  data_reporte DATE NOT NULL,
  PRIMARY KEY(Tarefa_PK_tarefa, Usuario_PK_usuario),
  INDEX Tarefa_has_Sprint_FKIndex1(Tarefa_PK_tarefa),
  INDEX Tarefa_has_Sprint_FKIndex3(Usuario_PK_usuario),
  FOREIGN KEY(Tarefa_PK_tarefa)
    REFERENCES Tarefa(PK_tarefa)
      ON DELETE NO ACTION
      ON UPDATE NO ACTION,
  FOREIGN KEY(Usuario_PK_usuario)
    REFERENCES Usuario(PK_usuario)
      ON DELETE NO ACTION
      ON UPDATE NO ACTION
);

/*******************************************************************************
                               FIM DO SCRIPT
*******************************************************************************/