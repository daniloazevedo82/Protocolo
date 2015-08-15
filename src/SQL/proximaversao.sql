-----------------------------------------------------------------------AT #88442-----------------------------------------------------------------------------

ALTER TABLE folharesposta
  ADD CONSTRAINT folharesposta_cdaplicacaoavaliacao_fkey FOREIGN KEY (cdaplicacaoavaliacao) REFERENCES aplicacaoavaliacao (cdaplicacaoavaliacao)
   ON UPDATE NO ACTION ON DELETE NO ACTION;
 
DROP TABLE aplicacaoavaliacaosincronizacao;
CREATE TABLE aplicacaoavaliacaosincronizacao
(
  cdaplicacaoavaliacao integer, 
  idempresa integer,
  candidato character varying,
  estadocivil character varying, 
  sexo character varying,
  datanascimento character varying,
  escolaridade character varying,
  cargo character varying,
  dataaplicacao character varying, 
  idteste integer,
  listaresposta character varying,
  datasincronizacao timestamp without time zone,
  codigoerro integer,
  mensagemerro character varying,
  deletado timestamp without time zone,
  url character varying,
  CONSTRAINT aplicacaoavaliacaosincronizacao_cdaplicacaoavaliacao_pkey PRIMARY KEY (cdaplicacaoavaliacao)
);


-- Table: folharespostasincronizacao

DROP TABLE folharespostasincronizacao;

