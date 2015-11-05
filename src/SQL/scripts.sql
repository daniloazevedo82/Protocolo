-- CREATE DATABASE plugin;

CREATE SCHEMA academico;
CREATE SCHEMA administrativo;

CREATE SEQUENCE administrativo.sq_tipoprocesso;
CREATE TABLE administrativo.tipoprocesso(
	id integer,
	nome character varying,
	CONSTRAINT tipoprocesso_pk PRIMARY KEY (id)
);

CREATE SEQUENCE administrativo.sq_pessoa;
CREATE TABLE administrativo.pessoa(
	id integer,
	RG character varying,
	CPF character varying(15),
	nome character varying,
	sexo integer,
	dataNascimento date,
	endereco character varying,
	telefone character varying,
	email character varying,
	idCategoria integer,
	CONSTRAINT pessoa_pk PRIMARY KEY (id)
);
ALTER TABLE administrativo.pessoa ADD COLUMN dtype character varying;

CREATE SEQUENCE administrativo.sq_usuario;
CREATE TABLE administrativo.usuario(
	id integer,
	login character varying,
	senha character varying,
	ativo boolean,
	idsetor integer,
	CONSTRAINT usuario_pk PRIMARY KEY (id),
	CONSTRAINT usuario_fk_0 FOREIGN KEY (idsetor)
		REFERENCES administrativo.setor (id)
		ON UPDATE NO ACTION ON DELETE NO ACTION
);

CREATE SEQUENCE administrativo.sq_cargo;
CREATE TABLE administrativo.cargo(
	id integer,
	nome character varying,
	cargoChefia boolean,
	CONSTRAINT cargo_pk PRIMARY KEY (id)
);

CREATE SEQUENCE academico.sq_modalidadecurso;
CREATE TABLE academico.modalidadecurso(
	id integer,
	nome character varying,
	CONSTRAINT modalidadecurso_pk PRIMARY KEY (id)
);

CREATE SEQUENCE academico.sq_curso;
CREATE TABLE academico.curso(
	id integer,
	nome character varying,
	idModalidadeCurso integer,
	CONSTRAINT curso_pk PRIMARY KEY (id),
	CONSTRAINT curso_fk_0 FOREIGN KEY (idModalidadeCurso)
		REFERENCES academico.modalidadecurso (id)
		ON UPDATE NO ACTION ON DELETE NO ACTION
);

CREATE SEQUENCE academico.sq_aluno;
CREATE TABLE academico.aluno(
	id integer,
	matricula character varying,
	idcurso integer,
	CONSTRAINT aluno_pk PRIMARY KEY (id),
	CONSTRAINT aluno_fk_0 FOREIGN KEY (idcurso)
		REFERENCES academico.curso (id)
		ON UPDATE NO ACTION ON DELETE NO ACTION
);

CREATE SEQUENCE administrativo.sq_tiposervidor;
CREATE TABLE administrativo.tiposervidor(
	id integer,
	tipo character varying,
	CONSTRAINT tiposervidor_pf PRIMARY KEY (id)
);

CREATE SEQUENCE administrativo.sq_servidor;
CREATE TABLE administrativo.servidor(
	id integer,
	SIAPE character varying,
	Professorcol character varying,
	cargaHoraria integer,
	idPessoa integer,
	idTipoServidor integer,
	idCargo integer,
	idSetor integer,
	CONSTRAINT servidor_pk PRIMARY KEY (id),
	CONSTRAINT servidor_fk_0 FOREIGN KEY (idPessoa)
		REFERENCES administrativo.pessoa (id)
		ON UPDATE NO ACTION ON DELETE NO ACTION,
	CONSTRAINT servidor_fk_1 FOREIGN KEY (idTipoServidor)
		REFERENCES administrativo.tiposervidor (id)
		ON UPDATE NO ACTION ON DELETE NO ACTION,
	CONSTRAINT servidor_fk_2 FOREIGN KEY (idCargo)
		REFERENCES administrativo.cargo (id)
		ON UPDATE NO ACTION ON DELETE NO ACTION
);

