/*******************************************************************************
                         CRIAÇÃO DO BANCO
*******************************************************************************/

DROP DATABASE Scrumming_DB;
CREATE DATABASE Scrumming_DB;

/*******************************************************************************
                         CRIAÇÃO DAS TABELAS
*******************************************************************************/

DROP TABLE IF EXISTS Tarefa_Favorita;
DROP TABLE IF EXISTS Reporte_Tarefa;
DROP TABLE IF EXISTS Sprint_Backlog;
DROP TABLE IF EXISTS Time_Projeto;
DROP TABLE IF EXISTS Sprint;
DROP TABLE IF EXISTS Tarefa;
DROP TABLE IF EXISTS ItemBacklog;
DROP TABLE IF EXISTS Usuario;
DROP TABLE IF EXISTS Projeto;


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

CREATE TABLE Sprint (
  PK_sprint INTEGER UNSIGNED NOT NULL AUTO_INCREMENT,
  descricao VARCHAR(20) NOT NULL,
  data_inicio DATE NOT NULL,
  data_fim DATE NOT NULL,
  situacao_sprint INTEGER NOT NULL,
  PRIMARY KEY(PK_sprint)
);

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

CREATE TABLE Sprint_Backlog (
  ItemBacklog_PK_backlog INTEGER UNSIGNED NOT NULL,
  Sprint_PK_sprint INTEGER UNSIGNED NOT NULL,
  PRIMARY KEY(ItemBacklog_PK_backlog, Sprint_PK_sprint),
  INDEX Sprint_Backlog_FKIndex1(ItemBacklog_PK_backlog),
  INDEX Sprint_Backlog_FKIndex2(Sprint_PK_sprint),
  FOREIGN KEY(ItemBacklog_PK_backlog)
    REFERENCES ItemBacklog(PK_backlog)
      ON DELETE NO ACTION
      ON UPDATE NO ACTION,
  FOREIGN KEY(Sprint_PK_sprint)
    REFERENCES Sprint(PK_sprint)
      ON DELETE NO ACTION
      ON UPDATE NO ACTION
);

CREATE TABLE Time_Projeto (
  Projeto_PK_projeto INTEGER UNSIGNED NOT NULL,
  Usuario_PK_usuario INTEGER UNSIGNED NOT NULL,
  perfil_usuario INTEGER NOT NULL,
  PRIMARY KEY(Projeto_PK_projeto, Usuario_PK_usuario),
  INDEX Time_Projeto_FKIndex1(Projeto_PK_projeto),
  INDEX Time_Projeto_FKIndex2(Usuario_PK_usuario),
  FOREIGN KEY(Projeto_PK_projeto)
    REFERENCES Projeto(PK_projeto)
      ON DELETE NO ACTION
      ON UPDATE NO ACTION,
  FOREIGN KEY(Usuario_PK_usuario)
    REFERENCES Usuario(PK_usuario)
      ON DELETE NO ACTION
      ON UPDATE NO ACTION
);

CREATE TABLE Reporte_Tarefa (
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

CREATE TABLE Tarefa_Favorita (
  Usuario_PK_usuario INTEGER UNSIGNED NOT NULL,
  Reporte_Tarefa_Usuario_PK_usuario INTEGER UNSIGNED NOT NULL,
  Reporte_Tarefa_Tarefa_PK_tarefa INTEGER UNSIGNED NOT NULL,
  PRIMARY KEY(Usuario_PK_usuario, Reporte_Tarefa_Usuario_PK_usuario, Reporte_Tarefa_Tarefa_PK_tarefa),
  INDEX Tarefa_Favorita_FKIndex1(Usuario_PK_usuario),
  INDEX Tarefa_Favorita_FKIndex2(Reporte_Tarefa_Tarefa_PK_tarefa, Reporte_Tarefa_Usuario_PK_usuario),
  FOREIGN KEY(Usuario_PK_usuario)
    REFERENCES Usuario(PK_usuario)
      ON DELETE NO ACTION
      ON UPDATE NO ACTION,
  FOREIGN KEY(Reporte_Tarefa_Tarefa_PK_tarefa, Reporte_Tarefa_Usuario_PK_usuario)
    REFERENCES Reporte_Tarefa(Tarefa_PK_tarefa, Usuario_PK_usuario)
      ON DELETE NO ACTION
      ON UPDATE NO ACTION
);

/*******************************************************************************
                               FIM DO SCRIPT
*******************************************************************************/