CREATE TABLE folharespostasincronizacao
(
  cdfolharesposta integer NOT NULL,
  cdaplicacaoavaliacao integer,
  cdquestao integer,
  resposta integer,
  datasincronizacao timestamp without time zone,
  CONSTRAINT folharespostasincronizacao_cdfolharesposta_pkey PRIMARY KEY (cdfolharesposta)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE folharespostasincronizacao
  OWNER TO postgres;


ALTER TABLE folharespostasincronizacao
  ADD CONSTRAINT folharespostasincronizacao_cdaplicacaoavaliacao_fkey FOREIGN KEY (cdaplicacaoavaliacao) REFERENCES aplicacaoavaliacaosincronizacao (cdaplicacaoavaliacao)
   ON UPDATE NO ACTION ON DELETE NO ACTION;
CREATE INDEX fki_folharespostasincronizacao_cdaplicacaoavaliacao_fkey
  ON folharespostasincronizacao(cdaplicacaoavaliacao);


CREATE OR REPLACE FUNCTION tg_folharesposta_ins_upd()
  RETURNS trigger AS
$trg_folharesposta_ins_upd$
    BEGIN
	IF(TG_OP = 'INSERT')THEN
		INSERT INTO folharespostasincronizacao (cdfolharesposta, cdaplicacaoavaliacao, cdquestao, resposta) values (NEW.cdfolharesposta, NEW.cdaplicacaoavaliacao, NEW.cdquestao, NEW.resposta);
	ELSEIF(TG_OP = 'UPDATE')THEN
		UPDATE folharespostasincronizacao 
		SET cdaplicacaoavaliacao = NEW.cdaplicacaoavaliacao, cdquestao = NEW.cdquestao, resposta = NEW.resposta, datasincronizacao = NULL 
		WHERE cdfolharesposta = NEW.cdfolharesposta;
	END IF;	
	RETURN NULL;
    END;
  $trg_folharesposta_ins_upd$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION tg_folharesposta_ins_upd()
  OWNER TO postgres;

DROP TRIGGER trg_folharesposta_ins_upd;

CREATE TRIGGER trg_folharesposta_ins_upd
  AFTER INSERT OR UPDATE OR DELETE
  ON folharesposta
  FOR EACH ROW
  EXECUTE PROCEDURE tg_folharesposta_ins_upd();
  
  


CREATE OR REPLACE FUNCTION tg_aplicacaoavaliacao_ins_upd_del()
  RETURNS trigger AS
$trg_aplicacaoavaliacao_ins_upd_del$
     DECLARE
	idempresa integer;
	candidato character varying; 
	estadocivil character varying;
	sexo character varying;
	dtnascimento character varying;
	escolaridade character varying;
	cargo character varying; 
	dtaplicacao character varying; 
    BEGIN
	IF(TG_OP != 'DELETE')THEN
		idempresa = (SELECT es.id FROM candidatoempresa ce
				LEFT OUTER JOIN empresasincronizacao es ON ce.cdempresa = es.cdpessoa
				WHERE ce.cdcandidatoempresa = NEW.cdcandidatoempresa);
		candidato = (SELECT p.nome FROM candidatoempresa ce 
				LEFT OUTER JOIN candidato c ON ce.cdcandidato = c.cdpessoa 
				LEFT OUTER JOIN pessoa p ON c.cdpessoa = p.cdpessoa 
				WHERE ce.cdcandidatoempresa = NEW.cdcandidatoempresa);
				
		estadocivil = (SELECT ec.nome FROM candidatoempresa ce
				LEFT OUTER JOIN candidato c ON ce.cdcandidato = c.cdpessoa
				LEFT OUTER JOIN estadocivil ec ON ec.cdestadocivil = c.cdestadocivil
				WHERE ce.cdcandidatoempresa = NEW.cdcandidatoempresa);

		sexo = (SELECT s.nome FROM candidatoempresa ce
			LEFT OUTER JOIN candidato c ON c.cdpessoa = ce.cdcandidato
			LEFT OUTER JOIN sexo s ON s.cdsexo = c.cdsexo
			WHERE ce.cdcandidatoempresa = NEW.cdcandidatoempresa);

		dtnascimento = to_char((SELECT c.dtnascimento FROM candidatoempresa ce
				LEFT OUTER JOIN candidato c ON ce.cdcandidato = c.cdpessoa
				WHERE ce.cdcandidatoempresa = NEW.cdcandidatoempresa), 'dd/MM/yyyy');

		escolaridade = (SELECT es.nome FROM candidatoempresa ce
				LEFT OUTER JOIN candidato c ON ce.cdcandidato = c.cdpessoa
				LEFT OUTER JOIN escolaridade es ON es.cdescolaridade = c.cdescolaridade
				WHERE ce.cdcandidatoempresa = NEW.cdcandidatoempresa);

		cargo = (SELECT c.cargo FROM candidatoempresa ce
			LEFT OUTER JOIN candidato c ON ce.cdcandidato = c.cdpessoa
			WHERE ce.cdcandidatoempresa = NEW.cdcandidatoempresa);

		dtaplicacao = to_char((NEW.dataaplicacao), 'dd/MM/yyyy');
	END IF;
			
	IF(TG_OP = 'INSERT') THEN
		INSERT INTO aplicacaoavaliacaosincronizacao (cdaplicacaoavaliacao, idempresa, candidato, estadocivil, sexo, datanascimento, escolaridade, cargo, dataaplicacao) 
		values (NEW.cdaplicacaoavaliacao, idempresa, candidato, estadocivil, sexo, dtnascimento, escolaridade, cargo, dtaplicacao);
	ELSEIF(TG_OP = 'UPDATE') THEN
		UPDATE aplicacaoavaliacaosincronizacao
		SET dataaplicacao = to_char(NEW.dataaplicacao, 'dd/MM/yyyy'), datasincronizacao = NULL
		WHERE cdaplicacaoavaliacao = NEW.cdaplicacaoavaliacao;
	ELSEIF(TG_OP = 'DELETE') THEN
		UPDATE aplicacaoavaliacaosincronizacao
		SET deletado = current_timestamp, datasincronizacao = NULL
		WHERE cdaplicacaoavaliacao = OLD.cdaplicacaoavaliacao;
	END IF;
	RETURN NULL;		
    END;
  $trg_aplicacaoavaliacao_ins_upd_del$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION tg_aplicacaoavaliacao_ins_upd_del()
  OWNER TO postgres;

DROP TRIGGER trg_aplicacaoavaliacao_ins_upd_del on aplicacaoavaliacao;
CREATE TRIGGER trg_aplicacaoavaliacao_ins_upd_del
  AFTER INSERT OR UPDATE OR DELETE
  ON aplicacaoavaliacao
  FOR EACH ROW
  EXECUTE PROCEDURE tg_aplicacaoavaliacao_ins_upd_del();


CREATE OR REPLACE FUNCTION tg_candidato_upd()
  RETURNS trigger AS
$trg_candidato_upd$
     DECLARE
	nomecandidato character varying;
	estcivil character varying;
	genre character varying;
	escol character varying;
    BEGIN
	nomecandidato = (SELECT nome FROM pessoa WHERE cdpessoa = NEW.cdpessoa);
	estcivil = (SELECT nome FROM estadocivil WHERE cdestadocivil = NEW.cdestadocivil);
	genre = (SELECT nome FROM sexo WHERE cdsexo = NEW.cdsexo);
	escol = (SELECT nome FROM escolaridade WHERE cdescolaridade = NEW.cdescolaridade);
	UPDATE aplicacaoavaliacaosincronizacao 	
	SET candidato = nomecandidato, estadocivil = estcivil, sexo = genre, datanascimento = to_char(NEW.dtnascimento, 'dd/MM/yyyy'), 
	    escolaridade = escol, cargo = NEW.cargo
	WHERE cdaplicacaoavaliacao = (SELECT aa.cdaplicacaoavaliacao 
					FROM aplicacaoavaliacao aa 
					LEFT OUTER JOIN candidatoempresa ce ON aa.cdcandidatoempresa = ce.cdcandidatoempresa
					LEFT OUTER JOIN candidato c ON ce.cdcandidato = c.cdpessoa
					WHERE c.cdpessoa = NEW.cdpessoa);
	RETURN NULL;		
    END;
  $trg_candidato_upd$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION tg_candidato_upd()
  OWNER TO postgres;

DROP TRIGGER trg_candidato_upd on candidato;
  CREATE TRIGGER trg_candidato_upd
  AFTER UPDATE
  ON candidato
  FOR EACH ROW
  EXECUTE PROCEDURE tg_candidato_upd();
  

-- Function: tg_empresacargo_ins_upd_del()

-- DROP FUNCTION tg_empresacargo_ins_upd_del();

CREATE OR REPLACE FUNCTION tg_empresacargo_ins_upd_del()
  RETURNS trigger AS
$BODY$
    DECLARE 
	cargos character varying;
    BEGIN
	IF(TG_OP = 'INSERT' OR  TG_OP = 'UPDATE') THEN
		cargos = (SELECT array_to_string(array_agg(cs.id), ',') FROM empresacargo ec  JOIN cargosincronizacao cs ON cs.cdcargo=ec.cdcargo WHERE ec.cdempresa = NEW.cdempresa);
		UPDATE empresasincronizacao 
		SET listacargostr = cargos, dtsincronizacao = NULL
		WHERE cdpessoa = NEW.cdempresa;
	ELSEIF(TG_OP = 'DELETE') THEN
		cargos = (SELECT array_to_string(array_agg(cdcargo), ',') FROM empresacargo WHERE cdempresa = OLD.cdempresa);
		UPDATE empresasincronizacao 
		SET listacargostr = cargos, dtsincronizacao = NULL
		WHERE cdpessoa = OLD.cdempresa;
	END IF;	
	RETURN NULL;		
    END;
  $BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION tg_empresacargo_ins_upd_del()
  OWNER TO postgres;


-- Trigger: trg_empresacargo_ins_upd_del on empresacargo

DROP TRIGGER trg_empresacargo_ins_upd_del ON empresacargo;

CREATE TRIGGER trg_empresacargo_ins_upd_del
  AFTER INSERT OR UPDATE OR DELETE
  ON empresacargo
  FOR EACH ROW
  EXECUTE PROCEDURE tg_empresacargo_ins_upd_del();
  
  
CREATE OR REPLACE FUNCTION tg_pessoa_upd()
  RETURNS trigger AS
$BODY$
    BEGIN
	UPDATE empresasincronizacao 
	SET nome = NEW.nome, cnpj = NEW.cnpj, email = NEW.email, dtsincronizacao = NULL
	WHERE cdpessoa = NEW.cdpessoa;


	UPDATE aplicacaoavaliacaosincronizacao
	SET candidato = NEW.nome
	WHERE cdaplicacaoavaliacao = (SELECT aa.cdaplicacaoavaliacao FROM aplicacaoavaliacao aa
					LEFT OUTER JOIN candidatoempresa ce ON aa.cdcandidatoempresa = ce.cdcandidatoempresa
					LEFT OUTER JOIN candidato c ON ce.cdcandidato = c.cdpessoa
					WHERE c.cdpessoa = NEW.cdpessoa);
	RETURN NULL;		
    END;
  $BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION tg_pessoa_upd()
  OWNER TO postgres;


CREATE TRIGGER trg_pessoa_upd
  AFTER UPDATE
  ON pessoa
  FOR EACH ROW
  EXECUTE PROCEDURE tg_pessoa_upd();

-------------------------------------------Autorização de trabalho #88490-----------------------------------------------------


DROP TABLE avaliacaocargo;
  
CREATE TABLE avaliacaocargo
(
  cdavaliacaocargo integer NOT NULL,
  cdavaliacao integer,
  cdcargo integer,
  CONSTRAINT avaliacaocargo_pkey PRIMARY KEY (cdavaliacaocargo),
  CONSTRAINT avaliacaocargo_cdavaliacao_fkey FOREIGN KEY (cdavaliacao)
      REFERENCES avaliacao (cdavaliacao) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT avaliacaocargo_cdcargo_fkey FOREIGN KEY (cdcargo)
      REFERENCES cargo (cdcargo) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);

DROP table avaliacaosegmento;
CREATE TABLE avaliacaosegmento
(
  cdavaliacaosegmento integer NOT NULL,
  cdavaliacao integer,
  cdsegmento integer,
  CONSTRAINT avaliacaosegmento_pkey PRIMARY KEY (cdavaliacaosegmento),
  CONSTRAINT avaliacaosegmento_cdavaliacao_fkey FOREIGN KEY (cdavaliacao)
      REFERENCES avaliacao (cdavaliacao) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT avaliacaosegmento_cdsegmento_fkey FOREIGN KEY (cdsegmento)
      REFERENCES segmento (cdsegmento) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);

DROP TABLE avaliacaoindicador;
  
CREATE TABLE avaliacaoindicador
(
  cdavaliacaoindicador integer NOT NULL,
  cdavaliacao integer,
  cdindicador integer,
  CONSTRAINT avaliacaoindicador_pkey PRIMARY KEY (cdavaliacaoindicador),
  CONSTRAINT avaliacaoindicador_cdavaliacao_fkey FOREIGN KEY (cdavaliacao)
      REFERENCES avaliacao (cdavaliacao) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT avaliacaoindicador_cdindicador_fkey FOREIGN KEY (cdindicador)
      REFERENCES indicador (cdindicador) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);


  
  
------------------------------------------------------------------------------------------------------------------------------------------------------------


---------------------------------------------------------------------AT #89616------------------------------------------------------------------------------

ALTER TABLE dimensao
   ADD COLUMN cdescala integer;

ALTER TABLE dimensao
  ADD CONSTRAINT dimensao_cdescala_fkey FOREIGN KEY (cdescala)
      REFERENCES indicador (cdindicador) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION;

--------------------------------------------------------------------------------------------------------------------------------------------------------------

--------------------------------------------------------------AT #88482---------------------------------------------------------------------------------------

ALTER TABLE empresarelatorio ADD COLUMN tipo integer;
ALTER TABLE empresarelatorio DROP COLUMN avaliacaow3erp;

------------------------------------------------------------------------------------------------------------------------------------------------------------


--------------------------------------------------------------AT #86540-------------------------------------------------------------------------------------

CREATE TABLE boletimmedicaostatus
(
  cdboletimmedicaostatus integer NOT NULL,
  nome character varying,
  CONSTRAINT boletimmedicaostatus_pkey PRIMARY KEY (cdboletimmedicaostatus)
);

CREATE SEQUENCE sq_boletimmedicaostatus
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;


CREATE OR REPLACE FUNCTION empresas_permissao_tela(
    pcdusuario integer,
    purl character varying,
    pacao character varying)
  RETURNS SETOF tp_cd_pessoa AS
$BODY$
declare
  registro  tp_cd_pessoa;
BEGIN
  --retorna as empresas cujo o usuario possui permissao naquela tela e geral
	FOR registro IN
	   SELECT eu.cdempresa
	   FROM empresausuario eu 
	   JOIN papel pap ON pap.cdpapel=eu.cdpapel
	   JOIN permissao perm ON pap.cdpapel=perm.cdpapel 
	   JOIN tela t ON perm.cdtela=t.cdtela
	   WHERE (
		(select perm.stringpermissao ~ ('.*'||pAcao||'=true.*'))=true or
		pap.administrador = true
	   )
	   AND pUrl LIKE '%'||t.path||'%'
	   AND eu.cdusuario=pCdusuario
  	LOOP
	   Return Next registro;
	END LOOP;
  
END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100
  ROWS 1000;
  
  update tela set descricao =  replace(descricao, 'Manter ', '');  
  ------------------------------------------------------------------------------------
  ALTER TABLE candidato DROP COLUMN email;  
---------------------------------------- AT #86305 ------------------------------------------------
  CREATE SEQUENCE sq_acao
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 99999999999999999
  START 1
  CACHE 1;
  
  CREATE SEQUENCE sq_acaopapel
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 99999999999999999
  START 8
  CACHE 1;
  
INSERT INTO acao (cdacao, descricao, key)
VALUES	(nextval('sq_acao'), 'Reenviar convite', 'REENVIAR_CONVITE'),
		(nextval('sq_acao'), 'Gerar CSV', 'GERAR_CSV'),
		(nextval('sq_acao'), 'Visualizar avaliação', 'VISUALIZAR_AVALIACAO'),
		(nextval('sq_acao'), 'Editar tempo', 'EDITAR_TEMPO'),
		(nextval('sq_acao'), 'Editar centro de custo', 'EDITAR_CENTRO_CUSTO'),
		(nextval('sq_acao'), 'Escolher colunas', 'ESCOLHER_COLUNAS_LISTAR_AVALIACOES'),
		(nextval('sq_acao'), 'Editar dados do candidato', 'EDITAR_DADOS_CANDIDATO'),
		(nextval('sq_acao'), 'Adicionar candidato', 'ADICIONAR_CANDIDATO'),
		(nextval('sq_acao'), 'Escolher colunas', 'ESCOLHER_COLUNAS_MIGRAR_AVALIACAO'),
		(nextval('sq_acao'), 'Exportar', 'EXPORTAR'),
		(nextval('sq_acao'), 'Migrar', 'MIGRAR'),
		(nextval('sq_acao'), 'Avaliação', 'AVALIACAO'),
		(nextval('sq_acao'), 'Reenviar boletim de medição', 'REENVIAR_BOLETIM_MEDICAO'),
		(nextval('sq_acao'), 'Gerar boletim de medição', 'GERAR_BOLETIM_MEDICAO'),
		(nextval('sq_acao'), 'Cancelar boletim de medição', 'CANCELAR_BOLETIM_MEDICAO'),
		(nextval('sq_acao'), 'Enviar dados de acesso', 'ENVIAR_DADOS_ACESSO'),
		(nextval('sq_acao'), 'Bloquear acesso', 'BLOQUEAR_ACESSO'),
		(nextval('sq_acao'), 'Desbloquear acesso', 'DESBLOQUEAR_ACESSO'),
		(nextval('sq_acao'), 'Reenviar e-mail', 'REENVIAR_EMAIL'),
		(nextval('sq_acao'), 'Gerar', 'GERAR'),
		(nextval('sq_acao'), 'Gerar como não psicólogo', 'GERAR_NAO_PSICOLOGO');

ALTER TABLE acaopapel
DROP CONSTRAINT fk_acaopapel_1;
ALTER TABLE acaopapel
ADD CONSTRAINT fk_acaopapel_1 FOREIGN KEY (cdpapel)
      REFERENCES papel (cdpapel) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE CASCADE;
	  
ALTER TABLE empresaavaliacao
DROP CONSTRAINT fk_cdempresa;
ALTER TABLE empresaavaliacao
ADD CONSTRAINT fk_cdempresa FOREIGN KEY (cdempresa)
      REFERENCES empresa (cdpessoa) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE CASCADE;
	  
ALTER TABLE empresacargo
DROP CONSTRAINT empresacargo_cdempresa_fkey;
ALTER TABLE empresacargo
ADD CONSTRAINT empresacargo_cdempresa_fkey FOREIGN KEY (cdempresa)
      REFERENCES empresa (cdpessoa) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE CASCADE;
	  
ALTER TABLE papel
DROP CONSTRAINT fk_papel_0;
ALTER TABLE papel
ADD CONSTRAINT fk_papel_0 FOREIGN KEY (cdempresa)
      REFERENCES empresa (cdpessoa) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE CASCADE;
-----------------------------------------------------------------------------------------------------------------------------------------------------------------

-------------------------------------------------------------------------AT #88450-------------------------------------------------------------------------------

ALTER TABLE aplicacaoavaliacaosincronizacao
  ADD CONSTRAINT aplicacaoavaliacaosincronizacao_cdaplicacaoavaliaco_fkey FOREIGN KEY (cdaplicacaoavaliacao) REFERENCES aplicacaoavaliacao (cdaplicacaoavaliacao)
   ON UPDATE NO ACTION ON DELETE NO ACTION;
   
   
------------------------------------------------------------------------------------------------------------------------------------------------------------------

------------------------------------------------------------------------AT #88466---------------------------------------------------------------------------------

CREATE OR REPLACE FUNCTION tg_cargo_ins_upd_del()
  RETURNS trigger AS
$BODY$
    DECLARE
	primeirasincronizacao timestamp without time zone;
    BEGIN
	IF(TG_OP = 'INSERT') THEN
		INSERT INTO cargosincronizacao (cdcargo, descricao) values (NEW.cdcargo, NEW.nome);
	ELSEIF(TG_OP = 'UPDATE') THEN
		UPDATE cargosincronizacao 
		SET descricao = NEW.nome, dtsincronizacao = NULL
		WHERE cdcargo = NEW.cdcargo;
	ELSEIF(TG_OP = 'DELETE') THEN
		primeirasincronizacao = (SELECT c.primeirasincronizacao FROM cargosincronizacao c WHERE c.cdcargo = OLD.cdcargo);
		IF(primeirasincronizacao IS NULL) THEN
			DELETE FROM cargosincronizacao WHERE cdcargo = OLD.cdcargo;
		ELSE
			UPDATE cargosincronizacao 
			SET deletado = current_timestamp
			WHERE cdcargo = OLD.cdcargo;
		END IF;
	END IF;
	RETURN NULL;		
    END;
  $BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION tg_cargo_ins_upd_del()
  OWNER TO postgres;
  
  
 

ALTER TABLE cargosincronizacao
   ADD COLUMN primeirasincronizacao timestamp without time zone; 
-----------------------------------------------------------------------------------------------------------------------------------------------------------------


CREATE OR REPLACE VIEW varvoreempresa AS 
 WITH RECURSIVE search_empresa(cdempresapai, nome, cdempresa, empresaprincipal, depth, path, cycle) AS (
                 SELECT emp.empresasubordinacao, p.nome, emp.cdpessoa, emp.cdpessoa, 1, 
                    ARRAY[ROW(emp.cdpessoa, p.nome)] AS "array", 
                    false AS bool
                   FROM empresa emp
              JOIN pessoa p ON p.cdpessoa = emp.cdpessoa
             WHERE emp.empresasubordinacao IS NULL
        UNION ALL 
                 SELECT emp.empresasubordinacao, p.nome, emp.cdpessoa, 
                    sg.cdempresapai, sg.depth + 1, 
                    sg.path || ROW(emp.cdpessoa, p.nome), 
                    ROW(emp.cdpessoa, p.nome) = ANY (sg.path)
                   FROM empresa emp
              JOIN pessoa p ON p.cdpessoa = emp.cdpessoa
         JOIN search_empresa sg ON sg.cdempresa = emp.empresasubordinacao
        WHERE NOT sg.cycle
        )
 SELECT search_empresa.cdempresapai, search_empresa.nome, 
    search_empresa.cdempresa, search_empresa.depth AS nivel, 
    search_empresa.empresaprincipal
   FROM search_empresa
  ORDER BY search_empresa.depth, upper(retira_acento(search_empresa.nome)::text);
  
---------------------------------------------AT -  #86154--------------------------------------------------------------------------------------------------
  
  CREATE OR REPLACE FUNCTION tg_centrocusto_upd()
  RETURNS trigger AS
$BODY$
DECLARE 
	existe integer;
BEGIN

	existe = (SELECT 1
	FROM centrocusto cc 
	JOIN empresacentrocusto ecc on ecc.cdcentrocusto=cc.cdcentrocusto
	WHERE upper(retira_acento(cc.nome)) = upper(retira_acento(NEW.nome))
	and cc.cdcentrocusto <> NEW.cdcentrocusto
	and ecc.cdempresa in (select cdempresa from empresacentrocusto where cdcentrocusto = NEW.cdcentrocusto));

	IF(EXISTE = 1) THEN
		RAISE EXCEPTION 'Já existe uma empresa que contém um centro de custo com esse nome: %',
                                                      New.nome;
	END IF;
	RETURN NEW;
END; $BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION tg_centrocusto_upd()
  OWNER TO postgres;

  ---AT---
  
  ALTER TABLE usuariolog ADD COLUMN cdcandidato integer;
  ALTER TABLE usuariolog
  ADD CONSTRAINT usuariolog_candidato_cdcandidato FOREIGN KEY (cdcandidato) REFERENCES candidato (cdpessoa)
   ON UPDATE NO ACTION ON DELETE NO ACTION;
   

-- Manter Unidade de Negócio ------------------------------
ALTER TABLE empresa DROP CONSTRAINT fk_empresa_0;
ALTER TABLE empresa DROP COLUMN cdempresacontrato;

CREATE UNIQUE INDEX idx_empresacargo
  ON empresacargo
  USING btree
  (cdcargo, cdempresa);
  
ALTER TABLE empresacentrocusto
ADD COLUMN replicar boolean;

ALTER TABLE empresaavaliacao
ADD COLUMN permiteUnidade boolean;

ALTER TABLE empresacentrocusto
DROP CONSTRAINT empresacentrocusto_cdempresa_fkey;
ALTER TABLE empresacentrocusto
ADD CONSTRAINT empresacentrocusto_cdempresa_fkey FOREIGN KEY (cdempresa)
      REFERENCES empresa (cdpessoa) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE CASCADE;
	
ALTER TABLE empresasetor
DROP CONSTRAINT fk_empresasetor_1;
ALTER TABLE empresasetor
ADD CONSTRAINT fk_empresasetor_1 FOREIGN KEY (cdsetor)
      REFERENCES setor (cdsetor) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION;
	  
ALTER TABLE empresausuario
ADD COLUMN naoPermiteAcessoSubordinadas boolean;

ALTER TABLE empresa
ADD CONSTRAINT fk_empresa_1 FOREIGN KEY (segmento)
      REFERENCES segmento (cdsegmento) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION;
	  
CREATE OR REPLACE FUNCTION tg_unidadeNegocio_ins_upd()
  RETURNS trigger AS
$BODY$
DECLARE 
	existe integer;
	nomePessoa varchar;
BEGIN
	nomePessoa = (select nome from pessoa where cdpessoa = NEW.cdpessoa);
	existe = (SELECT 1
	FROM empresa e
	JOIN pessoa p ON p.cdpessoa = e.cdpessoa
	WHERE upper(retira_acento(p.nome)) = upper(retira_acento(nomePessoa))
	and e.cdpessoa <> NEW.cdpessoa
	and e.unidade = true
	and e.empresasubordinacao = NEW.empresasubordinacao);

	IF(EXISTE = 1) THEN
		RAISE EXCEPTION 'Unidade de negócio já cadastrada(%).', nomePessoa;
                                                     
	END IF;
	RETURN NEW;
END; $BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
  
CREATE TRIGGER trg_unidadeNegocio_upd
  BEFORE UPDATE
  ON empresa
  FOR EACH ROW
  EXECUTE PROCEDURE tg_unidadeNegocio_ins_upd();
  
CREATE TRIGGER trg_unidadeNegocio_ins
  BEFORE INSERT
  ON empresa
  FOR EACH ROW
  EXECUTE PROCEDURE tg_unidadeNegocio_ins_upd();
-----------------------------------------------------------
 ALTER TABLE usuariolog ADD COLUMN outraacao character varying;
 ALTER TABLE usuariolog ADD COLUMN observacao text;
 
 
 ------------------------------------AT 86361----------------------------------------------------------------------
update candidato set cdestadocivil = null;
delete from estadocivil;

insert into estadocivil(cdestadocivil,nome,ativo) values(1,'Casado(a)',TRUE);
insert into estadocivil(cdestadocivil,nome,ativo) values(2,'Solteiro(a)',TRUE);
insert into estadocivil(cdestadocivil,nome,ativo) values(3,'Viúvo(a)',TRUE);
insert into estadocivil(cdestadocivil,nome,ativo) values(4,'Separado(a) judicialmente',TRUE);
insert into estadocivil(cdestadocivil,nome,ativo) values(5,'Divorciado(a)',TRUE);
insert into estadocivil(cdestadocivil,nome,ativo) values(6,'Desquitado(a)',TRUE);
insert into estadocivil(cdestadocivil,nome,ativo) values(7,'Amasiado(a)',TRUE);
insert into estadocivil(cdestadocivil,nome,ativo) values(8,'Outros',TRUE);
insert into estadocivil(cdestadocivil,nome,ativo) values(9,'União Estável',TRUE);


ALTER TABLE aplicacaoavaliacao DROP COLUMN classificacao;

ALTER TABLE aplicacaoavaliacao ADD COLUMN cdclassificacao integer;
ALTER TABLE aplicacaoavaliacao
  ADD CONSTRAINT aplicacaoavaliacao_cdclassificacao_fkey FOREIGN KEY (cdclassificacao)
      REFERENCES classificacao (cdclassificacao) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION;

CREATE SEQUENCE sq_candidatohistorico
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;
  
CREATE TABLE candidatohistorico
(
  cdcandidatohistorico integer NOT NULL,
  cdcandidato integer,
  cdusuarioaltera integer,
  dtaltera timestamp without time zone,
  acao integer,
  hora time without time zone,
  CONSTRAINT candidatohistorico_pkey PRIMARY KEY (cdcandidatohistorico),
  CONSTRAINT candidatohistorico_cdcandidato_fkey FOREIGN KEY (cdcandidato)
      REFERENCES candidato (cdpessoa) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT candidatohistorico_cdusuarioaltera_fkey FOREIGN KEY (cdusuarioaltera)
      REFERENCES usuario (cdpessoa) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);
ALTER TABLE empresacontrato
ADD COLUMN principal boolean;

ALTER TABLE empresaseguranca
ADD CONSTRAINT fk_empresaseguranca_0 FOREIGN KEY (cdempresa)
	REFERENCES empresa (cdpessoa) MATCH SIMPLE
	ON UPDATE NO ACTION ON DELETE NO ACTION;
CREATE SEQUENCE sq_empresaseguranca;

UPDATE enderecotipo
SET nome = 'Implantação'
WHERE nome = 'Instalação';

CREATE UNIQUE INDEX idx_contatotipo_descricao
  ON contatotipo
  USING btree
  (retira_acento(lower(descricao::text)::character varying) );

INSERT INTO contatotipo (cdcontatotipo, descricao)
VALUES	(nextval('sq_contatotipo'), 'Administrador'),
	(nextval('sq_contatotipo'), 'Advogado'),
	(nextval('sq_contatotipo'), 'Contador'),
	(nextval('sq_contatotipo'), 'Diretor'),
	(nextval('sq_contatotipo'), 'Gerente'),
	(nextval('sq_contatotipo'), 'Outros'),
	(nextval('sq_contatotipo'), 'Presidente'),
	(nextval('sq_contatotipo'), 'Psicólogo'),
	(nextval('sq_contatotipo'), 'Secretaria'),
	(nextval('sq_contatotipo'), 'Supervisor');

--------------------------------------------AT - #86540-------------------------------------
CREATE TABLE boletimmedicaostatus
(
  cdboletimmedicaostatus integer NOT NULL,
  nome character varying,
  CONSTRAINT boletimmedicaostatus_pkey PRIMARY KEY (cdboletimmedicaostatus)
);

CREATE SEQUENCE sq_boletimmedicaostatus
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;
ALTER TABLE sq_boletimmedicaostatus
  OWNER TO postgres;

CREATE TABLE boletimmedicao
(
  cdboletimmedicao integer NOT NULL,
  cdempresa integer,
  quantidade integer,
  cdboletimmedicaostatus integer,
  databoletim date,
  datainicio date,
  datafim date,
  CONSTRAINT boletimmedicao_pkey PRIMARY KEY (cdboletimmedicao),
  CONSTRAINT boletimmedicao_cdboletimmedicaostatus_fkey FOREIGN KEY (cdboletimmedicaostatus)
      REFERENCES boletimmedicaostatus (cdboletimmedicaostatus) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT boletimmedicao_cdempresa_fkey FOREIGN KEY (cdempresa)
      REFERENCES pessoa (cdpessoa) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);

CREATE INDEX fki_boletimmedicao_cdboletimmedicaostatus_fkey
  ON boletimmedicao
  USING btree
  (cdboletimmedicaostatus);
  
CREATE SEQUENCE sq_boletimmedicao
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;
  
CREATE TABLE boletimmedicaoavaliacao
(
  cdavaliacao integer,
  cdboletimmedicaoavaliacao integer NOT NULL,
  cdboletimmedicao integer,
  CONSTRAINT boletimmedicaoavaliacao_pkey PRIMARY KEY (cdboletimmedicaoavaliacao),
  CONSTRAINT boletimmedicaoavaliacao_cdavaliacao_fkey FOREIGN KEY (cdavaliacao)
      REFERENCES avaliacao (cdavaliacao) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT boletimmedicaoavaliacao_cdboletimmedicao_fkey FOREIGN KEY (cdboletimmedicao)
      REFERENCES boletimmedicao (cdboletimmedicao) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);

CREATE SEQUENCE sq_boletimmedicaoavaliacao
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 12
  CACHE 1;
ALTER TABLE sq_boletimmedicaoavaliacao
  OWNER TO postgres;

CREATE TABLE boletimmedicaocandidato
(
  cdboletimmedicaocandidato integer NOT NULL,
  cdboletimmedicao integer,
  cdcandidato integer,
  cdavaliacao integer,
  dataavaliacao date,
  CONSTRAINT boletimmedicaocandidato_pkey PRIMARY KEY (cdboletimmedicaocandidato),
  CONSTRAINT boletimmedicaocandidato_cdavaliacao_fkey FOREIGN KEY (cdavaliacao)
      REFERENCES avaliacao (cdavaliacao) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT boletimmedicaocandidato_cdboletimmedicao_fkey FOREIGN KEY (cdboletimmedicao)
      REFERENCES boletimmedicao (cdboletimmedicao) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT boletimmedicaocandidato_cdcandidato_fkey FOREIGN KEY (cdcandidato)
      REFERENCES candidato (cdpessoa) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);  
  
CREATE SEQUENCE sq_boletimmedicaocandidato
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 14
  CACHE 1;
ALTER TABLE sq_boletimmedicaocandidato
  OWNER TO postgres;
  
CREATE TABLE boletimmedicaocentrocusto
(
  cdboletimmedicao integer,
  cdcentrocusto integer,
  cdboletimmedicaocentrocusto integer NOT NULL,
  CONSTRAINT boletimmedicaocentrocusto_pkey PRIMARY KEY (cdboletimmedicaocentrocusto),
  CONSTRAINT boletimmedicaocentrocusto_cdboletimmedicao_fkey FOREIGN KEY (cdboletimmedicao)
      REFERENCES boletimmedicao (cdboletimmedicao) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT boletimmedicaocentrocusto_cdcentrocusto_fkey FOREIGN KEY (cdcentrocusto)
      REFERENCES centrocusto (cdcentrocusto) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);

CREATE SEQUENCE sq_boletimmedicaocentrocusto
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 17
  CACHE 1;
ALTER TABLE sq_boletimmedicaocentrocusto
  OWNER TO postgres;
  
CREATE TABLE boletimmedicaoempresa
(
  cdboletimmedicaoempresa integer NOT NULL,
  cdboletimmedicao integer,
  cdpessoa integer,
  CONSTRAINT boletimmedicaoempresa_pkey PRIMARY KEY (cdboletimmedicaoempresa),
  CONSTRAINT boletimmedicaoempresa_cdboletimmedicao_fkey FOREIGN KEY (cdboletimmedicao)
      REFERENCES boletimmedicao (cdboletimmedicao) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT boletimmedicaoempresa_cdpessoa_fkey FOREIGN KEY (cdpessoa)
      REFERENCES empresa (cdpessoa) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);
 
CREATE SEQUENCE sq_boletimmedicaoempresa
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 17
  CACHE 1;
ALTER TABLE sq_boletimmedicaoempresa
  OWNER TO postgres;

-----homologacao WS----
ALTER TABLE indicador  ADD COLUMN grupo_ integer;
UPDATE indicador set grupo_=grupo::integer;

ALTER TABLE indicador drop column grupo;

ALTER TABLE indicador RENAME grupo_  TO grupo;

create sequence sq_empresaavaliacao;

ALTER TABLE empresaavaliacao
	ALTER COLUMN cdavaliacao SET NOT NULL;
   
CREATE SEQUENCE sq_parametrogeral;
INSERT INTO tela (cdtela, descricao, path) VALUES(nextval('sq_tela'), 'Aplicar Avaliação', '/publico/process/AplicarAvaliacao');
INSERT INTO parametrogeral (cdparametrogeral, nome, valor) VALUES(nextval('sq_parametrogeral'), 'EMAIL_ADMINISTRADOR_SISTEMA', 'igor.costa@linkcom.com.br');

------------------------------------------------------------------------------------------------------------------------------------------------------------

---------------------------------------------------------------AT #86377------------------------------------------------------------------------------------

ALTER TABLE avaliacao
   ADD COLUMN folharesposta integer;

-----------------------------------------------------------------------------------------------------------------------------------------------------------

------------------------------------------------------------AT #88485---------------------------------------------------------------------------------------

ALTER TABLE empresarelatorioindicador DROP CONSTRAINT empresarelatorioindicador_cdempresarelatorio_fkey;
ALTER TABLE empresarelatorioindicador ADD FOREIGN KEY(cdempresarelatorio) REFERENCES empresarelatorio(cdempresarelatorio) ON DELETE CASCADE;

ALTER TABLE empresarelatoriosubmodulo DROP CONSTRAINT empresarelatoriosubmodulo_cdempresarelatorio_fkey;
ALTER TABLE empresarelatoriosubmodulo ADD FOREIGN KEY(cdempresarelatorio) REFERENCES empresarelatorio(cdempresarelatorio) ON DELETE CASCADE;


------------------------------------------------------------------------------------------------------------------------------------------------------------

----HOMOLOGACAO WS-----------------------
 update avaliacao set idmapa=cdavaliacao where idmapa is null;
 
 alter table aplicacaoavaliacaosincronizacao add column id integer;
 alter table aplicacaoavaliacaosincronizacao add column primeirasincronizacao date;
 
 ALTER TABLE aplicacaoavaliacaosincronizacao
  DROP CONSTRAINT aplicacaoavaliacaosincronizacao_cdaplicacaoavaliaco_fkey;
  INSERT INTO tela (cdtela, descricao, path) VALUES(nextval('sq_tela'), 'Homologacao WS', '/publico/HomologacaoWS');

------------------------------------------------------------------------------------------------------------------------------------------------------------
---------------------------------------------- Tarefa da At #92766 --------------------------------------------------------
create sequence sq_escala;
create sequence sq_dimensaoescala; 
CREATE TABLE escala
(
  cdescala integer NOT NULL,
  nome character varying,
  CONSTRAINT escala_pkey PRIMARY KEY (cdescala)
);


CREATE TABLE empresarelatoriodimensao
(
  cdempresarelatoriodimensao integer NOT NULL,
  cdempresarelatorio integer,
  cddimensao integer,
  CONSTRAINT empresarelatoriodimensao_pkey PRIMARY KEY (cdempresarelatoriodimensao),
  CONSTRAINT empresarelatoriodimensao_cddimensao_fkey FOREIGN KEY (cddimensao)
      REFERENCES dimensao (cddimensao) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT empresarelatoriodimensao_cdempresarelatorio_fkey FOREIGN KEY (cdempresarelatorio)
      REFERENCES empresarelatorio (cdempresarelatorio) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);

CREATE SEQUENCE sq_empresarelatoriodimensao
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;
ALTER TABLE sq_empresarelatoriodimensao
  OWNER TO postgres;
--------------------------------------------------------------------
ALTER TABLE submodulo ADD COLUMN tipo integer;
--------------------------------------------------------------------
CREATE TABLE dimensao
(
  cddimensao integer NOT NULL,
  descricao character varying,
  cdescala integer,
  CONSTRAINT dimensao_cddimensao_pkey PRIMARY KEY (cddimensao),
  CONSTRAINT dimensao_cdescala_fkey FOREIGN KEY (cdescala)
      REFERENCES escala (cdescala) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);

--------------------------------------------------------------------
CREATE TABLE dimensaoescala
(
  cddimensaoescala integer NOT NULL,
  cddimensao integer,
  cdescala integer,
  CONSTRAINT dimensaoescala_pkey PRIMARY KEY (cddimensaoescala),
  CONSTRAINT dimensaoescala_cddimensao_fkey FOREIGN KEY (cddimensao)
      REFERENCES dimensao (cddimensao) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE CASCADE,
  CONSTRAINT dimensaoescala_cdescala_fkey FOREIGN KEY (cdescala)
      REFERENCES escala (cdescala) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE CASCADE
);

ALTER TABLE dimensao ADD COLUMN idmapa integer not null;
ALTER TABLE escala ADD COLUMN idmapa integer not null;


----------------------------------------AT #88501---------------------------------------

ALTER TABLE empresarelatoriosubmodulo DROP CONSTRAINT empresarelatoriosubmodulo_cdsubmodulo_fkey;
ALTER TABLE empresarelatoriosubmodulo ADD FOREIGN KEY(cdsubmodulo) REFERENCES submodulo(cdsubmodulo) ON DELETE CASCADE;

ALTER TABLE empresarelatorioindicador DROP CONSTRAINT empresarelatorioindicador_cdindicador_fkey;
ALTER TABLE empresarelatorioindicador ADD FOREIGN KEY(cdindicador) REFERENCES indicador(cdindicador) ON DELETE CASCADE;

create sequence sq_parametrogeral;

-----------------------------------------------------------------------------------------
CREATE OR REPLACE VIEW varvoreempresa AS 
 WITH RECURSIVE search_empresa(cdempresapai, nome, cdempresa, empresaprincipal, depth, path, cycle) AS (
                 SELECT emp.empresasubordinacao, p.nome, emp.cdpessoa, 
                    emp.cdpessoa, 1, 
                    ARRAY[ROW(emp.cdpessoa, p.nome)] AS "array", false AS bool
                   FROM empresa emp
              JOIN pessoa p ON p.cdpessoa = emp.cdpessoa
             WHERE emp.empresasubordinacao IS NULL
        UNION ALL 
                 SELECT emp.empresasubordinacao, p.nome, emp.cdpessoa, 
                    coalesce(sg.empresaprincipal, sg.cdempresapai), sg.depth + 1, 
                    sg.path || ROW(emp.cdpessoa, p.nome), 
                    ROW(emp.cdpessoa, p.nome) = ANY (sg.path)
                   FROM empresa emp
              JOIN pessoa p ON p.cdpessoa = emp.cdpessoa
         JOIN search_empresa sg ON sg.cdempresa = emp.empresasubordinacao
        WHERE NOT sg.cycle
        )
 SELECT search_empresa.cdempresapai, search_empresa.nome, 
    search_empresa.cdempresa, search_empresa.depth AS nivel, 
    search_empresa.empresaprincipal
   FROM search_empresa
  ORDER BY search_empresa.depth, upper(retira_acento(search_empresa.nome)::text);
  
  ALTER TABLE empresasincronizacao ADD COLUMN primeirasincronizacao date;
  ALTER TABLE segmentosincronizacao ADD COLUMN primeirasincronizacao date;
  ALTER TABLE aplicacaoavaliacaosincronizacao ADD COLUMN cpf character varying(14);
  
  ALTER TABLE avaliacao ADD COLUMN folharesposta INTEGER;
  
----------------------------------------------------------------------------------------------------------------------------------------------------
---------------------------------------------------------AT #86297----------------------------------------------------------------------------------
  
  
ALTER TABLE papel
   ADD COLUMN perfilinterno boolean;
   
ALTER TABLE papel
   ADD COLUMN colaboradormapa boolean;
   
ALTER TABLE papel
   ALTER COLUMN colaboradormapa SET DEFAULT FALSE;
   
UPDATE papel SET colaboradormapa = FALSE;

ALTER TABLE usuario
   ADD COLUMN perfilacesso integer;

ALTER TABLE usuario
	ADD CONSTRAINT usuario_perfilacesso_fkey FOREIGN KEY (perfilacesso) REFERENCES papel (cdpapel)
		ON UPDATE NO ACTION ON DELETE NO ACTION;
CREATE INDEX fki_usuario_perfilacesso_fkey
  ON usuario(perfilacesso);
  
------------------------------------------------------------------------------------------------------------------------------------------------------
---------------------------------------------------------AT #86297------------------------------------------------------------------------------------

ALTER TABLE papel DROP COLUMN colaboradormapa;
ALTER TABLE usuario
   ADD COLUMN colaboradormapa boolean DEFAULT FALSE;
   
---------------------------------------------------------AT #86369---------------------------------------------------------
INSERT INTO acao (cdacao, descricao, key) VALUES(nextval('sq_acao'), 'Visualizar avaliações do candidato', 'VISUALIZAR_AVALIACAO_CANDIDATO');

----------------------------Homologação WS----------------------------------------------------------------------------------

update empresa set indiceconfiabilidade=true where indiceconfiabilidade is null;
ALTER TABLE empresa ALTER COLUMN indiceconfiabilidade SET NOT NULL;
update empresa set segmento=1 where segmento is null;
ALTER TABLE empresa ALTER COLUMN segmento SET NOT NULL;

alter table submodulo add column tipo_ int;
update submodulo set tipo_=tipo::int;
ALTER TABLE submodulo drop column tipo;
alter table submodulo rename column tipo_ to tipo;


CREATE OR REPLACE FUNCTION tg_segmento_ins_upd_del()
  RETURNS trigger AS
$BODY$
    BEGIN
	IF(TG_OP = 'INSERT') THEN
		INSERT INTO segmentosincronizacao (cdsegmento, descricao) values (NEW.cdsegmento, NEW.nome);
	ELSEIF(TG_OP = 'UPDATE') THEN
		UPDATE segmentosincronizacao 
		SET descricao = NEW.nome, dtsincronizacao = NULL
		WHERE cdsegmento = NEW.cdsegmento;
	ELSEIF(TG_OP = 'DELETE') THEN
		UPDATE segmentosincronizacao 
		SET deletado = current_timestamp, dtsincronizacao = null
		WHERE cdsegmento = OLD.cdsegmento;
	END IF;
	RETURN NULL;		
    END;
  $BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
  
  
CREATE OR REPLACE FUNCTION tg_cargo_ins_upd_del()
  RETURNS trigger AS
$BODY$
    DECLARE
	primeirasincronizacao timestamp without time zone;
    BEGIN
	IF(TG_OP = 'INSERT') THEN
		INSERT INTO cargosincronizacao (cdcargo, descricao) values (NEW.cdcargo, NEW.nome);
	ELSEIF(TG_OP = 'UPDATE') THEN
		UPDATE cargosincronizacao 
		SET descricao = NEW.nome, dtsincronizacao = NULL
		WHERE cdcargo = NEW.cdcargo;
	ELSEIF(TG_OP = 'DELETE') THEN
		primeirasincronizacao = (SELECT c.primeirasincronizacao FROM cargosincronizacao c WHERE c.cdcargo = OLD.cdcargo);
		IF(primeirasincronizacao IS NULL) THEN
			DELETE FROM cargosincronizacao WHERE cdcargo = OLD.cdcargo;
		ELSE
			UPDATE cargosincronizacao 
			SET deletado = current_timestamp, dtsincronizacao = null
			WHERE cdcargo = OLD.cdcargo;
		END IF;
	END IF;
	RETURN NULL;		
    END;
  $BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;

CREATE OR REPLACE FUNCTION tg_aplicacaoavaliacao_ins_upd_del()
  RETURNS trigger AS
$BODY$
     DECLARE
	idempresa integer;
	candidato character varying; 
	idestadocivil integer;
	sexo character varying;
	dtnascimento character varying;
	idescolaridade integer;
	idcargo integer;
	idteste integer; 
	dtaplicacao character varying; 
	cpf character varying;
	
    BEGIN
	IF(TG_OP != 'DELETE')THEN
		idempresa = (SELECT cdempresa FROM candidatoempresa ce
				WHERE ce.cdcandidatoempresa = NEW.cdcandidatoempresa);
				
		candidato = (SELECT p.nome FROM candidatoempresa ce 
				LEFT OUTER JOIN candidato c ON ce.cdcandidato = c.cdpessoa 
				LEFT OUTER JOIN pessoa p ON c.cdpessoa = p.cdpessoa 
				WHERE ce.cdcandidatoempresa = NEW.cdcandidatoempresa);

		cpf = 	(SELECT p.cpf FROM candidatoempresa ce 
				LEFT OUTER JOIN candidato c ON ce.cdcandidato = c.cdpessoa 
				LEFT OUTER JOIN pessoa p ON c.cdpessoa = p.cdpessoa 
				WHERE ce.cdcandidatoempresa = NEW.cdcandidatoempresa);
				
		idestadocivil = (SELECT ec.cdestadocivil FROM candidatoempresa ce
				LEFT OUTER JOIN candidato c ON ce.cdcandidato = c.cdpessoa
				LEFT OUTER JOIN estadocivil ec ON ec.cdestadocivil = c.cdestadocivil
				WHERE ce.cdcandidatoempresa = NEW.cdcandidatoempresa);

		sexo = (SELECT s.nome FROM candidatoempresa ce
			LEFT OUTER JOIN candidato c ON c.cdpessoa = ce.cdcandidato
			LEFT OUTER JOIN sexo s ON s.cdsexo = c.cdsexo
			WHERE ce.cdcandidatoempresa = NEW.cdcandidatoempresa);

		dtnascimento = to_char((SELECT c.dtnascimento FROM candidatoempresa ce
				LEFT OUTER JOIN candidato c ON ce.cdcandidato = c.cdpessoa
				WHERE ce.cdcandidatoempresa = NEW.cdcandidatoempresa), 'dd/MM/yyyy');

		idescolaridade = (SELECT c.cdescolaridade FROM candidatoempresa ce
				LEFT OUTER JOIN candidato c ON ce.cdcandidato = c.cdpessoa
				WHERE ce.cdcandidatoempresa = NEW.cdcandidatoempresa);

		idcargo = NEW.cdcargo;

		dtaplicacao = to_char((coalesce(NEW.dataaplicacao, current_date)), 'dd/MM/yyyy');

		idteste = (SELECT idmapa FROM avaliacao WHERE cdavaliacao = NEW.cdavaliacao);
	END IF;
			
	IF(TG_OP = 'INSERT') THEN
		INSERT INTO aplicacaoavaliacaosincronizacao (cdaplicacaoavaliacao, idempresa, candidato, idestadocivil, sexo, datanascimento, idescolaridade, idcargo, dataaplicacao, cpf, idteste, cdavaliacaostatus) 
		values (NEW.cdaplicacaoavaliacao, idempresa, candidato, idestadocivil, sexo, dtnascimento, idescolaridade, idcargo, dtaplicacao, cpf, idteste, NEW.cdavaliacaostatus);
		RETURN NEW;
	ELSEIF(TG_OP = 'UPDATE') THEN
		UPDATE aplicacaoavaliacaosincronizacao
		SET dataaplicacao = dtaplicacao, datasincronizacao = NULL
		WHERE cdaplicacaoavaliacao = NEW.cdaplicacaoavaliacao;
		RETURN NEW;
	ELSEIF(TG_OP = 'DELETE') THEN
		UPDATE aplicacaoavaliacaosincronizacao
		SET deletado = current_timestamp, datasincronizacao = NULL
		WHERE cdaplicacaoavaliacao = OLD.cdaplicacaoavaliacao;
		RETURN OLD;
	END IF;
    END;
  $BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;

  
ALTER FUNCTION tg_aplicacaoavaliacao_ins_upd_del()
  OWNER TO postgres;

update segmentosincronizacao set dtsincronizacao = null where deletado is not null;
update cargosincronizacao set dtsincronizacao = null where deletado is not null;
update cargosincronizacao set dtsincronizacao = null where deletado is not null;

ALTER TABLE aplicacaoavaliacaosincronizacao alter column idempresa SET NOT NULL;
  ALTER TABLE aplicacaoavaliacaosincronizacao alter column candidato SET NOT NULL;
  ALTER TABLE aplicacaoavaliacaosincronizacao drop column estadocivil;
  ALTER TABLE aplicacaoavaliacaosincronizacao add column idestadocivil integer NOT NULL;
  ALTER TABLE aplicacaoavaliacaosincronizacao alter column sexo SET NOT NULL;
  ALTER TABLE aplicacaoavaliacaosincronizacao alter column datanascimento SET NOT NULL;
  ALTER TABLE aplicacaoavaliacaosincronizacao drop column escolaridade;
  ALTER TABLE aplicacaoavaliacaosincronizacao add column idescolaridade integer NOT NULL;
  ALTER TABLE aplicacaoavaliacaosincronizacao add column idcargo integer NOT NULL;
  ALTER TABLE aplicacaoavaliacaosincronizacao drop column cargo;
  ALTER TABLE aplicacaoavaliacaosincronizacao alter column dataaplicacao SET NOT NULL;
  ALTER TABLE aplicacaoavaliacaosincronizacao alter column cpf SET NOT NULL;
  ALTER TABLE aplicacaoavaliacaosincronizacao alter column idteste SET NOT NULL;
  
  ALTER TABLE candidato alter column cdestadocivil SET NOT NULL;
  ALTER TABLE candidato alter column cdescolaridade SET NOT NULL;
  ALTER TABLE aplicacaoavaliacao alter column cdcargo SET NOT NULL;
  
ALTER TABLE empresarelatorio ADD COLUMN idmapa integer;

ALTER TABLE aplicacaoavaliacaosincronizacao ADD column idteste integer not null;


create sequence sq_relatorio;
-------------------------WS W3--------------------------------
ALTER TABLE boletimmedicao ADD COLUMN dtsincronizacao date;
ALTER TABLE boletimmedicao ADD COLUMN cdpedidovendaw3 int;
ALTER TABLE boletimmedicao ADD COLUMN mensagemerro text;
ALTER TABLE boletimmedicaoavaliacao ADD COLUMN quantidade int not null;


INSERT INTO boletimmedicaostatus (cdboletimmedicaostatus, nome) VALUES(nextval('sq_boletimmedicaostatus'), 'Enviado');
INSERT INTO boletimmedicaostatus (cdboletimmedicaostatus, nome) VALUES(nextval('sq_boletimmedicaostatus'), 'Não Enviado');
INSERT INTO boletimmedicaostatus (cdboletimmedicaostatus, nome) VALUES(nextval('sq_boletimmedicaostatus'), 'Cancelado');

-----------------------------------------TAREFA 81--------------------------------------------------------
ALTER TABLE empresacontrato ADD COLUMN faturarwebservice boolean;

ALTER TABLE empresacontrato ADD COLUMN representante integer;

ALTER TABLE empresacontrato
  ADD CONSTRAINT empresacontrato_representante_fkey FOREIGN KEY (representante)
      REFERENCES empresa (cdpessoa) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION;

ALTER TABLE empresa ALTER COLUMN segmento DROP NOT NULL;

ALTER TABLE empresa ALTER COLUMN indiceconfiabilidade DROP NOT NULL;

ALTER TABLE empresa ALTER COLUMN unidade DROP NOT NULL;

ALTER TABLE pessoa ALTER COLUMN tipopessoa DROP NOT NULL;


ALTER TABLE empresacontrato
  ADD CONSTRAINT empresacontrato_cdempresa_fkey FOREIGN KEY (cdempresa)
      REFERENCES empresa (cdpessoa) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION;
      
 ALTER TABLE aplicacaoavaliacaosincronizacao ADD COLUMN cdavaliacaostatus integer; 
 
INSERT INTO parametrogeral (cdparametrogeral, nome, valor) VALUES(nextval('sq_parametrogeral'), 'REMETENTE_PADRAO', 'noreply@mapaavaliacoes.com.br');

ALTER TABLE aplicacaoavaliacao DROP COLUMN tempogasto;
 ALTER TABLE aplicacaoavaliacao ADD COLUMN tempogasto character varying;
INSERT INTO acao (cdacao, descricao, key) VALUES(nextval('sq_acao'), 'Vincular usuário cadastrado em outra empresa/unidade a empresa/unidade', 'VINCULAR_USUARIO_EXTERNO');

CREATE UNIQUE INDEX idx_empresausuario
  ON empresausuario
  USING btree
  (cdempresa, cdusuario, cdpapel);
  
  
  ALTER TABLE empresa ALTER COLUMN segmento DROP NOT NULL;
  ALTER TABLE public.empresa
 ALTER COLUMN indiceconfiabilidade DROP NOT NULL;
 

INSERT INTO parametrogeral (cdparametrogeral, nome, valor) VALUES(nextval('sq_parametrogeral'), 'SEGMENTO_OUTROS', '55');

----------------------------------------------
update tela set path = '/sistema/crud/Email' where path = '/sistema/process/MailingEnviados';

-------------------tela wslog-----------------------


create sequence sq_wslog;
CREATE TABLE wslog
(
  cdwslog integer NOT NULL,
  json text,
  dtrequisicao timestamp without time zone,
  url character varying,
  tipo integer,
  metodo character varying,
  CONSTRAINT wslog_cdwslog_pkey PRIMARY KEY (cdwslog)
)
WITH (
  OIDS=FALSE
);


----------------correçoes trigger com Marden-----------------------------------------

drop trigger if exists trg_candidato_upd ON candidato;



CREATE OR REPLACE FUNCTION tg_aplicacaoavaliacao_ins_upd_del()
  RETURNS trigger AS
$BODY$
     DECLARE
	idempresa integer;
	candidato character varying; 
	idestadocivil integer;
	sexo character varying;
	dtnascimento character varying;
	idescolaridade integer;
	idcargo integer;
	idteste integer; 
	dtaplicacao character varying; 
	cpf character varying;

    BEGIN
	IF(TG_OP != 'DELETE')THEN
		idempresa = (SELECT cdempresa FROM candidatoempresa ce 
				WHERE ce.cdcandidatoempresa = NEW.cdcandidatoempresa);
				
		candidato = (SELECT p.nome FROM candidatoempresa ce 
				LEFT OUTER JOIN candidato c ON ce.cdcandidato = c.cdpessoa 
				LEFT OUTER JOIN pessoa p ON c.cdpessoa = p.cdpessoa 
				WHERE ce.cdcandidatoempresa = NEW.cdcandidatoempresa);

		cpf = 	(SELECT p.cpf FROM candidatoempresa ce 
				LEFT OUTER JOIN candidato c ON ce.cdcandidato = c.cdpessoa 
				LEFT OUTER JOIN pessoa p ON c.cdpessoa = p.cdpessoa 
				WHERE ce.cdcandidatoempresa = NEW.cdcandidatoempresa);
				
		idestadocivil = (SELECT ec.cdestadocivil FROM candidatoempresa ce
				LEFT OUTER JOIN candidato c ON ce.cdcandidato = c.cdpessoa
				LEFT OUTER JOIN estadocivil ec ON ec.cdestadocivil = c.cdestadocivil
				WHERE ce.cdcandidatoempresa = NEW.cdcandidatoempresa);

		sexo = (SELECT s.nome FROM candidatoempresa ce
			LEFT OUTER JOIN candidato c ON c.cdpessoa = ce.cdcandidato
			LEFT OUTER JOIN sexo s ON s.cdsexo = c.cdsexo
			WHERE ce.cdcandidatoempresa = NEW.cdcandidatoempresa);

		dtnascimento = to_char((SELECT c.dtnascimento FROM candidatoempresa ce
				LEFT OUTER JOIN candidato c ON ce.cdcandidato = c.cdpessoa
				WHERE ce.cdcandidatoempresa = NEW.cdcandidatoempresa), 'dd/MM/yyyy');

		idescolaridade = (SELECT c.cdescolaridade FROM candidatoempresa ce
				LEFT OUTER JOIN candidato c ON ce.cdcandidato = c.cdpessoa
				WHERE ce.cdcandidatoempresa = NEW.cdcandidatoempresa);

		idcargo = NEW.cdcargo;

		dtaplicacao = to_char((coalesce(NEW.dataaplicacao, current_date)), 'dd/MM/yyyy');

		idteste = (SELECT idmapa FROM avaliacao WHERE cdavaliacao = NEW.cdavaliacao);
	END IF;
			
	IF(TG_OP = 'INSERT') THEN
		INSERT INTO aplicacaoavaliacaosincronizacao (cdaplicacaoavaliacao, idempresa, candidato, idestadocivil, sexo, datanascimento, idescolaridade, idcargo, dataaplicacao, cpf, idteste, cdavaliacaostatus) 
		values (NEW.cdaplicacaoavaliacao, idempresa, candidato, idestadocivil, sexo, dtnascimento, idescolaridade, idcargo, dtaplicacao, cpf, idteste, NEW.cdavaliacaostatus);
		RETURN NEW;
	ELSEIF(TG_OP = 'UPDATE') THEN
		UPDATE aplicacaoavaliacaosincronizacao
		SET dataaplicacao = dtaplicacao, datasincronizacao = NULL
		WHERE cdaplicacaoavaliacao = NEW.cdaplicacaoavaliacao;
		RETURN NEW;
	ELSEIF(TG_OP = 'DELETE') THEN
		UPDATE aplicacaoavaliacaosincronizacao
		SET deletado = current_timestamp, datasincronizacao = NULL
		WHERE cdaplicacaoavaliacao = OLD.cdaplicacaoavaliacao;
		RETURN OLD;
	END IF;
    END;
  $BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION tg_aplicacaoavaliacao_ins_upd_del()
  OWNER TO postgres;

-------------------------------------------Remoção da trigger da tabela pessoa-----------------------------------------------------

DROP TRIGGER trg_pessoa_upd ON public.pessoa;
DROP FUNCTION public.tg_pessoa_upd();

-------------------------------------------Remoção do index de email único---------------------------------------------------------

DROP INDEX idx_pessoa_email;

-------alteraçoes na tabela de indicador
  
ALTER TABLE indicador ADD COLUMN ordem integer;
  
ALTER TABLE indicador ADD COLUMN balanco_ integer;
UPDATE indicador SET balanco_=0 WHERE balanco='positive';

UPDATE indicador SET balanco_=1 WHERE balanco='negative';
ALTER TABLE indicador DROP COLUMN balanco;
ALTER TABLE indicador RENAME COLUMN balanco_ TO balanco;

--------------------#86313----------------------------
ALTER TABLE email ALTER COLUMN dtenvio TYPE timestamp with time zone;
  
------------------------------------AT #86385---------------------------------

INSERT INTO avaliacaostatus VALUES(6, 'Expirado');

ALTER TABLE envioavaliacao
   ADD COLUMN id integer;
ALTER TABLE envioavaliacao
   ADD COLUMN cpf character varying(15);
ALTER TABLE envioavaliacao
   ADD COLUMN cdcandidato integer;
ALTER TABLE envioavaliacao
   ADD COLUMN datacadastro timestamp without time zone;
ALTER TABLE envioavaliacao
   ADD COLUMN avaliacaocandidato integer;


ALTER TABLE envioavaliacao
  ADD CONSTRAINT envioavaliacao_cpf_fkey FOREIGN KEY (cpf) REFERENCES pessoa (cpf)
   ON UPDATE NO ACTION ON DELETE NO ACTION;
CREATE INDEX fki_envioavaliacao_cpf_fkey
  ON envioavaliacao(cpf);
ALTER TABLE envioavaliacao
  ADD CONSTRAINT envioavaliacao_cdcandidato_fkey FOREIGN KEY (cdcandidato) REFERENCES candidato (cdpessoa)
   ON UPDATE NO ACTION ON DELETE NO ACTION;
CREATE INDEX fki_envioavaliacao_cdcandidato_fkey
  ON envioavaliacao(cdcandidato);
ALTER TABLE envioavaliacao
  ADD CONSTRAINT envioavaliacao_avaliacaocandidato_fkey FOREIGN KEY (avaliacaocandidato)
      REFERENCES avaliacao (cdavaliacao) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION;
  
----------------------------Modificações de regras da AT #88450----------  
  
ALTER TABLE aplicacaoavaliacao ALTER COLUMN percentualcompleto TYPE numeric(5,2); 
ALTER TABLE aplicacaoavaliacao ALTER COLUMN primeiroacesso TYPE timestamp without time zone;
ALTER TABLE aplicacaoavaliacao ALTER COLUMN dataaplicacao TYPE timestamp without time zone;
ALTER TABLE aplicacaoavaliacao ALTER COLUMN ultimoacesso TYPE timestamp without time zone; 
ALTER TABLE aplicacaoavaliacao ALTER COLUMN dataconclusao TYPE timestamp without time zone; 


------------------------------------------AT #86409-------------------------------------


ALTER TABLE questao DROP COLUMN descricao;
  
------------------------------------------------------------------------------------------

-----------------------------------------AT #86385----------------------------------------

INSERT INTO parametrogeral VALUES(nextval('sq_parametrogeral'), 'PRAZO_VALIDADE_CONVITE');

ALTER TABLE envioavaliacao
   ADD COLUMN cdusuariologado integer;
ALTER TABLE envioavaliacao
  ADD CONSTRAINT envio_avaliacao_cdusuariologado_fkey FOREIGN KEY (cdusuariologado) REFERENCES usuario (cdpessoa)
   ON UPDATE NO ACTION ON DELETE NO ACTION;
CREATE INDEX fki_envio_avaliacao_cdusuariologado_fkey
  ON envioavaliacao(cdusuariologado);
  
--------------------------------------------------------------------------------------------
----------------------------------------AT #86385-------------------------------------------

UPDATE envioavaliacao SET avaliacaocandidato = NULL;
ALTER TABLE envioavaliacao DROP CONSTRAINT envioavaliacao_avaliacaocandidato_fkey;
ALTER TABLE envioavaliacao
  ADD CONSTRAINT envioavaliacao_avaliacaocandidato_fkey FOREIGN KEY (avaliacaocandidato) REFERENCES aplicacaoavaliacao (cdaplicacaoavaliacao)
   ON UPDATE NO ACTION ON DELETE NO ACTION;
   
ALTER TABLE candidatoempresa
  ADD CONSTRAINT candidatoempresa_candidato_empresa_unq UNIQUE(cdcandidato, cdempresa);
  
--------------------------------------------------------------------------------------------

--------------------------------------AT #86377-----------------------------------------------

ALTER TABLE avaliacao ADD COLUMN numeroacessos integer;

---------------------------------------------------------------------------------------------

--------------------------------------AT #86385----------------------------------------------

ALTER TABLE envioavaliacao
   ALTER COLUMN datacadastro TYPE date;
   
---------------------------------------------------------------------------------------------

ALTER TABLE empresarelatorio ADD COLUMN adicionarperfilgrupo boolean;

------atualizando trigger de empresa para que não seja possivel uma empresa ser subordinada a ela mesma
CREATE OR REPLACE FUNCTION public.tg_empresa_ins ()
RETURNS trigger AS
$BODY$
    DECLARE
    Vcnpj character varying;
    Vemail character varying;
    Vnome character varying;
    BEGIN
    IF(NEW.cdpessoa IS NOT NULL AND NEW.empresasubordinacao IS NOT NULL AND NEW.cdpessoa = NEW.empresasubordinacao) THEN
        RAISE EXCEPTION 'Uma empresa não pode ser subordinada a ela mesma (cdpessoa=%).', NEW.cdpessoa ;
    END IF;
    IF(NEW.empresasubordinacao is null) THEN
        Vcnpj = (SELECT pessoa.cnpj FROM pessoa WHERE cdpessoa = NEW.cdpessoa);
        Vemail = (SELECT pessoa.email FROM pessoa WHERE cdpessoa = NEW.cdpessoa); 
        Vnome = (SELECT pessoa.nome FROM pessoa WHERE cdpessoa = NEW.cdpessoa); 
        IF(TG_OP = 'INSERT') THEN
            INSERT INTO empresasincronizacao (cdpessoa, nome, cnpj, email, indiceconfiabilidade, idsegmento) 
            values (NEW.cdpessoa, Vnome, Vcnpj, Vemail, NEW.indiceconfiabilidade, NEW.segmento);
        ELSEIF(TG_OP = 'UPDATE') THEN
            UPDATE empresasincronizacao 
            SET indiceconfiabilidade = NEW.indiceconfiabilidade, idsegmento = NEW.segmento, dtsincronizacao = NULL
            WHERE cdpessoa = NEW.cdpessoa;
        END IF;
    END IF;
    RETURN NULL;        
    END;
$BODY$
LANGUAGE 'plpgsql' VOLATILE
CALLED ON NULL INPUT
SECURITY INVOKER;

CREATE SEQUENCE sq_modeloemail;

CREATE TABLE public.modeloemail (
  cdmodeloemail INTEGER NOT NULL, 
  cc TEXT NOT NULL, 
  nome VARCHAR(300) NOT NULL UNIQUE, 
  assunto VARCHAR(300) NOT NULL, 
  corpo TEXT NOT NULL, 
  usuario INTEGER, 
  PRIMARY KEY(cdmodeloemail)
) WITHOUT OIDS;

ALTER TABLE public.avaliacaoindicador
  ADD COLUMN cdsegmento INTEGER;

ALTER TABLE public.avaliacaoindicador
  ADD CONSTRAINT avaliacaoindicador_cdsegmento_fkey FOREIGN KEY (cdsegmento)
    REFERENCES public.segmento(cdsegmento)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION
    NOT DEFERRABLE;

ALTER TABLE public.candidato
  ADD COLUMN idmapa INTEGER;
  
-------------------------------------------------AT #86122 ----------------------------------------------------
ALTER TABLE usuario
  ADD CONSTRAINT usuario_cdpessoa_fkey FOREIGN KEY (cdpessoa) REFERENCES pessoa (cdpessoa)
   ON UPDATE NO ACTION ON DELETE NO ACTION;


------------------------ Autorização de trabalho #101642 ------------------------------------------
ALTER TABLE submodulo DROP COLUMN cdempresarelatorio;
ALTER TABLE avaliacao DROP COLUMN folharesposta;
ALTER TABLE indicador DROP COLUMN cdempresarelatorio;
ALTER TABLE candidato DROP COLUMN casado;
ALTER TABLE candidato ALTER COLUMN tempodesempregado TYPE CHARACTER VARYING;
ALTER TABLE escala ADD COLUMN nome2 character varying;
ALTER TABLE escala ADD COLUMN abreviatura character varying;
ALTER TABLE escala ADD COLUMN cor character varying;
ALTER TABLE escala ADD COLUMN grupo integer;
ALTER TABLE escala ADD COLUMN ordem integer;
ALTER TABLE escala ADD COLUMN balanco character varying;
ALTER TABLE escala ADD COLUMN leitura integer;
ALTER TABLE escala ADD COLUMN correcao integer;

CREATE OR REPLACE FUNCTION tg_candidato_idmapa_upd()
	RETURNS trigger AS
$BODY$
	BEGIN
		IF(NEW.idmapa IS NULL) THEN
			IF(OLD.idmapa IS NOT NULL) THEN
				NEW.idmapa = OLD.idmapa;
			 END IF;
		END IF;
	RETURN NEW;
	END;
$BODY$
LANGUAGE plpgsql VOLATILE
  COST 100;

DROP TRIGGER IF EXISTS trg_candidato_idmapa_upd ON candidato;
CREATE TRIGGER trg_candidato_idmapa_upd
  BEFORE UPDATE
  ON candidato
  FOR EACH ROW
  EXECUTE PROCEDURE tg_candidato_idmapa_upd();
---------------------------------------------------------------------------------------------------