CREATE SEQUENCE administrativo.sq_setor;
CREATE TABLE administrativo.setor(
	id integer,
	nome character varying,
	idCoordenador integer,
	isCoordenacao boolean,
	CONSTRAINT setor_pk PRIMARY KEY (id)
);

ALTER TABLE administrativo.servidor
	ADD CONSTRAINT servidor_fk_3 FOREIGN KEY (idSetor)
	REFERENCES administrativo.setor (id)
	ON UPDATE NO ACTION ON DELETE NO ACTION;
	
ALTER TABLE administrativo.setor
	ADD CONSTRAINT setor_fk_0 FOREIGN KEY (idCoordenador)
	REFERENCES administrativo.servidor (id)
	ON UPDATE NO ACTION ON DELETE NO ACTION;
	
CREATE SEQUENCE administrativo.sq_etapa;
CREATE TABLE administrativo.etapa(
	id integer,
	nome character varying,
	nrSequencia integer,
	temParecer boolean default false,
	permiteAnexo boolean default false,
	idSetor integer,
	idTipoProcesso integer,
	idCargoAutorizador int,
	CONSTRAINT etapa_pk PRIMARY KEY (id),
	CONSTRAINT etapa_fk_0 FOREIGN KEY (idSetor)
		REFERENCES administrativo.setor (id)
		ON UPDATE NO ACTION ON DELETE NO ACTION,
	CONSTRAINT etapa_fk_1 FOREIGN KEY (idTipoProcesso)
		REFERENCES administrativo.tipoprocesso (id)
		ON UPDATE NO ACTION ON DELETE NO ACTION,
	CONSTRAINT etapa_fk_2 FOREIGN KEY (idCargoAutorizador)
		REFERENCES administrativo.cargo (id)
		ON UPDATE NO ACTION ON DELETE NO ACTION
);

CREATE SEQUENCE administrativo.sq_processo;
CREATE TABLE administrativo.processo(
	id integer,
	data date,
	idTipoProcesso integer,
	dtype character varying,
	CONSTRAINT processo_pk PRIMARY KEY (id),
	CONSTRAINT processo_fk_0 FOREIGN KEY (idTipoProcesso)
		REFERENCES administrativo.tipoprocesso (id)
		ON UPDATE NO ACTION ON DELETE NO ACTION
);

CREATE SEQUENCE administrativo.sq_historico;
CREATE TABLE administrativo.historico(
	id integer,
	descricao character varying,
	observacao character varying,
	parecer integer,
	dataHoraInicio timestamp without time zone,
	dataHoraFim timestamp without time zone,
	idUsuario integer,
	idEtapasProcesso integer,
	CONSTRAINT historico_pk PRIMARY KEY (id),
	CONSTRAINT historico_fk_0 FOREIGN KEY (idUsuario)
		REFERENCES administrativo.usuario (id)
		ON UPDATE NO ACTION ON DELETE NO ACTION,
	CONSTRAINT historico_fk_1 FOREIGN KEY (idEtapasProcesso)
		REFERENCES administrativo.etapasprocesso (id)
		ON UPDATE NO ACTION ON DELETE NO ACTION
);

CREATE SEQUENCE administrativo.sq_arquivo;
CREATE TABLE administrativo.arquivo(
	id integer,
	nome character varying,
	caminho character varying,
	idHistorico integer,
	CONSTRAINT arquivo_pk PRIMARY KEY (id),
	CONSTRAINT arquivo_fk_0 FOREIGN KEY (idHistorico)
		REFERENCES administrativo.historico (id)
		ON DELETE NO ACTION ON UPDATE NO ACTION
);

CREATE SEQUENCE administrativo.sq_tiporequisicao;
CREATE TABLE administrativo.tiporequisicao(
	id integer,
	nome character varying,
	idNivel int,
	CONSTRAINT tiporequisicao_pk PRIMARY KEY (id)
);

