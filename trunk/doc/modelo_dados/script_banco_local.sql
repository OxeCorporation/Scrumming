-- *******************************************************************************
--                         CRIAÇÃO DO BANCO
-- *******************************************************************************

DROP DATABASE scrumming;
CREATE DATABASE scrumming;
USE scrumming;

-- *******************************************************************************
--                         CRIAÇÃO DAS TABELAS
-- *******************************************************************************

DROP TABLE IF EXISTS tarefafavorita;
DROP TABLE IF EXISTS tarefareporte;
DROP TABLE IF EXISTS sprintbacklog;
DROP TABLE IF EXISTS checkindailyscrum;
DROP TABLE IF EXISTS dailyscrum;
DROP TABLE IF EXISTS timeprojeto;
DROP TABLE IF EXISTS usuarioempresa;
DROP TABLE IF EXISTS sprint;
DROP TABLE IF EXISTS tarefa;
DROP TABLE IF EXISTS itembacklog;
DROP TABLE IF EXISTS usuario;
DROP TABLE IF EXISTS projeto;
DROP TABLE IF EXISTS config;
DROP TABLE IF EXISTS empresa;

-- -----------------------------------------------------
-- Table `Empresa`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Empresa` (
  `PK_empresa` INT(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `nome` VARCHAR(50) NOT NULL,
  `data_cadastro` TIMESTAMP NOT NULL,
  `is_ativo` BOOLEAN NOT NULL DEFAULT false,
  PRIMARY KEY (`PK_empresa`),
  INDEX `UNIQUE` (`nome` ASC))
PACK_KEYS = 0
ROW_FORMAT = DEFAULT;

-- -----------------------------------------------------
-- Table `Config`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Config` (
  `PK_config` INT(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `FK_empresa` INT(11) UNSIGNED NOT NULL,
  `codigo_config` INT(11) NOT NULL,
  `nome_config` VARCHAR(45) NULL,
  `perfil_owner` BOOLEAN NOT NULL DEFAULT false,
  `perfil_master` BOOLEAN NOT NULL DEFAULT false,
  `perfil_team` BOOLEAN NOT NULL DEFAULT false,
  PRIMARY KEY (`PK_config`, `FK_empresa`),
  INDEX `fk_Config_Empresa1_idx` (`FK_empresa` ASC),
  CONSTRAINT `fk_Config_Empresa1`
    FOREIGN KEY (`FK_empresa`)
    REFERENCES `Empresa` (`PK_empresa`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
PACK_KEYS = 0
ROW_FORMAT = DEFAULT
AUTO_INCREMENT = 0;

-- -----------------------------------------------------
-- Table `Projeto`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Projeto` (
  `PK_projeto` INT(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `FK_empresa` INT(11) UNSIGNED NOT NULL,
  `nome` VARCHAR(50) NOT NULL,
  `descricao` VARCHAR(500) NULL,
  `data_inicio` TIMESTAMP NOT NULL,
  `data_fim` TIMESTAMP NOT NULL,
  `data_cadastro` TIMESTAMP NOT NULL,
  `situacao_projeto` INT(1) UNSIGNED NOT NULL,
  PRIMARY KEY (`PK_projeto`),
  INDEX `Projeto_FKIndex1` (`FK_empresa` ASC),
  CONSTRAINT `fk_{CD46B0F7-869D-49D1-AF89-24AB82042C7F}`
    FOREIGN KEY (`FK_empresa`)
    REFERENCES `Empresa` (`PK_empresa`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
PACK_KEYS = 0
ROW_FORMAT = DEFAULT;


-- -----------------------------------------------------
-- Table `ItemBacklog`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ItemBacklog` (
  `PK_backlog` INT(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `FK_projeto` INT(11) UNSIGNED NOT NULL,
  `nome` VARCHAR(50) NOT NULL,
  `descricao` VARCHAR(500) NOT NULL,
  `criterio_aceitacao` VARCHAR(300) NOT NULL,
  `valor_negocio` DOUBLE NULL,
  `story_points` INT(11) UNSIGNED NULL,
 -- `prioridade` INT(11) UNSIGNED NOT NULL DEFAULT 0,
  `roi` DOUBLE NULL,
  `situacao_backlog` INT(1) UNSIGNED NOT NULL,
  `is_ativo` BOOL NOT NULL DEFAULT true,
  PRIMARY KEY (`PK_backlog`),
  INDEX `ItemBacklog_FKIndex1` (`FK_projeto` ASC),
  CONSTRAINT `fk_{60FDBEBA-E550-4A38-A6C5-E482203F03CD}`
    FOREIGN KEY (`FK_projeto`)
    REFERENCES `Projeto` (`PK_projeto`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
PACK_KEYS = 0
ROW_FORMAT = DEFAULT;


-- -----------------------------------------------------
-- Table `Sprint`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Sprint` (
  `PK_sprint` INT(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `FK_projeto` INT(11) UNSIGNED NOT NULL,
  `nome` VARCHAR(50) NULL,
  `descricao` VARCHAR(500) NOT NULL,
  `data_cadastro` TIMESTAMP NOT NULL,
  `data_inicio` TIMESTAMP NOT NULL,
  `data_fim` TIMESTAMP NOT NULL,
  `data_revisao` TIMESTAMP NOT NULL,
  `data_fechamento` TIMESTAMP NULL,
  `situacao_sprint` INT(1) UNSIGNED NOT NULL,
  PRIMARY KEY (`PK_sprint`),
  INDEX `fk_Sprint_Projeto1_idx` (`FK_projeto` ASC),
  CONSTRAINT `fk_Sprint_Projeto1`
    FOREIGN KEY (`FK_projeto`)
    REFERENCES `Projeto` (`PK_projeto`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
PACK_KEYS = 0
ROW_FORMAT = DEFAULT;

-- -----------------------------------------------------
-- Table `Usuario`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Usuario` (
  `PK_usuario` INT(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `nome` VARCHAR(50) NOT NULL,
  `email` VARCHAR(50) NOT NULL UNIQUE,
  `login` VARCHAR(30) NOT NULL UNIQUE,
  `senha` VARCHAR(32) NOT NULL,
  `data_cadastro` TIMESTAMP NOT NULL,
  `is_ativo` BOOL NOT NULL DEFAULT false,
  `is_empresa` BOOL NOT NULL DEFAULT false,
  `foto` LONGBLOB,
  PRIMARY KEY (`PK_usuario`),
  UNIQUE INDEX `UNIQUE` (`email` ASC, `login` ASC))
PACK_KEYS = 0
ROW_FORMAT = DEFAULT;


-- -----------------------------------------------------
-- Table `Tarefa`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Tarefa` (
  `PK_tarefa` INT(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `FK_itemBacklog` INT(11) UNSIGNED NOT NULL,
  `FK_usuario` INT(11) UNSIGNED NULL,
  `nome` VARCHAR(50) NULL,
  `descricao` VARCHAR(500) NOT NULL,
  `situacao_tarefa` INT(1) UNSIGNED NOT NULL,
  `tempo_estimado` INT(11) UNSIGNED NOT NULL,
  `data_atribuicao` TIMESTAMP NULL,
  PRIMARY KEY (`PK_tarefa`),
  INDEX `Tarefa_FKIndex1` (`FK_usuario` ASC),
  INDEX `Tarefa_FKIndex2` (`FK_itemBacklog` ASC),
  CONSTRAINT `fk_{598AC7FD-F143-4AAC-A61E-0D85D4B19EBF}`
    FOREIGN KEY (`FK_usuario`)
    REFERENCES `Usuario` (`PK_usuario`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_{DCB8C618-E73B-4E40-8080-1455EEE6699D}`
    FOREIGN KEY (`FK_itemBacklog`)
    REFERENCES `ItemBacklog` (`PK_backlog`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
PACK_KEYS = 0
ROW_FORMAT = DEFAULT;


-- -----------------------------------------------------
-- Table `TarefaReporte`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `TarefaReporte` (
  `PK_tarefaReporte` INT(11) NOT NULL AUTO_INCREMENT,
  `FK_tarefa` INT(11) UNSIGNED NOT NULL,
  `FK_usuario` INT(11) UNSIGNED NOT NULL,
  `tempo_reportado` INT(11) UNSIGNED NULL DEFAULT 0,
  `tempo_restante` INT(11) UNSIGNED NULL DEFAULT 0,
  `data_reporte` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  INDEX `Tarefa_has_Sprint_FKIndex1` (`FK_tarefa` ASC),
  INDEX `Tarefa_has_Sprint_FKIndex3` (`FK_usuario` ASC),
  PRIMARY KEY (`PK_tarefaReporte`),
  CONSTRAINT `fk_{BD8787A6-B767-4730-9A62-08388ADF10C0}`
    FOREIGN KEY (`FK_tarefa`)
    REFERENCES `Tarefa` (`PK_tarefa`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_{A9524165-9EEB-4852-9F2A-23F325B61712}`
    FOREIGN KEY (`FK_usuario`)
    REFERENCES `Usuario` (`PK_usuario`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
PACK_KEYS = 0
ROW_FORMAT = DEFAULT;


-- -----------------------------------------------------
-- Table `SprintBacklog`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `SprintBacklog` (
  `FK_backlog` INT(11) UNSIGNED NOT NULL,
  `FK_sprint` INT(11) UNSIGNED NOT NULL,
  `is_ativo` BOOLEAN NOT NULL DEFAULT true,
  PRIMARY KEY (`FK_backlog`, `FK_sprint`),
  INDEX `ItemBacklog_has_Sprint_FKIndex1` (`FK_backlog` ASC),
  INDEX `ItemBacklog_has_Sprint_FKIndex2` (`FK_sprint` ASC),
  CONSTRAINT `fk_{8BA718B2-B43F-4350-95ED-938E1561EF7C}`
    FOREIGN KEY (`FK_backlog`)
    REFERENCES `ItemBacklog` (`PK_backlog`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_{05CE95F1-43D0-4EA0-9C46-FA32B6C4C00D}`
    FOREIGN KEY (`FK_sprint`)
    REFERENCES `Sprint` (`PK_sprint`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
PACK_KEYS = 0
ROW_FORMAT = DEFAULT;


-- -----------------------------------------------------
-- Table `TarefaFavorita`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `TarefaFavorita` (
  `FK_usuario` INT(11) UNSIGNED NOT NULL,
  `FK_tarefa` INT(11) UNSIGNED NOT NULL,
  `favorita` TINYINT(1) NULL DEFAULT true,
  PRIMARY KEY (`FK_usuario`, `FK_tarefa`),
  INDEX `Usuario_has_Usuario_has_Tarefa_FKIndex1` (`FK_usuario` ASC),
  INDEX `Usuario_has_Usuario_has_Tarefa_FKIndex2` (`FK_tarefa` ASC),
  CONSTRAINT `fk_{6352C4BD-7159-4976-B467-F34DF8522F3F}`
    FOREIGN KEY (`FK_usuario`)
    REFERENCES `Usuario` (`PK_usuario`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_{0598683E-AD9C-4199-8B9E-B71F82907AC6}`
    FOREIGN KEY (`FK_tarefa`)
    REFERENCES `Tarefa` (`PK_tarefa`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
PACK_KEYS = 0
ROW_FORMAT = DEFAULT;


-- -----------------------------------------------------
-- Table `UsuarioEmpresa`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `UsuarioEmpresa` (
  `FK_empresa` INT(11) UNSIGNED NOT NULL,
  `FK_usuario` INT(11) UNSIGNED NOT NULL,
  `is_usuarioEmpresa` BOOLEAN NOT NULL DEFAULT true,
  `is_ativo` BOOLEAN NOT NULL DEFAULT true,
  PRIMARY KEY (`FK_empresa`, `FK_usuario`),
  INDEX `Empresa_has_Usuario_FKIndex1` (`FK_empresa` ASC),
  INDEX `Empresa_has_Usuario_FKIndex2` (`FK_usuario` ASC),
  CONSTRAINT `fk_{100C6641-CAE6-46D8-AA8D-045ABE1A439F}`
    FOREIGN KEY (`FK_empresa`)
    REFERENCES `Empresa` (`PK_empresa`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_{49A3E971-3D61-4AB9-BC82-7D84D2388F80}`
    FOREIGN KEY (`FK_usuario`)
    REFERENCES `Usuario` (`PK_usuario`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
PACK_KEYS = 0
ROW_FORMAT = DEFAULT;


-- -----------------------------------------------------
-- Table `TimeProjeto`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `TimeProjeto` (
  `FK_projeto` INT(11) UNSIGNED NOT NULL,
  `FK_usuario` INT(11) UNSIGNED NOT NULL,
  `FK_empresa` INT(11) UNSIGNED NOT NULL,
  `perfil_usuario` INT(11) UNSIGNED NOT NULL,
  `is_ativo` BOOLEAN NOT NULL DEFAULT true,
  PRIMARY KEY (`FK_projeto`, `FK_usuario`, `FK_empresa`),
  INDEX `Projeto_has_UsuarioEmpresa_FKIndex1` (`FK_projeto` ASC),
  INDEX `Projeto_has_UsuarioEmpresa_FKIndex2` (`FK_empresa` ASC, `FK_usuario` ASC),
  CONSTRAINT `fk_{E948F8A8-4024-4C10-B179-A3C8691326C5}`
    FOREIGN KEY (`FK_projeto`)
    REFERENCES `Projeto` (`PK_projeto`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_{D59AB639-8CAF-4E18-8007-FA01102B0EA3}`
    FOREIGN KEY (`FK_empresa` , `FK_usuario`)
    REFERENCES `UsuarioEmpresa` (`FK_empresa` , `FK_usuario`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
PACK_KEYS = 0
ROW_FORMAT = DEFAULT;


-- -----------------------------------------------------
-- Table `DailyScrum`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `DailyScrum` (
  `PK_dailyScrum` INT(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `FK_sprint` INT(11) UNSIGNED NOT NULL,
  `descricao` VARCHAR(500) NULL,
  `local_meeting` VARCHAR(50) NOT NULL,
  `dataHoraMarcada` TIMESTAMP NOT NULL,
  `duracao` INT(11) UNSIGNED NOT NULL,
  PRIMARY KEY (`PK_dailyScrum`),
  INDEX `DailyScrum_FKIndex1` (`FK_sprint` ASC),
  CONSTRAINT `fk_{9B6F1DFF-CCB8-4BB7-BF63-0DA0E171740A}`
    FOREIGN KEY (`FK_sprint`)
    REFERENCES `Sprint` (`PK_sprint`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
PACK_KEYS = 0
ROW_FORMAT = DEFAULT;


-- -----------------------------------------------------
-- Table `CheckinDailyScrum`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `CheckinDailyScrum` (
  `FK_empresa` INT(11) UNSIGNED NOT NULL,
  `FK_usuario` INT(11) UNSIGNED NOT NULL,
  `FK_projeto` INT(11) UNSIGNED NOT NULL,
  `FK_dailyScrum` INT(11) UNSIGNED NOT NULL,
  PRIMARY KEY (`FK_empresa`, `FK_usuario`, `FK_projeto`, `FK_dailyScrum`),
  INDEX `TimeProjeto_has_DailyScrum_FKIndex1` (`FK_projeto` ASC, `FK_usuario` ASC, `FK_empresa` ASC),
  INDEX `TimeProjeto_has_DailyScrum_FKIndex2` (`FK_dailyScrum` ASC),
  CONSTRAINT `fk_{428FE6DA-1EDA-4261-8FC5-4470BD012B29}`
    FOREIGN KEY (`FK_projeto` , `FK_usuario` , `FK_empresa`)
    REFERENCES `TimeProjeto` (`FK_projeto` , `FK_usuario` , `FK_empresa`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_{69BD035F-9FD7-432D-9CE6-062BDA3C1857}`
    FOREIGN KEY (`FK_dailyScrum`)
    REFERENCES `DailyScrum` (`PK_dailyScrum`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
PACK_KEYS = 0
ROW_FORMAT = DEFAULT;

-- *******************************************************************************
--            INSERTS INICIAIS PARA TESTE(Usuario, Empresa, Funcionario)
-- *******************************************************************************

INSERT INTO `scrumming`.`usuario`
(`nome`,
`email`,
`login`,
`senha`,
`data_cadastro`,
`is_ativo`,
`is_empresa`)
VALUES
('Oxe Corporation',
'oxecorporation@gmail.com',
'admin',
'admin',
NOW(),
1,
1);

INSERT INTO `scrumming`.`usuario`
(`nome`,
`email`,
`login`,
`senha`,
`data_cadastro`,
`is_ativo`,
`is_empresa`)
VALUES
('Funcionário Teste',
'funcionario@gmail.com',
'func',
'func',
NOW(),
1,
0);

INSERT INTO `scrumming`.`empresa`
(`nome`,
`data_cadastro`,
`is_ativo`)
VALUES
('Oxe Corporation',
NOW(),
1);

INSERT INTO `scrumming`.`usuarioEmpresa`
(`FK_empresa`,
`FK_usuario`,
`is_usuarioEmpresa`,
`is_ativo`)
VALUES
(1,
1,
1,
1);

INSERT INTO `scrumming`.`usuarioEmpresa`
(`FK_empresa`,
`FK_usuario`,
`is_usuarioEmpresa`,
`is_ativo`)
VALUES
(1,
2,
1,
1);

-- Projeto01
insert 
    into
        Projeto
        (FK_empresa, data_cadastro, data_fim, data_inicio, descricao, nome, situacao_projeto) 
    values
        (1, NOW(), NOW(), NOW(), 'Descrição do projeto 01', 'Projeto 01', 1);

-- Projeto02
insert 
    into
        Projeto
        (FK_empresa, data_cadastro, data_fim, data_inicio, descricao, nome, situacao_projeto) 
    values
        (1, NOW(), NOW(), NOW(), 'Descrição do projeto 01', 'Projeto 02', 1);

-- Sprint01
insert 
    into
        Sprint
        (data_cadastro, data_fechamento, data_fim, data_inicio, data_revisao, descricao, nome, FK_projeto, situacao_sprint) 
    values
        (now(), NULL, now(), now(), null, 'Descrição da Sprint 01', 'Sprint 01', 1, 0);

-- Sprint02
insert 
    into
        Sprint
        (data_cadastro, data_fechamento, data_fim, data_inicio, data_revisao, descricao, nome, FK_projeto, situacao_sprint) 
    values
        (now(), NULL, now(), now(), null, 'Descrição da Sprint 02', 'Sprint 02', 1, 0);

-- Item01 (Sprint01)
insert 
    into
        ItemBacklog
        (criterio_aceitacao, descricao, nome, FK_projeto, roi, situacao_backlog, story_points, valor_negocio) 
    values
        ('Critério 01', 'Descrição do item de backlog 01', 'Item 01', 1, 2.0, 0, 2, 2.0);

-- Item02 (Sprint01)
insert 
    into
        ItemBacklog
        (criterio_aceitacao, descricao, nome, FK_projeto, roi, situacao_backlog, story_points, valor_negocio) 
    values
        ('Critério 02', 'Descrição do item de backlog 02', 'Item 03', 1, 2.0, 0, 2, 2.0);

-- Item03 (Sprint02)
insert 
    into
        ItemBacklog
        (criterio_aceitacao, descricao, nome, FK_projeto, roi, situacao_backlog, story_points, valor_negocio) 
    values
        ('Critério 03', 'Descrição do item de backlog 03', 'Item 03', 1, 2.0, 0, 2, 2.0);
-- Item04 (Sem associação)
insert 
    into
        ItemBacklog
        (criterio_aceitacao, descricao, nome, FK_projeto, roi, situacao_backlog, story_points, valor_negocio) 
    values
        ('Critério 04', 'Descrição do item de backlog 04', 'Item 04', 1, 2.0, 0, 2, 2.0);

-- Item05 (Sem associação)
insert 
    into
        ItemBacklog
        (criterio_aceitacao, descricao, nome, FK_projeto, roi, situacao_backlog, story_points, valor_negocio) 
    values
        ('Critério 05', 'Descrição do item de backlog 05', 'Item 05', 1, 2.0, 0, 2, 2.0);

-- Item06 (Sem associação)
insert 
    into
        ItemBacklog
        (criterio_aceitacao, descricao, nome, FK_projeto, roi, situacao_backlog, story_points, valor_negocio) 
    values
        ('Critério 06', 'Descrição do item de backlog 06', 'Item 06', 1, 2.0, 0, 2, 2.0);

insert
	into
		sprintbacklog
		(FK_sprint, FK_backlog, is_ativo)
	values
		(1, 1, true);

insert
	into
		sprintbacklog
		(FK_sprint, FK_backlog, is_ativo)
	values
		(1, 2, true);

insert
	into
		sprintbacklog
		(FK_sprint, FK_backlog, is_ativo)
	values
		(2, 3, true);

INSERT
	INTO `scrumming`.`tarefa`
		(`FK_itemBacklog`, `FK_usuario`, `nome`, `descricao`, `situacao_tarefa`, `tempo_estimado`) 
	VALUES 
		('1', '1', 'Tarefa 01', 'Descrição da tarefa 01', '0', '1');

INSERT
	INTO `scrumming`.`tarefa`
		(`FK_itemBacklog`, `FK_usuario`, `nome`, `descricao`, `situacao_tarefa`, `tempo_estimado`) 
	VALUES 
		('1', '1', 'Tarefa 02', 'Descrição da tarefa 02', '0', '1');

INSERT
	INTO `scrumming`.`tarefa`
		(`FK_itemBacklog`, `FK_usuario`, `nome`, `descricao`, `situacao_tarefa`, `tempo_estimado`) 
	VALUES 
		('1', '1', 'Tarefa 03', 'Descrição da tarefa 03', '0', '1');

INSERT
	INTO `scrumming`.`tarefa`
		(`FK_itemBacklog`, `FK_usuario`, `nome`, `descricao`, `situacao_tarefa`, `tempo_estimado`) 
	VALUES 
		('2', '1', 'Tarefa 04', 'Descrição da tarefa 04', '0', '1');

INSERT
	INTO `scrumming`.`tarefa`
		(`FK_itemBacklog`, `FK_usuario`, `nome`, `descricao`, `situacao_tarefa`, `tempo_estimado`) 
	VALUES 
		('2', '1', 'Tarefa 05', 'Descrição da tarefa 05', '0', '1');

INSERT
	INTO `scrumming`.`dailyscrum`
		(`FK_sprint`, `local_meeting`, `dataHoraMarcada`, `duracao`) 
	VALUES 
		('3', 'Sala 5', now(), '2');

INSERT
	INTO `scrumming`.`dailyscrum`
		(`FK_sprint`, `local_meeting`, `dataHoraMarcada`, `duracao`) 
	VALUES 
		('3', 'Sala 5', now(), '2');

INSERT
	INTO `scrumming`.`dailyscrum`
		(`FK_sprint`, `local_meeting`, `dataHoraMarcada`, `duracao`) 
	VALUES 
		('3', 'Sala 5', now(), '2');

INSERT
	INTO `scrumming`.`dailyscrum`
		(`FK_sprint`, `local_meeting`, `dataHoraMarcada`, `duracao`) 
	VALUES 
		('3', 'Sala 5', now(), '2');

INSERT
	INTO `scrumming`.`dailyscrum`
		(`FK_sprint`, `local_meeting`, `dataHoraMarcada`, `duracao`) 
	VALUES 
		('3', 'Sala 5', now(), '2');

-- *******************************************************************************
--                                Consultas
-- *******************************************************************************

-- select * from Projeto;
-- select * from Sprint;
-- select * from itembacklog;
-- select * from usuarioempresa;
-- select * from timeprojeto;
-- select * from sprintBacklog;
-- select * from tarefa;
-- select * from usuario;
-- select * from empresa;
-- select * from dailyscrum;
-- select * from tarefaReporte;
-- select * from tarefaFavorita;
-- select * from config;

-- *******************************************************************************
--                               FIM DO SCRIPT
-- *******************************************************************************