CREATE SEQUENCE administrativo.sq_tipodocumentoacademico;
CREATE TABLE administrativo.tipodocumentoacademico(
	id integer,
	nome character varying,
	CONSTRAINT tipodocumentoacademico_pk PRIMARY KEY (id)
);

CREATE SEQUENCE administrativo.sq_processorequerimentoacademico;
CREATE TABLE administrativo.processorequerimentoacademico(
	id integer,
	observacoes character varying,
	idaluno integer,
	CONSTRAINT processorequerimentoacademico_pk PRIMARY KEY (id),
	CONSTRAINT processorequerimentoacademico_fk_0 FOREIGN KEY (idaluno)
		REFERENCES academico.aluno (id)
		ON UPDATE NO ACTION ON DELETE NO ACTION
);

CREATE SEQUENCE administrativo.sq_documentorequerimento;
CREATE TABLE administrativo.documentorequerimento(
	id integer,
	data date,
	idProcessoRequerimentoAcademico integer,
	idTipoDocumentoAcademico integer,
	idArquivo integer,
	CONSTRAINT documentorequerimento_pk PRIMARY KEY (id),
	CONSTRAINT documentorequerimento_fk_0 FOREIGN KEY (idProcessoRequerimentoAcademico)
		REFERENCES administrativo.processorequerimentoacademico (id)
		ON UPDATE NO ACTION ON DELETE NO ACTION,
	CONSTRAINT documentorequerimento_fk_1 FOREIGN KEY (idTipoDocumentoAcademico)
		REFERENCES academico.tipodocumentoacademico (id)
		ON UPDATE NO ACTION ON DELETE NO ACTION,
	CONSTRAINT documentorequerimento_fk_2 FOREIGN KEY (idArquivo)
		REFERENCES administrativo.arquivo (id)
		ON UPDATE NO ACTION ON DELETE NO ACTION
);

CREATE SEQUENCE administrativo.sq_etapasprocesso;
CREATE TABLE administrativo.etapasprocesso(
	id integer,
	dataInicio date,
	dataFim date,
	status integer,
	parecer integer,
	idEtapa integer,
	idProcessoRequerimento integer,
	CONSTRAINT etapasprocesso_pk PRIMARY KEY (id),
	CONSTRAINT etapasprocesso_fk_0 FOREIGN KEY (idEtapa)
		REFERENCES administrativo.etapa (id)
		ON UPDATE NO ACTION ON DELETE NO ACTION,
	CONSTRAINT etapasprocesso_fk_1 FOREIGN KEY (idProcessoRequerimento)
		REFERENCES administrativo.processorequerimentoacademico (id)
		ON UPDATE NO ACTION ON DELETE NO ACTION
);

CREATE SEQUENCE academico.sq_tipodocumentoacademico;
CREATE TABLE academico.tipodocumentoacademico(
	id integer,
	nome character varying,
	CONSTRAINT tipodocumentoacademico_pk PRIMARY KEY (id)
);

-- INSERTS TABELA TIPOPROCESSO
INSERT INTO administrativo.TipoProcesso (id, nome) VALUES (1, 'Progressão Funcional');
INSERT INTO administrativo.TipoProcesso (id, nome) VALUES (2, 'Solicitação de compras');
INSERT INTO administrativo.TipoProcesso (id, nome) VALUES (3, 'Aproveitamento de Conteúdo');
INSERT INTO administrativo.TipoProcesso (id, nome) VALUES (4, 'Exercício Domiciliar');
INSERT INTO administrativo.TipoProcesso (id, nome) VALUES (5, 'Reintegração de curso');
INSERT INTO administrativo.TipoProcesso (id, nome) VALUES (6, 'Revisão de avaliação de disciplina');
INSERT INTO administrativo.TipoProcesso (id, nome) VALUES (7, 'Segunda chamada de disciplina');
INSERT INTO administrativo.TipoProcesso (id, nome) VALUES (8, 'Trancamento de disciplina');
INSERT INTO administrativo.TipoProcesso (id, nome) VALUES (9, 'Transferência interna');
INSERT INTO administrativo.TipoProcesso (id, nome) VALUES (10, 'Transferência externa');

-- INSERTS TABELA SETOR
INSERT INTO administrativo.setor (id, nome) VALUES(1, 'Protocolo');
INSERT INTO administrativo.setor (id, nome) VALUES(2, 'DEN');
INSERT INTO administrativo.setor (id, nome) VALUES(3, 'DG');
INSERT INTO administrativo.setor (id, nome) VALUES(4, 'DIREH');
INSERT INTO administrativo.setor (id, nome) VALUES(5, 'Planejamento');
INSERT INTO administrativo.setor (id, nome) VALUES(6, 'DAP');
INSERT INTO administrativo.setor (id, nome) VALUES(7, 'Setor de compras');
INSERT INTO administrativo.setor (id, nome) VALUES(8, 'CORES');
INSERT INTO administrativo.setor (id, nome) VALUES(9, 'Coordenação de BSI');
INSERT INTO administrativo.setor (id, nome) VALUES(10, 'Coordenação de Informática');

-- INSERTS TABELA ETAPA
INSERT INTO administrativo.etapa(id, nome, nrsequencia, permiteanexo, idsetor, idtipoprocesso, temparecer) VALUES (nextval('administrativo.sq_etapa'), 'Cadastro de documentos',1,false,null,1,false);
INSERT INTO administrativo.etapa(id, nome, nrsequencia, permiteanexo, idsetor, idtipoprocesso, temparecer) VALUES (nextval('administrativo.sq_etapa'), 'Busca por assinaturas',2,false,null,1,false);
INSERT INTO administrativo.etapa(id, nome, nrsequencia, permiteanexo, idsetor, idtipoprocesso, temparecer) VALUES (nextval('administrativo.sq_etapa'), 'Encaminhamento ao protocolo',3,false,1,1,false);
INSERT INTO administrativo.etapa(id, nome, nrsequencia, permiteanexo, idsetor, idtipoprocesso, temparecer) VALUES (nextval('administrativo.sq_etapa'), 'Encaminhamento ao DEN',4,false,1,1,false);
INSERT INTO administrativo.etapa(id, nome, nrsequencia, permiteanexo, idsetor, idtipoprocesso, temparecer) VALUES (nextval('administrativo.sq_etapa'), 'Solicitação de nomeação da comissão',5,true,2,1,false);
INSERT INTO administrativo.etapa(id, nome, nrsequencia, permiteanexo, idsetor, idtipoprocesso, temparecer) VALUES (nextval('administrativo.sq_etapa'), 'Nomeação da comissão',6,true,3,1,false);
INSERT INTO administrativo.etapa(id, nome, nrsequencia, permiteanexo, idsetor, idtipoprocesso, temparecer) VALUES (nextval('administrativo.sq_etapa'), 'Convocação da comissão',7,true,2,1,false);
INSERT INTO administrativo.etapa(id, nome, nrsequencia, permiteanexo, idsetor, idtipoprocesso, temparecer) VALUES (nextval('administrativo.sq_etapa'), 'Avaliação do pedido ',8,false,null,1,false);
INSERT INTO administrativo.etapa(id, nome, nrsequencia, permiteanexo, idsetor, idtipoprocesso, temparecer) VALUES (nextval('administrativo.sq_etapa'), 'Ciência ao solicitante',9,true,2,1,false);
INSERT INTO administrativo.etapa(id, nome, nrsequencia, permiteanexo, idsetor, idtipoprocesso, temparecer) VALUES (nextval('administrativo.sq_etapa'), 'Envio para CPPD',10,false,4,1,false);

INSERT INTO administrativo.etapa(id, nome, nrsequencia, permiteanexo, idsetor, idtipoprocesso, temparecer) VALUES (nextval('administrativo.sq_etapa'), 'Solicitação de compra',1,false,null,2,false);
INSERT INTO administrativo.etapa(id, nome, nrsequencia, permiteanexo, idsetor, idtipoprocesso, temparecer) VALUES (nextval('administrativo.sq_etapa'), 'Aprovação pela chefia imediata',2,false,null,2,true);
INSERT INTO administrativo.etapa(id, nome, nrsequencia, permiteanexo, idsetor, idtipoprocesso, temparecer) VALUES (nextval('administrativo.sq_etapa'), 'Análise da solicitação',3,false,5,2,false);
INSERT INTO administrativo.etapa(id, nome, nrsequencia, permiteanexo, idsetor, idtipoprocesso, temparecer) VALUES (nextval('administrativo.sq_etapa'), 'Análise da solicitação',4,false,6,2,false);
INSERT INTO administrativo.etapa(id, nome, nrsequencia, permiteanexo, idsetor, idtipoprocesso, temparecer) VALUES (nextval('administrativo.sq_etapa'), 'Impressão e assinaturas',5,false,7,2,false);
INSERT INTO administrativo.etapa(id, nome, nrsequencia, permiteanexo, idsetor, idtipoprocesso, temparecer) VALUES (nextval('administrativo.sq_etapa'), 'Compra dos itens',6,false,7,2,false);
INSERT INTO administrativo.etapa(id, nome, nrsequencia, permiteanexo, idsetor, idtipoprocesso, temparecer) VALUES (nextval('administrativo.sq_etapa'), 'Atendimento da solicitação',7,false,7,2,false);

INSERT INTO administrativo.etapa(id, nome, nrsequencia, permiteanexo, idsetor, idtipoprocesso, temparecer) VALUES (nextval('administrativo.sq_etapa'), 'Solicitação',1,false,1,3,false);
INSERT INTO administrativo.etapa(id, nome, nrsequencia, permiteanexo, idsetor, idtipoprocesso, temparecer) VALUES (nextval('administrativo.sq_etapa'), 'Encaminhamento ao setor responsável',2,false,1,3,false);
INSERT INTO administrativo.etapa(id, nome, nrsequencia, permiteanexo, idsetor, idtipoprocesso, temparecer) VALUES (nextval('administrativo.sq_etapa'), 'Montagem do processo',3,true,8,3,false);
INSERT INTO administrativo.etapa(id, nome, nrsequencia, permiteanexo, idsetor, idtipoprocesso, temparecer) VALUES (nextval('administrativo.sq_etapa'), 'Encaminhamento à coordenação',4,true,8,3,false);
INSERT INTO administrativo.etapa(id, nome, nrsequencia, permiteanexo, idsetor, idtipoprocesso, temparecer) VALUES (nextval('administrativo.sq_etapa'), 'Encaminhamento ao professor da disciplina',5,true,null,3,false);
INSERT INTO administrativo.etapa(id, nome, nrsequencia, permiteanexo, idsetor, idtipoprocesso, temparecer) VALUES (nextval('administrativo.sq_etapa'), 'Avaliação pelo professor',6,true,null,3,true);
INSERT INTO administrativo.etapa(id, nome, nrsequencia, permiteanexo, idsetor, idtipoprocesso, temparecer) VALUES (nextval('administrativo.sq_etapa'), 'Ecaminhamento para atualização de registro acadêmico',7,true,null,3,false);

INSERT INTO administrativo.etapa(id, nome, nrsequencia, permiteanexo, idsetor, idtipoprocesso, temparecer) VALUES (nextval('administrativo.sq_etapa'), 'Solicitação',1,false,1,4,false);
INSERT INTO administrativo.etapa(id, nome, nrsequencia, permiteanexo, idsetor, idtipoprocesso, temparecer) VALUES (nextval('administrativo.sq_etapa'), 'Encaminhamento ao setor responsável',2,false,1,4,false);
INSERT INTO administrativo.etapa(id, nome, nrsequencia, permiteanexo, idsetor, idtipoprocesso, temparecer) VALUES (nextval('administrativo.sq_etapa'), 'Montagem do processo',3,true,8,4,false);
INSERT INTO administrativo.etapa(id, nome, nrsequencia, permiteanexo, idsetor, idtipoprocesso, temparecer) VALUES (nextval('administrativo.sq_etapa'), 'Encaminhamento à coordenação',4,true,8,4,false);
INSERT INTO administrativo.etapa(id, nome, nrsequencia, permiteanexo, idsetor, idtipoprocesso, temparecer) VALUES (nextval('administrativo.sq_etapa'), 'Apreciação pelo colegiado',5,true,null,4,true);

INSERT INTO administrativo.etapa(id, nome, nrsequencia, permiteanexo, idsetor, idtipoprocesso, temparecer) VALUES (nextval('administrativo.sq_etapa'), 'Solicitação',1,false,1,5,false);
INSERT INTO administrativo.etapa(id, nome, nrsequencia, permiteanexo, idsetor, idtipoprocesso, temparecer) VALUES (nextval('administrativo.sq_etapa'), 'Encaminhamento ao setor responsável',2,false,1,5,false);
INSERT INTO administrativo.etapa(id, nome, nrsequencia, permiteanexo, idsetor, idtipoprocesso, temparecer) VALUES (nextval('administrativo.sq_etapa'), 'Montagem do processo',3,true,8,5,false);
INSERT INTO administrativo.etapa(id, nome, nrsequencia, permiteanexo, idsetor, idtipoprocesso, temparecer) VALUES (nextval('administrativo.sq_etapa'), 'Encaminhamento à coordenação',4,true,8,5,false);
INSERT INTO administrativo.etapa(id, nome, nrsequencia, permiteanexo, idsetor, idtipoprocesso, temparecer) VALUES (nextval('administrativo.sq_etapa'), 'Avaliação pela coordenação',5,true,null,5,true);

INSERT INTO administrativo.etapa(id, nome, nrsequencia, permiteanexo, idsetor, idtipoprocesso, temparecer) VALUES (nextval('administrativo.sq_etapa'), 'Solicitação',1,false,1,6,false);
INSERT INTO administrativo.etapa(id, nome, nrsequencia, permiteanexo, idsetor, idtipoprocesso, temparecer) VALUES (nextval('administrativo.sq_etapa'), 'Encaminhamento ao setor responsável',2,false,1,6,false);
INSERT INTO administrativo.etapa(id, nome, nrsequencia, permiteanexo, idsetor, idtipoprocesso, temparecer) VALUES (nextval('administrativo.sq_etapa'), 'Montagem do processo',3,true,8,6,false);
INSERT INTO administrativo.etapa(id, nome, nrsequencia, permiteanexo, idsetor, idtipoprocesso, temparecer) VALUES (nextval('administrativo.sq_etapa'), 'Encaminhamento à coordenação',4,true,8,6,false);
INSERT INTO administrativo.etapa(id, nome, nrsequencia, permiteanexo, idsetor, idtipoprocesso, temparecer) VALUES (nextval('administrativo.sq_etapa'), 'Avaliação pela coordenação',5,true,null,6,true);

INSERT INTO administrativo.etapa(id, nome, nrsequencia, permiteanexo, idsetor, idtipoprocesso, temparecer) VALUES (nextval('administrativo.sq_etapa'), 'Solicitação',1,false,1,7,false);
INSERT INTO administrativo.etapa(id, nome, nrsequencia, permiteanexo, idsetor, idtipoprocesso, temparecer) VALUES (nextval('administrativo.sq_etapa'), 'Encaminhamento ao setor responsável',2,false,1,7,false);
INSERT INTO administrativo.etapa(id, nome, nrsequencia, permiteanexo, idsetor, idtipoprocesso, temparecer) VALUES (nextval('administrativo.sq_etapa'), 'Montagem do processo',3,true,8,7,false);
INSERT INTO administrativo.etapa(id, nome, nrsequencia, permiteanexo, idsetor, idtipoprocesso, temparecer) VALUES (nextval('administrativo.sq_etapa'), 'Encaminhamento à coordenação',4,true,8,7,false);
INSERT INTO administrativo.etapa(id, nome, nrsequencia, permiteanexo, idsetor, idtipoprocesso, temparecer) VALUES (nextval('administrativo.sq_etapa'), 'Apreciação pelo colegiado',5,true,null,7,true);
INSERT INTO administrativo.etapa(id, nome, nrsequencia, permiteanexo, idsetor, idtipoprocesso, temparecer) VALUES (nextval('administrativo.sq_etapa'), 'Parecer final do Departamento de Ensino',6,true,2,7,true);

INSERT INTO administrativo.etapa(id, nome, nrsequencia, permiteanexo, idsetor, idtipoprocesso, temparecer) VALUES (nextval('administrativo.sq_etapa'), 'Solicitação',1,false,1,8,false);
INSERT INTO administrativo.etapa(id, nome, nrsequencia, permiteanexo, idsetor, idtipoprocesso, temparecer) VALUES (nextval('administrativo.sq_etapa'), 'Encaminhamento ao setor responsável',2,false,1,8,false);
INSERT INTO administrativo.etapa(id, nome, nrsequencia, permiteanexo, idsetor, idtipoprocesso, temparecer) VALUES (nextval('administrativo.sq_etapa'), 'Montagem do processo',3,true,8,8,false);
INSERT INTO administrativo.etapa(id, nome, nrsequencia, permiteanexo, idsetor, idtipoprocesso, temparecer) VALUES (nextval('administrativo.sq_etapa'), 'Encaminhamento à coordenação',4,true,8,8,false);
INSERT INTO administrativo.etapa(id, nome, nrsequencia, permiteanexo, idsetor, idtipoprocesso, temparecer) VALUES (nextval('administrativo.sq_etapa'), 'Encaminhamento ao professor da disciplina',5,true,null,8,false);
INSERT INTO administrativo.etapa(id, nome, nrsequencia, permiteanexo, idsetor, idtipoprocesso, temparecer) VALUES (nextval('administrativo.sq_etapa'), 'Avaliação pelo professor',6,true,null,8,true);
INSERT INTO administrativo.etapa(id, nome, nrsequencia, permiteanexo, idsetor, idtipoprocesso, temparecer) VALUES (nextval('administrativo.sq_etapa'), 'Aceite do aluno',7,false,null,8,true);
INSERT INTO administrativo.etapa(id, nome, nrsequencia, permiteanexo, idsetor, idtipoprocesso, temparecer) VALUES (nextval('administrativo.sq_etapa'), 'Montagem da banca',8,true,null,8,false);
INSERT INTO administrativo.etapa(id, nome, nrsequencia, permiteanexo, idsetor, idtipoprocesso, temparecer) VALUES (nextval('administrativo.sq_etapa'), 'Avaliação pela banca',9,true,null,8,true);

INSERT INTO administrativo.etapa(id, nome, nrsequencia, permiteanexo, idsetor, idtipoprocesso, temparecer) VALUES (nextval('administrativo.sq_etapa'), 'Solicitação',1,false,1,9,false);
INSERT INTO administrativo.etapa(id, nome, nrsequencia, permiteanexo, idsetor, idtipoprocesso, temparecer) VALUES (nextval('administrativo.sq_etapa'), 'Encaminhamento ao setor responsável',2,false,1,9,false);
INSERT INTO administrativo.etapa(id, nome, nrsequencia, permiteanexo, idsetor, idtipoprocesso, temparecer) VALUES (nextval('administrativo.sq_etapa'), 'Montagem do processo',3,true,8,9,false);
INSERT INTO administrativo.etapa(id, nome, nrsequencia, permiteanexo, idsetor, idtipoprocesso, temparecer) VALUES (nextval('administrativo.sq_etapa'), 'Encaminhamento à coordenação',4,true,8,9,false);
INSERT INTO administrativo.etapa(id, nome, nrsequencia, permiteanexo, idsetor, idtipoprocesso, temparecer) VALUES (nextval('administrativo.sq_etapa'), 'Encaminhamento ao colegiado',5,true,null,9,false);
INSERT INTO administrativo.etapa(id, nome, nrsequencia, permiteanexo, idsetor, idtipoprocesso, temparecer) VALUES (nextval('administrativo.sq_etapa'), 'Elaboração de processo seletivo',6,true,null,9,false);
INSERT INTO administrativo.etapa(id, nome, nrsequencia, permiteanexo, idsetor, idtipoprocesso, temparecer) VALUES (nextval('administrativo.sq_etapa'), 'Encaminhamento de resultado ao DEN',7,true,null,9,false);
INSERT INTO administrativo.etapa(id, nome, nrsequencia, permiteanexo, idsetor, idtipoprocesso, temparecer) VALUES (nextval('administrativo.sq_etapa'), 'Homologação de resultado',8,true,2,9,false);

INSERT INTO administrativo.etapa(id, nome, nrsequencia, permiteanexo, idsetor, idtipoprocesso, temparecer) VALUES (nextval('administrativo.sq_etapa'), 'Solicitação',1,false,1,10,false);
INSERT INTO administrativo.etapa(id, nome, nrsequencia, permiteanexo, idsetor, idtipoprocesso, temparecer) VALUES (nextval('administrativo.sq_etapa'), 'Encaminhamento ao setor responsável',2,false,1,10,false);
INSERT INTO administrativo.etapa(id, nome, nrsequencia, permiteanexo, idsetor, idtipoprocesso, temparecer) VALUES (nextval('administrativo.sq_etapa'), 'Montagem do processo',3,true,8,10,false);
INSERT INTO administrativo.etapa(id, nome, nrsequencia, permiteanexo, idsetor, idtipoprocesso, temparecer) VALUES (nextval('administrativo.sq_etapa'), 'Encaminhamento à coordenação',4,true,8,10,false);
INSERT INTO administrativo.etapa(id, nome, nrsequencia, permiteanexo, idsetor, idtipoprocesso, temparecer) VALUES (nextval('administrativo.sq_etapa'), 'Encaminhamento ao colegiado',5,true,null,10,false);
INSERT INTO administrativo.etapa(id, nome, nrsequencia, permiteanexo, idsetor, idtipoprocesso, temparecer) VALUES (nextval('administrativo.sq_etapa'), 'Elaboração de processo seletivo',6,true,null,10,false);
INSERT INTO administrativo.etapa(id, nome, nrsequencia, permiteanexo, idsetor, idtipoprocesso, temparecer) VALUES (nextval('administrativo.sq_etapa'), 'Encaminhamento de resultado ao DEN',7,true,null,10,false);
INSERT INTO administrativo.etapa(id, nome, nrsequencia, permiteanexo, idsetor, idtipoprocesso, temparecer) VALUES (nextval('administrativo.sq_etapa'), 'Homologação de resultado',8,true,2,10,false);

UPDATE administrativo.etapa set idsetor = 1 where nrsequencia = 1;
ALTER TABLE administrativo.etapa ADD COLUMN ultimaEtapa boolean DEFAULT false;
ALTER TABLE administrativo.etapa ADD COLUMN primeiraetapa boolean DEFAULT false;

INSERT INTO administrativo.usuario(id, login, senha, ativo, idsetor) VALUES (nextval('administrativo.sq_usuario'), 'protocolo', '123456', true, 1);
INSERT INTO administrativo.usuario(id, login, senha, ativo, idsetor) VALUES (nextval('administrativo.sq_usuario'), 'den', '123456', true, 2);
INSERT INTO administrativo.usuario(id, login, senha, ativo, idsetor) VALUES (nextval('administrativo.sq_usuario'), 'cores', '123456', true, 8);
INSERT INTO administrativo.usuario(id, login, senha, ativo, idsetor) VALUES (nextval('administrativo.sq_usuario'), 'csi', '123456', true, 9);
