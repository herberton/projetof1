--------------------------------------------------
-- Export file for user HR                      --
-- Created by RicardoMMO on 8/11/2010, 20:38:40 --
--------------------------------------------------

spool DUMP_BANCO_INTEIRO_08112010.log

prompt
prompt Creating table TABELA_AUXILIAR
prompt ==============================
prompt
create table TABELA_AUXILIAR
(
  ID_TAB_AUX   NUMBER(3) not null,
  NOME_TAB_AUX VARCHAR2(50) not null,
  DESCRICAO    VARCHAR2(250)
)
;
alter table TABELA_AUXILIAR
  add constraint TABELA_AUXILIAR_PK primary key (ID_TAB_AUX);
alter table TABELA_AUXILIAR
  add constraint TABELA_AUXILIAR_NOME_UK unique (NOME_TAB_AUX);

prompt
prompt Creating table VALOR_TAB_AUX
prompt ============================
prompt
create table VALOR_TAB_AUX
(
  ID_VAL_TAB_AUX NUMBER(9) not null,
  ID_TAB_AUX     NUMBER(3) not null,
  NOME           VARCHAR2(50) not null,
  OBSERVACAO     VARCHAR2(1000),
  ABREV          VARCHAR2(25)
)
;
alter table VALOR_TAB_AUX
  add constraint VALORES_TAB_AUX_PK primary key (ID_VAL_TAB_AUX);
alter table VALOR_TAB_AUX
  add constraint VALOR_TAB_AUX_ABREV_UK unique (ABREV);
alter table VALOR_TAB_AUX
  add constraint VAL_TAB_AUX_TAB_FK foreign key (ID_TAB_AUX)
  references TABELA_AUXILIAR (ID_TAB_AUX);
create index VALORES_TAB_AUX_I on VALOR_TAB_AUX (ID_VAL_TAB_AUX, ID_TAB_AUX);
create index VAL_TAB_AUX_TAB_FK_I on VALOR_TAB_AUX (ID_TAB_AUX);

prompt
prompt Creating table CIDADE
prompt =====================
prompt
create table CIDADE
(
  ID_CIDADE     NUMBER(5) not null,
  NOME_CIDADE   VARCHAR2(50) not null,
  ID_UF         NUMBER(9) not null,
  ABREV         VARCHAR2(50),
  ID_TAB_AUX_UF NUMBER(3) not null
)
;
alter table CIDADE
  add constraint CIDADE_PK primary key (ID_CIDADE);
alter table CIDADE
  add constraint NOME_UF_UK unique (NOME_CIDADE, ID_UF);
alter table CIDADE
  add constraint TAB_AUX_CIDADE_FK foreign key (ID_TAB_AUX_UF)
  references TABELA_AUXILIAR (ID_TAB_AUX) on delete cascade;
alter table CIDADE
  add constraint UF_CID_VAL_TAB_AUX_FK foreign key (ID_UF)
  references VALOR_TAB_AUX (ID_VAL_TAB_AUX);
create index TAB_AUX_CIDADE_FK_I on CIDADE (ID_TAB_AUX_UF);
create index UF_CID_VAL_TAB_AUX_FK_I on CIDADE (ID_UF);

prompt
prompt Creating table BAIRRO
prompt =====================
prompt
create table BAIRRO
(
  ID_BAIRRO NUMBER(5) not null,
  NOME      VARCHAR2(50) not null,
  ABREV     VARCHAR2(25),
  ID_CIDADE NUMBER(5) not null
)
;
alter table BAIRRO
  add constraint BAIRRO_PK primary key (ID_BAIRRO);
alter table BAIRRO
  add constraint NOME_CIDADE_UK unique (NOME, ID_CIDADE);
alter table BAIRRO
  add constraint CIDADE_BAIRRO_FK foreign key (ID_CIDADE)
  references CIDADE (ID_CIDADE);
create index CIDADE_BAIRRO_FK_I on BAIRRO (ID_CIDADE);

prompt
prompt Creating table STATUS_BRINQUEDO
prompt ===============================
prompt
create table STATUS_BRINQUEDO
(
  ID_STATUS_BRINQUEDO NUMBER(3) not null,
  DESCRICAO           VARCHAR2(100) not null
)
;
alter table STATUS_BRINQUEDO
  add constraint PK_STATUSBRINQUEDO primary key (ID_STATUS_BRINQUEDO);
create unique index PK_STATUSBRINQUEDOX on STATUS_BRINQUEDO (ID_STATUS_BRINQUEDO);

prompt
prompt Creating table BRINQUEDO
prompt ========================
prompt
create table BRINQUEDO
(
  ID_BRINQUEDO                NUMBER(4) not null,
  NOME                        VARCHAR2(50) not null,
  OBSERVACAO                  VARCHAR2(100),
  TEMPO_MENSAGEM              INTEGER,
  ESTIMATIVA_TEMPO_FILA       INTEGER,
  QTD_PESSOAS_FILA_FISICA     INTEGER,
  QTD_MAX_PESSOAS_FILA_FISICA INTEGER,
  ID_STATUS_BRINQUEDO         NUMBER(3),
  TEMPO_EXECUCAO              INTEGER,
  QTD_PESSOAS_LEVA            INTEGER
)
;
alter table BRINQUEDO
  add constraint PK_BRINQUEDO primary key (ID_BRINQUEDO);
alter table BRINQUEDO
  add constraint FK_STATUSBRINQUEDO_BRINQUEDO foreign key (ID_STATUS_BRINQUEDO)
  references STATUS_BRINQUEDO (ID_STATUS_BRINQUEDO) on delete set null;
create index FK_STATUSBRINQUEDO_BRINQUEDOX on BRINQUEDO (ID_STATUS_BRINQUEDO);
create unique index PK_BRINQUEDOX on BRINQUEDO (ID_BRINQUEDO);

prompt
prompt Creating table CATRACA
prompt ======================
prompt
create table CATRACA
(
  ID_CATRACA   NUMBER(4) not null,
  DESCRICAO    VARCHAR2(100) not null,
  LOCALIZACAO  VARCHAR2(150),
  ID_BRINQUEDO NUMBER(4)
)
;
alter table CATRACA
  add constraint PK_CATRACA primary key (ID_CATRACA);
alter table CATRACA
  add constraint FK_BRINQUEDO_CATRACA foreign key (ID_BRINQUEDO)
  references BRINQUEDO (ID_BRINQUEDO) on delete set null;
create index FK_BRINQUEDO_CATRACAX on CATRACA (ID_BRINQUEDO);
create unique index PK_CATRACAX on CATRACA (ID_CATRACA);

prompt
prompt Creating table STATUS_DISPOSITIVO
prompt =================================
prompt
create table STATUS_DISPOSITIVO
(
  ID_STATUS_DISPOSITIVO NUMBER(3) not null,
  DESCRICAO             VARCHAR2(100) not null
)
;
alter table STATUS_DISPOSITIVO
  add constraint PK_STATUSDISPO primary key (ID_STATUS_DISPOSITIVO);
create unique index PK_STATUSDISPOX on STATUS_DISPOSITIVO (ID_STATUS_DISPOSITIVO);

prompt
prompt Creating table CLIENTE
prompt ======================
prompt
create table CLIENTE
(
  ID_CLIENTE      NUMBER(7) not null,
  NOME            VARCHAR2(50) not null,
  DATA_NASCIMENTO DATE,
  SEXO            CHAR(1) not null,
  CPF             VARCHAR2(11),
  RG              VARCHAR2(9),
  ALTURA          FLOAT,
  QTD_VISITAS     INTEGER,
  ID_DISPOSITIVO  NUMBER,
  CELULAR         NUMBER(8)
)
;
alter table CLIENTE
  add constraint PK_CLIENTE primary key (ID_CLIENTE);
alter table CLIENTE
  add constraint UK_CLIENTE_DISPOSITIVO unique (ID_DISPOSITIVO);
alter table CLIENTE
  add constraint PK_CLIENTE_DISPOSITIVO foreign key (ID_DISPOSITIVO)
  references DISPOSITIVO (ID_DISPOSITIVO);
alter table CLIENTE
  add check ( SEXO IN ('F' , 'M' ));
create unique index PK_CLIENTEX on CLIENTE (ID_CLIENTE);

prompt
prompt Creating table DISPOSITIVO
prompt ==========================
prompt
create table DISPOSITIVO
(
  ID_DISPOSITIVO        NUMBER(4) not null,
  ID_STATUS_DISPOSITIVO NUMBER(3),
  IP                    VARCHAR2(15),
  ID_CLIENTE            NUMBER(7),
  DATA_CADASTRO         DATE not null,
  CELULAR               NUMBER(8),
  ID_RFID               VARCHAR2(15)
)
;
alter table DISPOSITIVO
  add constraint PK_DISPOSITIVO primary key (ID_DISPOSITIVO);
alter table DISPOSITIVO
  add constraint UK_CLIENTE unique (ID_CLIENTE);
alter table DISPOSITIVO
  add constraint UK_COD_RFID unique (ID_RFID);
alter table DISPOSITIVO
  add constraint FK_DISPOSITIVO_CLIENTE foreign key (ID_CLIENTE)
  references CLIENTE (ID_CLIENTE);
alter table DISPOSITIVO
  add constraint FK_DISPOSITIVO_STATUSDISPO foreign key (ID_STATUS_DISPOSITIVO)
  references STATUS_DISPOSITIVO (ID_STATUS_DISPOSITIVO) on delete set null;
create index FK_DISPOSITIVO_STATUSDISPOX on DISPOSITIVO (ID_STATUS_DISPOSITIVO);
create unique index PK_DISPOSITIVOX on DISPOSITIVO (ID_DISPOSITIVO);
create unique index UK_CLIENTEX on DISPOSITIVO (ID_CLIENTE);

prompt
prompt Creating table STATUS_CLIENTE
prompt =============================
prompt
create table STATUS_CLIENTE
(
  ID_STATUS_CLIENTE NUMBER(3) not null,
  DESCRICAO         VARCHAR2(100) not null
)
;
alter table STATUS_CLIENTE
  add constraint PK_STATUSCLIENTE primary key (ID_STATUS_CLIENTE);
create unique index PK_STATUSCLIENTEX on STATUS_CLIENTE (ID_STATUS_CLIENTE);

prompt
prompt Creating table FILA
prompt ===================
prompt
create table FILA
(
  ID_FILA                        NUMBER(7) not null,
  ID_DISPOSITIVO                 NUMBER(4) not null,
  ID_BRINQUEDO                   NUMBER(4) not null,
  ID_STATUS_CLIENTE              NUMBER(3),
  DATA_HORA_ENTRADA_FILA_VIRTUAL DATE,
  DATA_HOTA_ENVIO_MENSAGEM       DATE,
  DATA_HORA_ENTRADA_FILA_FISICA  DATE,
  DATA_HORA_SAIDA_BRINQUEDO      DATE
)
;
alter table FILA
  add constraint PK_FILA primary key (ID_FILA);
alter table FILA
  add constraint UK_BRINQUEDO_DISPOSITIVO unique (ID_DISPOSITIVO, ID_BRINQUEDO);
alter table FILA
  add constraint FK_FILA_BRINQUEDO foreign key (ID_BRINQUEDO)
  references BRINQUEDO (ID_BRINQUEDO) on delete cascade;
alter table FILA
  add constraint FK_FILA_DISPOSITIVO foreign key (ID_DISPOSITIVO)
  references DISPOSITIVO (ID_DISPOSITIVO) on delete cascade;
alter table FILA
  add constraint FK_STATUSCLIENTE_FILA foreign key (ID_STATUS_CLIENTE)
  references STATUS_CLIENTE (ID_STATUS_CLIENTE) on delete set null;
create index FK_FILA_BRINQUEDOX on FILA (ID_BRINQUEDO);
create index FK_FILA_DISPOSITIVOX on FILA (ID_DISPOSITIVO);
create index FK_STATUSCLIENTE_FILAX on FILA (ID_STATUS_CLIENTE);
create unique index PK_FILAX on FILA (ID_FILA);
create unique index UK_BRINQUEDO_DISPOSITIVOX on FILA (ID_DISPOSITIVO, ID_BRINQUEDO);

prompt
prompt Creating table MODULO_SISTEMA
prompt =============================
prompt
create table MODULO_SISTEMA
(
  ID_MODULO     NUMBER(3) not null,
  NOME          VARCHAR2(50) not null,
  DESCRICAO     VARCHAR2(250),
  POSICAO       NUMBER(2),
  ID_MODULO_PAI NUMBER(3),
  PRINCIPAL     CHAR(1) default 'S' not null
)
;
alter table MODULO_SISTEMA
  add constraint MODULO_SISTEMA_PK primary key (ID_MODULO);
alter table MODULO_SISTEMA
  add constraint MODULO_SISTEMA_NOME_UK unique (NOME);
alter table MODULO_SISTEMA
  add constraint NOME_ID_MODULO_PAI_UK unique (NOME, ID_MODULO_PAI);
alter table MODULO_SISTEMA
  add constraint MODULO_SUB_MODULO_FK foreign key (ID_MODULO_PAI)
  references MODULO_SISTEMA (ID_MODULO) on delete set null;
create index MODULO_SUB_MODULO_FK_I on MODULO_SISTEMA (ID_MODULO_PAI);

prompt
prompt Creating table FUNCIONALIDADE
prompt =============================
prompt
create table FUNCIONALIDADE
(
  ID_FUNC      NUMBER(3) not null,
  ID_MODULO    NUMBER(3) not null,
  NOME         VARCHAR2(50) not null,
  DESCRICAO    VARCHAR2(250),
  VISIVEL_MENU CHAR(1) not null,
  ORDEM_FUNC   NUMBER(2),
  ACESSO       VARCHAR2(50)
)
;
alter table FUNCIONALIDADE
  add constraint PK_FUNCIONALIDADE primary key (ID_FUNC);
alter table FUNCIONALIDADE
  add constraint NOME_UK unique (NOME);
alter table FUNCIONALIDADE
  add constraint MODULO_FUNC_FK foreign key (ID_MODULO)
  references MODULO_SISTEMA (ID_MODULO) on delete cascade;
alter table FUNCIONALIDADE
  add check ( VISIVEL_MENU IN ('N' , 'S' ));
create index MODULO_FUNC_FK_I on FUNCIONALIDADE (ID_MODULO);

prompt
prompt Creating table HISTORICO_CLIENTE
prompt ================================
prompt
create table HISTORICO_CLIENTE
(
  ID_HISTORICO_CLIENTE     NUMBER(7) not null,
  DATA_HORA_ENTRADA_PARQUE DATE not null,
  DATA_HORA_SAIDA_PARQUE   DATE,
  OBSERVACAO               VARCHAR2(150),
  ID_DISPOSITIVO           NUMBER(4),
  ID_STATUS_DISPOSITIVO    NUMBER(3),
  ID_CLIENTE               NUMBER(7)
)
;
alter table HISTORICO_CLIENTE
  add constraint PK_HISTCLIENTE primary key (ID_HISTORICO_CLIENTE);
alter table HISTORICO_CLIENTE
  add constraint FK_CLIENTE foreign key (ID_CLIENTE)
  references CLIENTE (ID_CLIENTE);
alter table HISTORICO_CLIENTE
  add constraint FK_HISTCLIENT_DISPOSITIVO foreign key (ID_DISPOSITIVO)
  references DISPOSITIVO (ID_DISPOSITIVO) on delete set null;
alter table HISTORICO_CLIENTE
  add constraint FK_HISTCLIENT_STATUSDISPO foreign key (ID_STATUS_DISPOSITIVO)
  references STATUS_DISPOSITIVO (ID_STATUS_DISPOSITIVO) on delete set null;
create index FK_CLIENTE_HISTCLIENTEX on HISTORICO_CLIENTE (ID_CLIENTE);
create index FK_HISTCLIENT_DISPOSITIVOX on HISTORICO_CLIENTE (ID_DISPOSITIVO);
create index FK_HISTCLIENT_STATUSDISPOX on HISTORICO_CLIENTE (ID_STATUS_DISPOSITIVO);
create unique index PK_HISTCLIENTEX on HISTORICO_CLIENTE (ID_HISTORICO_CLIENTE);

prompt
prompt Creating table HISTORICO_CLIENTE_BRINQUEDO
prompt ==========================================
prompt
create table HISTORICO_CLIENTE_BRINQUEDO
(
  ID_HISTORICO_CLIENTE_BRINQUEDO NUMBER(7) not null,
  ID_BRINQUEDO                   NUMBER(4),
  DATA_HORA_ENTRADA_FILA_VIRTUAL DATE,
  DATA_HORA_ENVIO_MENSAGEM       DATE,
  DATA_HORA_ENTRADA_FILA_FISICA  DATE,
  DATA_HORA_SAIDA_BRINQUEDO      DATE,
  ID_CLIENTE                     NUMBER(7)
)
;
alter table HISTORICO_CLIENTE_BRINQUEDO
  add constraint PK_HISTCLIENTEBRINQ primary key (ID_HISTORICO_CLIENTE_BRINQUEDO);
alter table HISTORICO_CLIENTE_BRINQUEDO
  add constraint FK_HISTCLIENTBRINQ_BRINQUEDO foreign key (ID_BRINQUEDO)
  references BRINQUEDO (ID_BRINQUEDO) on delete set null;
create index FK_CLIENTE_HISTCLIENTBRINQX on HISTORICO_CLIENTE_BRINQUEDO (ID_CLIENTE);
create index FK_HISTCLIENTBRINQ_BRINQUEDOX on HISTORICO_CLIENTE_BRINQUEDO (ID_BRINQUEDO);
create unique index PK_HISTCLIENTEBRINQX on HISTORICO_CLIENTE_BRINQUEDO (ID_HISTORICO_CLIENTE_BRINQUEDO);

prompt
prompt Creating table HISTORICO_TEMPO_BRINQUEDO
prompt ========================================
prompt
create table HISTORICO_TEMPO_BRINQUEDO
(
  ID_HIST_TEMP_BRINQUEDO NUMBER not null,
  ID_BRINQUEDO           NUMBER,
  TEMPO_INICIO           DATE,
  TEMPO_FIM              DATE
)
;
alter table HISTORICO_TEMPO_BRINQUEDO
  add constraint PK_HIST_TEMP_BRINQ primary key (ID_HIST_TEMP_BRINQUEDO);
alter table HISTORICO_TEMPO_BRINQUEDO
  add constraint FK_HISTEMPBRINQ_X_BRINQ foreign key (ID_BRINQUEDO)
  references BRINQUEDO (ID_BRINQUEDO);

prompt
prompt Creating table LOGRADOURO
prompt =========================
prompt
create table LOGRADOURO
(
  ID_LOGRADOURO       NUMBER(9) not null,
  CEP                 VARCHAR2(8) not null,
  LOGRADOURO          VARCHAR2(50) not null,
  ABREV               VARCHAR2(25),
  DATA_ATUALIZACAO    DATE,
  ID_TIPO_LOG         NUMBER(9) not null,
  ID_BAIRRO           NUMBER(5) not null,
  ATIVO               CHAR(1) not null,
  ID_TAB_AUX_TIPO_LOG NUMBER(3),
  ID_CIDADE           NUMBER(5)
)
;
alter table LOGRADOURO
  add constraint CEP_PK primary key (ID_LOGRADOURO);
alter table LOGRADOURO
  add constraint CEP_BAIRRO_UK unique (CEP, ID_BAIRRO);
alter table LOGRADOURO
  add constraint CEP_CIDADE_UK unique (CEP, ID_CIDADE);
alter table LOGRADOURO
  add constraint BAIRRO_CEP_FK foreign key (ID_BAIRRO)
  references BAIRRO (ID_BAIRRO);
alter table LOGRADOURO
  add constraint CIDADE_LOGRADOURO_FK foreign key (ID_CIDADE)
  references CIDADE (ID_CIDADE);
alter table LOGRADOURO
  add constraint TAB_AUX_LOGRADOURO_FK foreign key (ID_TAB_AUX_TIPO_LOG)
  references TABELA_AUXILIAR (ID_TAB_AUX) on delete cascade;
alter table LOGRADOURO
  add constraint VAL_AUX_CEP_TIPO_LOG_FK foreign key (ID_TIPO_LOG)
  references VALOR_TAB_AUX (ID_VAL_TAB_AUX);
alter table LOGRADOURO
  add check ( ATIVO IN ('N' , 'S' ));
create index BAIRRO_CEP_FK_I on LOGRADOURO (ID_BAIRRO);
create index TAB_AUX_LOGRADOURO_FK_I on LOGRADOURO (ID_TAB_AUX_TIPO_LOG);
create index VAL_AUX_CEP_TIPO_LOG_FK_I on LOGRADOURO (ID_TIPO_LOG);

prompt
prompt Creating table PESSOA
prompt =====================
prompt
create table PESSOA
(
  ID_PESSOA           NUMBER(5) not null,
  NOME_PESSOA         VARCHAR2(50) not null,
  CPF                 VARCHAR2(11),
  CNPJ                VARCHAR2(14),
  RG_NUM              VARCHAR2(10),
  RG_DIG              VARCHAR2(1),
  ORGAO_EMISSAO_RG    VARCHAR2(10),
  DATA_NASCIMENTO     DATE,
  DATA_ATIVACAO       DATE not null,
  DATA_ULT_ALTERACAO  DATE default SYSDATE,
  DATA_ADMISSAO       DATE,
  DATA_DESATIVACAO    DATE,
  ATIVO               CHAR(1) not null,
  ID_TIPO_PES         NUMBER(9) not null,
  ID_USUARIO_ATIVA    NUMBER(5),
  ID_USUARIO_DESATIVA NUMBER(5),
  ID_USUARIO_ALT      NUMBER(5),
  SEXO                CHAR(1),
  ID_TIPO_PES_TAB_AUX NUMBER(3)
)
;
alter table PESSOA
  add constraint COLABORADOR_PK primary key (ID_PESSOA);
alter table PESSOA
  add constraint PESSOA_USUARIO_ATIVA_FK foreign key (ID_USUARIO_ATIVA)
  references USUARIO (ID_USUARIO) on delete set null;
alter table PESSOA
  add constraint TAB_AUX_TIPO_PES_PESSOA_FK foreign key (ID_TIPO_PES_TAB_AUX)
  references TABELA_AUXILIAR (ID_TAB_AUX);
alter table PESSOA
  add constraint USUARIO_PESSOA_ALT_FK foreign key (ID_USUARIO_ALT)
  references USUARIO (ID_USUARIO) on delete set null;
alter table PESSOA
  add constraint USUARIO_PESSOA_DESATIVA_FK foreign key (ID_USUARIO_DESATIVA)
  references USUARIO (ID_USUARIO) on delete set null;
alter table PESSOA
  add constraint PESSOA_SEXO_CK
  check (SEXO IN ('M', 'F'));
alter table PESSOA
  add check ( ATIVO IN ('N' , 'S' ));
create index PESSOA_USUARIO_ATIVA_FK_I on PESSOA (ID_USUARIO_ATIVA);
create index USUARIO_PESSOA_ALT_FK_I on PESSOA (ID_USUARIO_ALT);
create index USUARIO_PESSOA_DESATIVA_FK_I on PESSOA (ID_USUARIO_DESATIVA);

prompt
prompt Creating table USUARIO
prompt ======================
prompt
create table USUARIO
(
  ID_USUARIO          NUMBER(5) not null,
  LOGIN               VARCHAR2(12 CHAR) not null,
  SENHA               VARCHAR2(50 CHAR) not null,
  DICA_SENHA          VARCHAR2(30),
  OBSERVACAO          VARCHAR2(1000),
  ATIVO               CHAR(1) default 'S' not null,
  DATA_ATIVACAO       DATE not null,
  DATA_ULT_ALTERACAO  DATE default SYSDATE,
  DATA_DESATIVACAO    DATE,
  ID_USUARIO_DESATIVA NUMBER(5),
  ID_USUARIO_ATIVA    NUMBER(5) not null,
  ID_USUARIO_ALT      NUMBER(5),
  ID_PERFIL           NUMBER(3) not null,
  ID_PESSOA           NUMBER(5),
  BLOQUEADO           CHAR(1) default 'N' not null,
  AUDITOR             CHAR(1) default 'N' not null,
  MASTER              CHAR(1) default 'N' not null,
  ADMINISTRADOR       CHAR(1) default 'N' not null
)
;
alter table USUARIO
  add constraint USUARIO_PK primary key (ID_USUARIO);
alter table USUARIO
  add constraint LOGIN_UK unique (LOGIN);
alter table USUARIO
  add constraint PESSOA_USUARIO_FK foreign key (ID_PESSOA)
  references PESSOA (ID_PESSOA) on delete set null;
alter table USUARIO
  add constraint USU_ATIVA_USU_FK foreign key (ID_USUARIO_ATIVA)
  references USUARIO (ID_USUARIO);
alter table USUARIO
  add constraint USU_DESATIVACAO_FK foreign key (ID_USUARIO_DESATIVA)
  references USUARIO (ID_USUARIO) on delete set null;
alter table USUARIO
  add constraint USU_ULT_ALTER_FK foreign key (ID_USUARIO_ALT)
  references USUARIO (ID_USUARIO) on delete set null;
alter table USUARIO
  add check ( ATIVO IN ('N' , 'S' ));
create index PESSOA_USUARIO_FK_I on USUARIO (ID_PESSOA);
create index USU_ATIVA_USU_FK_I on USUARIO (ID_USUARIO_ATIVA);
create index USU_DESATIVACAO_FK_I on USUARIO (ID_USUARIO_DESATIVA);
create index USU_ULT_ALTER_FKV1 on USUARIO (ID_USUARIO_ALT);

prompt
prompt Creating table PERFIL
prompt =====================
prompt
create table PERFIL
(
  ID_PERFIL           NUMBER(3) not null,
  NOME_PERFIL         VARCHAR2(50) not null,
  DESCRICAO_PERFIL    VARCHAR2(250),
  DATA_ATIVACAO       DATE not null,
  DATA_ULT_ALTERACAO  DATE,
  DATA_DESATIVACAO    DATE,
  ID_USUARIO_ATIVA    NUMBER(5),
  ID_USUARIO_ALT      NUMBER(5),
  ID_USUARIO_DESATIVA NUMBER(5),
  ATIVO               CHAR(1) default 'S' not null
)
;
alter table PERFIL
  add constraint PERFIL_PK primary key (ID_PERFIL);
alter table PERFIL
  add constraint PERFIL_NOME_UK unique (NOME_PERFIL);
alter table PERFIL
  add constraint PER_ACESSO_USU_ATIVA_FK foreign key (ID_USUARIO_ATIVA)
  references USUARIO (ID_USUARIO);
alter table PERFIL
  add constraint PER_ACESSO_USU_DESATIVA_FK foreign key (ID_USUARIO_DESATIVA)
  references USUARIO (ID_USUARIO) on delete set null;
alter table PERFIL
  add constraint PER_ACESSO_USU_ULT_ALT_FK foreign key (ID_USUARIO_ALT)
  references USUARIO (ID_USUARIO) on delete set null;
alter table PERFIL
  add check ( ATIVO IN ('N' , 'S' ));
create index PER_ACESSO_USU_ATIVA_FK_I on PERFIL (ID_USUARIO_ATIVA);
create index PER_ACESSO_USU_DESATIVA_FK_I on PERFIL (ID_USUARIO_DESATIVA);
create index PER_ACESSO_USU_ULT_ALT_FK_I on PERFIL (ID_USUARIO_ALT);

prompt
prompt Creating table PERFIL_FUNCIONALIDADE
prompt ====================================
prompt
create table PERFIL_FUNCIONALIDADE
(
  ID_PERFIL_FUNC      NUMBER(3) not null,
  ID_FUNC             NUMBER(3) not null,
  OBSERVACAO          VARCHAR2(250),
  ID_USUARIO_ATIVA    NUMBER(5) not null,
  ID_USUARIO_DESATIVA NUMBER(5),
  DATA_ATIVACAO       DATE not null,
  DATA_DESATIVACAO    DATE,
  ATIVO               CHAR(1) not null,
  ID_PERFIL           NUMBER(3) not null
)
;
alter table PERFIL_FUNCIONALIDADE
  add constraint PK_PERFIL_FUNC primary key (ID_PERFIL_FUNC);
alter table PERFIL_FUNCIONALIDADE
  add constraint PERFIL_FUNCIONALIDADE_UK unique (ID_FUNC, ID_PERFIL, ATIVO);
alter table PERFIL_FUNCIONALIDADE
  add constraint FUNC_PERF_FUNC_FK foreign key (ID_FUNC)
  references FUNCIONALIDADE (ID_FUNC);
alter table PERFIL_FUNCIONALIDADE
  add constraint PERFIL_FUNC_PERFIL_FK foreign key (ID_PERFIL)
  references PERFIL (ID_PERFIL);
alter table PERFIL_FUNCIONALIDADE
  add constraint USU_ATIVA_PERF_FUNC_FK foreign key (ID_USUARIO_ATIVA)
  references USUARIO (ID_USUARIO) on delete cascade;
alter table PERFIL_FUNCIONALIDADE
  add constraint USU_DESATIVA_PER_FUNC_FK foreign key (ID_USUARIO_DESATIVA)
  references USUARIO (ID_USUARIO) on delete set null;
alter table PERFIL_FUNCIONALIDADE
  add check ( ATIVO IN ('N' , 'S' ));
create index FUNC_PERF_FUNC_FK_I on PERFIL_FUNCIONALIDADE (ID_FUNC);
create index PERFIL_FUNCIONALIDADE_I on PERFIL_FUNCIONALIDADE (ID_FUNC, ID_PERFIL);
create index PERFIL_FUNC_PERFIL_FK_I on PERFIL_FUNCIONALIDADE (ID_PERFIL);
create index USU_ATIVA_PER_FUNC_FK_I on PERFIL_FUNCIONALIDADE (ID_USUARIO_ATIVA);
create index USU_DESATIVA_PER_FUNC_FK_I on PERFIL_FUNCIONALIDADE (ID_USUARIO_DESATIVA);

prompt
prompt Creating table PERFIL_USUARIO
prompt =============================
prompt
create table PERFIL_USUARIO
(
  ID_PERFIL_USUARIO   NUMBER(3) not null,
  ID_PERFIL           NUMBER(3) not null,
  OBSERVACAO          VARCHAR2(1000),
  ID_USUARIO_ATIVA    NUMBER(5) not null,
  ID_USUARIO_ALT      NUMBER(5),
  ID_USUARIO          NUMBER(5) not null,
  ID_USUARIO_DESATIVA NUMBER(5),
  DATA_ATIVACAO       DATE not null,
  DATA_ULT_ALTERACAO  DATE not null,
  DATA_DESATIVACAO    DATE,
  ATIVO               CHAR(1) default 'S' not null
)
;
alter table PERFIL_USUARIO
  add constraint PERFIL_USUARIO_PK primary key (ID_PERFIL_USUARIO);
alter table PERFIL_USUARIO
  add constraint ID_USUARIO_ID_PERFIL_UK unique (ID_USUARIO, ID_PERFIL);
alter table PERFIL_USUARIO
  add constraint PERFIL_ULT_ALTER_USUARIO_FK foreign key (ID_USUARIO_ALT)
  references USUARIO (ID_USUARIO) on delete set null;
alter table PERFIL_USUARIO
  add constraint PERFIL_USU_PERFIL_ACESSO_FK foreign key (ID_PERFIL)
  references PERFIL (ID_PERFIL);
alter table PERFIL_USUARIO
  add constraint USU_ATIVA_PERFIL_USU_FK foreign key (ID_USUARIO_ATIVA)
  references USUARIO (ID_USUARIO);
alter table PERFIL_USUARIO
  add constraint USU_DESATIVA_PERF_USU_FK foreign key (ID_USUARIO_DESATIVA)
  references USUARIO (ID_USUARIO) on delete set null;
alter table PERFIL_USUARIO
  add constraint USU_PERF_USU_FK foreign key (ID_USUARIO)
  references USUARIO (ID_USUARIO);
alter table PERFIL_USUARIO
  add check ( ATIVO IN ('N' , 'S' ));
create index PERFIL_ULT_ALTER_USUARIO_FK_I on PERFIL_USUARIO (ID_USUARIO_ALT);
create index PERFIL_USUARIO_I on PERFIL_USUARIO (ID_PERFIL, ID_USUARIO);
create index PERFIL_USU_PERFIL_ACESSO_FK_I on PERFIL_USUARIO (ID_PERFIL);
create index USU_ATIVA_PERFIL_USU_FK_I on PERFIL_USUARIO (ID_USUARIO_ATIVA);
create index USU_DESATIVA_PERF_USU_FK_I on PERFIL_USUARIO (ID_USUARIO_DESATIVA);
create index USU_PERF_USU_FK_I on PERFIL_USUARIO (ID_USUARIO);

prompt
prompt Creating table TERMINAL_CONSULTA
prompt ================================
prompt
create table TERMINAL_CONSULTA
(
  ID_TERMINAL_CONSULTA NUMBER(4) not null,
  DESCRICAO            VARCHAR2(100),
  LOCALIZACAO          VARCHAR2(150),
  IP                   VARCHAR2(15),
  HOST_NAME            VARCHAR2(30)
)
;
alter table TERMINAL_CONSULTA
  add constraint PK_TERMINALCONSULTA primary key (ID_TERMINAL_CONSULTA);
create unique index PK_TERMINALCONSULTAX on TERMINAL_CONSULTA (ID_TERMINAL_CONSULTA);

prompt
prompt Creating sequence BAIRRO_SEQ
prompt ============================
prompt
create sequence BAIRRO_SEQ
minvalue 1
maxvalue 999999999999999999999999999
start with 3
increment by 1
cache 2;

prompt
prompt Creating sequence CIDADE_SEQ
prompt ============================
prompt
create sequence CIDADE_SEQ
minvalue 1
maxvalue 999999999999999999999999999
start with 10066
increment by 1
cache 2;

prompt
prompt Creating sequence FUNCIONALIDADE_SEQ
prompt ====================================
prompt
create sequence FUNCIONALIDADE_SEQ
minvalue 1
maxvalue 999999999999999999999999999
start with 16
increment by 1
cache 2;

prompt
prompt Creating sequence LOGRADOURO_SEQ
prompt ================================
prompt
create sequence LOGRADOURO_SEQ
minvalue 1
maxvalue 999999999999999999999999999
start with 11
increment by 1
cache 2;

prompt
prompt Creating sequence MODULO_SISTEMA_SEQ
prompt ====================================
prompt
create sequence MODULO_SISTEMA_SEQ
minvalue 1
maxvalue 999999999999999999999999999
start with 13
increment by 1
cache 3;

prompt
prompt Creating sequence PERFIL_FUNCIONALIDADE_SEQ
prompt ===========================================
prompt
create sequence PERFIL_FUNCIONALIDADE_SEQ
minvalue 1
maxvalue 999999999999999999999999999
start with 11
increment by 1
cache 5;

prompt
prompt Creating sequence PERFIL_SEQ
prompt ============================
prompt
create sequence PERFIL_SEQ
minvalue 1
maxvalue 999999999999999999999999999
start with 20
increment by 1
cache 3;

prompt
prompt Creating sequence PERFIL_USUARIO_SEQ
prompt ====================================
prompt
create sequence PERFIL_USUARIO_SEQ
minvalue 1
maxvalue 999999999999999999999999999
start with 1
increment by 1
cache 10;

prompt
prompt Creating sequence PESSOA_SEQ
prompt ============================
prompt
create sequence PESSOA_SEQ
minvalue 1
maxvalue 999999999999999999999999999
start with 11
increment by 1
cache 2;

prompt
prompt Creating sequence SEQ_BRINQUEDO
prompt ===============================
prompt
create sequence SEQ_BRINQUEDO
minvalue 1
maxvalue 999999999999999999999999999
start with 4
increment by 1
cache 20;

prompt
prompt Creating sequence SEQ_CATRACA
prompt =============================
prompt
create sequence SEQ_CATRACA
minvalue 1
maxvalue 999999999999999999999999999
start with 22
increment by 1
cache 20;

prompt
prompt Creating sequence SEQ_CLIENTE
prompt =============================
prompt
create sequence SEQ_CLIENTE
minvalue 1
maxvalue 999999999999999999999999999
start with 91
increment by 1
cache 20;

prompt
prompt Creating sequence SEQ_DISPOSITIVO
prompt =================================
prompt
create sequence SEQ_DISPOSITIVO
minvalue 1
maxvalue 999999999999999999999999999
start with 24
increment by 1
cache 20;

prompt
prompt Creating sequence SEQ_FILA
prompt ==========================
prompt
create sequence SEQ_FILA
minvalue 1
maxvalue 999999999999999999999999999
start with 21
increment by 1
cache 20;

prompt
prompt Creating sequence SEQ_HIST_CLIENTE_BRINQUEDO
prompt ============================================
prompt
create sequence SEQ_HIST_CLIENTE_BRINQUEDO
minvalue 1
maxvalue 999999999999999999999999999
start with 21
increment by 1
cache 20;

prompt
prompt Creating sequence SEQ_HISTORICO_CLIENTE
prompt =======================================
prompt
create sequence SEQ_HISTORICO_CLIENTE
minvalue 1
maxvalue 999999999999999999999999999
start with 28
increment by 1
cache 20;

prompt
prompt Creating sequence SEQ_HTB
prompt =========================
prompt
create sequence SEQ_HTB
minvalue 0
maxvalue 999999999999999999
start with 61
increment by 1
nocache
order;

prompt
prompt Creating sequence SEQ_STATUS_BRINQUEDO
prompt ======================================
prompt
create sequence SEQ_STATUS_BRINQUEDO
minvalue 1
maxvalue 999999999999999999999999999
start with 2
increment by 1
cache 20;

prompt
prompt Creating sequence SEQ_STATUS_CLIENTE
prompt ====================================
prompt
create sequence SEQ_STATUS_CLIENTE
minvalue 1
maxvalue 999999999999999999999999999
start with 22
increment by 1
cache 20;

prompt
prompt Creating sequence SEQ_STATUS_DISPOSITIVO
prompt ========================================
prompt
create sequence SEQ_STATUS_DISPOSITIVO
minvalue 1
maxvalue 999999999999999999999999999
start with 2
increment by 1
cache 20;

prompt
prompt Creating sequence SEQ_TERMINAL_CONSULTA
prompt =======================================
prompt
create sequence SEQ_TERMINAL_CONSULTA
minvalue 1
maxvalue 999999999999999999999999999
start with 1
increment by 1
cache 20;

prompt
prompt Creating sequence TABELA_AUXILIAR_SEQ
prompt =====================================
prompt
create sequence TABELA_AUXILIAR_SEQ
minvalue 1
maxvalue 999999999999999999999999999
start with 16
increment by 1
cache 2;

prompt
prompt Creating sequence USUARIO_SEQ
prompt =============================
prompt
create sequence USUARIO_SEQ
minvalue 1
maxvalue 999999999999999999999999999
start with 7
increment by 1
cache 5;

prompt
prompt Creating sequence VALOR_TAB_AUX_SEQ
prompt ===================================
prompt
create sequence VALOR_TAB_AUX_SEQ
minvalue 1
maxvalue 999999999999999999999999999
start with 122
increment by 1
cache 10;

prompt
prompt Creating view EMP_DETAILS_VIEW
prompt ==============================
prompt
CREATE OR REPLACE VIEW EMP_DETAILS_VIEW AS
SELECT
  e.employee_id,
  e.job_id,
  e.manager_id,
  e.department_id,
  d.location_id,
  l.country_id,
  e.first_name,
  e.last_name,
  e.salary,
  e.commission_pct,
  d.department_name,
  j.job_title,
  l.city,
  l.state_province,
  c.country_name,
  r.region_name
FROM
  employees e,
  departments d,
  jobs j,
  locations l,
  countries c,
  regions r
WHERE e.department_id = d.department_id
  AND d.location_id = l.location_id
  AND l.country_id = c.country_id
  AND c.region_id = r.region_id
  AND j.job_id = e.job_id
WITH READ ONLY
/

prompt
prompt Creating function FUNC_QTDE_CLIENTE_FILA_TOTAL
prompt ==============================================
prompt
CREATE OR REPLACE FUNCTION FUNC_QTDE_CLIENTE_FILA_TOTAL(P_ID_BRINQUEDO NUMBER)
  RETURN INTEGER AS

  --
  V_QTD_PESSOAS_FILA_TOTAL INTEGER;
  --

BEGIN
  ------------------------------------------------------------
  -- 1 - REL PRIMARIO                                       --
  -- 2 - AGUARDANDO BRINQUEDO                               --
  -- 3 - BRINQUEDO AGUARDANDO CLIENTE                       --
  -- 4 - TEMPO EXPIRADO                                     --
  -- 5 - CLIENTE NA FILA FISICA                             --
  -- 6 - CLIENTE NO BRINQUEDO                               --
  -- 7 - SAIO DO BRINQUEDO                                  --
  ------------------------------------------------------------
  --
  SELECT COUNT(*)
    INTO V_QTD_PESSOAS_FILA_TOTAL
    FROM FILA F
   WHERE F.ID_BRINQUEDO = P_ID_BRINQUEDO
     AND F.ID_STATUS_CLIENTE IN (2, 3, 5);
  --
  RETURN V_QTD_PESSOAS_FILA_TOTAL;
  --

END FUNC_QTDE_CLIENTE_FILA_TOTAL;
/

prompt
prompt Creating function FUNC_VALIDA_DISPOSITIVO
prompt =========================================
prompt
CREATE OR REPLACE FUNCTION FUNC_VALIDA_DISPOSITIVO(COD_RFID VARCHAR2)
  RETURN NUMBER AS

  V_COUNT NUMBER;

BEGIN

  SELECT COUNT(*)
    INTO V_COUNT
    FROM DISPOSITIVO DISP, CLIENTE CLI
   WHERE DISP.ID_DISPOSITIVO = CLI.ID_DISPOSITIVO
     AND DISP.ID_RFID = COD_RFID;

  IF (V_COUNT > 0) THEN
    RETURN 1;
  ELSE
    RETURN 0;
  END IF;

END FUNC_VALIDA_DISPOSITIVO;
/

prompt
prompt Creating procedure ADD_JOB_HISTORY
prompt ==================================
prompt
CREATE OR REPLACE PROCEDURE add_job_history
  (  p_emp_id          job_history.employee_id%type
   , p_start_date      job_history.start_date%type
   , p_end_date        job_history.end_date%type
   , p_job_id          job_history.job_id%type
   , p_department_id   job_history.department_id%type
   )
IS
BEGIN
  INSERT INTO job_history (employee_id, start_date, end_date,
                           job_id, department_id)
    VALUES(p_emp_id, p_start_date, p_end_date, p_job_id, p_department_id);
END add_job_history;
/

prompt
prompt Creating procedure PROC_ENTRADA_BRINQUEDO
prompt =========================================
prompt
CREATE OR REPLACE PROCEDURE PROC_ENTRADA_BRINQUEDO(P_ID_BRINQUEDO IN NUMBER,
                                                   P_COD_RFID     IN VARCHAR2,
                                                   P_RESPOSTA     OUT VARCHAR2) IS

  V_ID_DISPOSITIVO    NUMBER;
  V_ID_STATUS_CLIENTE NUMBER;

BEGIN
  ------------------------------------------------------------
  -- 1 - REL PRIMARIO                                       --
  -- 2 - AGUARDANDO BRINQUEDO                               --
  -- 3 - BRINQUEDO AGUARDANDO CLIENTE                       --
  -- 4 - TEMPO EXPIRADO                                     --
  -- 5 - CLIENTE NA FILA FISICA                             --
  -- 6 - CLIENTE NO BRINQUEDO                               --
  -- 7 - SAIO DO BRINQUEDO                                  --
  ------------------------------------------------------------
  --
  DECLARE
    -- VERIFICA SE O DISPOSITIVO É VÁLIDO
    V_COUNT NUMBER := 0;
  BEGIN
    V_COUNT := FUNC_VALIDA_DISPOSITIVO(P_COD_RFID);
    --
    IF (V_COUNT = 0) THEN
      P_RESPOSTA := 'DISPOSITIVO INVÁLIDO';
      RETURN;
    END IF;
    --
  END;
  --
  SELECT D.ID_DISPOSITIVO
    INTO V_ID_DISPOSITIVO
    FROM DISPOSITIVO D
   WHERE D.ID_RFID = P_COD_RFID;
  --
  BEGIN
    SELECT F.ID_STATUS_CLIENTE
      INTO V_ID_STATUS_CLIENTE
      FROM FILA F
     WHERE F.ID_DISPOSITIVO = V_ID_DISPOSITIVO
       AND F.ID_BRINQUEDO = P_ID_BRINQUEDO;
  
  EXCEPTION
    WHEN NO_DATA_FOUND THEN
      V_ID_STATUS_CLIENTE:= NULL;
  END;
  --
  IF (V_ID_STATUS_CLIENTE IS NULL) THEN
    --
    P_RESPOSTA := 'VOCÊ NÃO ESTÁ CADASTRADO NA FILA DESTE BRINQUEDO, CASO QUEIRA SE CADASTRAR NESTE BRINQUEDO, COMPARECER A PORTARIA DO MESMO, OBRIGADO.';
    --
  ELSIF (V_ID_STATUS_CLIENTE = 3) THEN
    --
    P_RESPOSTA := 'PARABENS! A SUA VEZ CHEGOU, ENTRE NA FILA DO BRINQUEDO.';
    --
    UPDATE FILA F
       SET F.ID_STATUS_CLIENTE             = 5,
           F.DATA_HORA_ENTRADA_FILA_FISICA = SYSDATE
     WHERE F.ID_DISPOSITIVO = V_ID_DISPOSITIVO
       AND F.ID_BRINQUEDO = P_ID_BRINQUEDO;
    --
  ELSIF (V_ID_STATUS_CLIENTE = 2) THEN
    --
    P_RESPOSTA := 'A SUA VEZ AINDA NÃO CHEGOU, AGUARDE QUE LOGO SERÁ CHAMADO, OBRIGADO.';
    --
  ELSIF (V_ID_STATUS_CLIENTE = 4) THEN
    --
    P_RESPOSTA := 'A SUA VEZ EXPIROU, VOCÊ NÃO COMPARECEU NO TEMPO SOLICITADO, COMPAREÇA A PORTARIA DO BRINQUEDO E SE CADASTRE CASO AINDA DESEJA BRINCAR, OBRIGADO.';
    --
  END IF;
  --
  -- **** TRATAR O EVENTO NA TELA: CLIENTE ENTROU NA FILA FISICA E DESEJA SAIR.
  --
  COMMIT;
  --
END PROC_ENTRADA_BRINQUEDO;
/

prompt
prompt Creating procedure PROC_ENTRADA_BRINQUEDO_CATRACA
prompt =================================================
prompt
CREATE OR REPLACE PROCEDURE PROC_ENTRADA_BRINQUEDO_CATRACA(P_ID_BRINQUEDO IN NUMBER,
                                                           P_COD_RFID     IN VARCHAR2,
                                                           P_RESPOSTA     OUT VARCHAR2) IS

  V_ID_DISPOSITIVO    NUMBER;
  V_ID_STATUS_CLIENTE NUMBER;

BEGIN
  ------------------------------------------------------------
  -- 1 - REL PRIMARIO                                       --
  -- 2 - AGUARDANDO BRINQUEDO                               --
  -- 3 - BRINQUEDO AGUARDANDO CLIENTE                       --
  -- 4 - TEMPO EXPIRADO                                     --
  -- 5 - CLIENTE NA FILA FISICA                             --
  -- 6 - CLIENTE NO BRINQUEDO                               --
  -- 7 - SAIO DO BRINQUEDO                                  --
  ------------------------------------------------------------
  --
  DECLARE
    -- VERIFICA SE O DISPOSITIVO É VÁLIDO
    V_COUNT NUMBER := 0;
  BEGIN
    V_COUNT := FUNC_VALIDA_DISPOSITIVO(P_COD_RFID);
    --
    IF (V_COUNT = 0) THEN
      P_RESPOSTA := 'DISPOSITIVO INVÁLIDO';
      RETURN;
    END IF;
    --
  END;
  --
  SELECT D.ID_DISPOSITIVO
    INTO V_ID_DISPOSITIVO
    FROM DISPOSITIVO D
   WHERE D.ID_RFID = P_COD_RFID;
  --
  BEGIN
    SELECT F.ID_STATUS_CLIENTE
      INTO V_ID_STATUS_CLIENTE
      FROM FILA F
     WHERE F.ID_DISPOSITIVO = V_ID_DISPOSITIVO
       AND F.ID_BRINQUEDO = P_ID_BRINQUEDO;

  EXCEPTION
    WHEN NO_DATA_FOUND THEN
      V_ID_STATUS_CLIENTE := NULL;
  END;
  --
  IF (V_ID_STATUS_CLIENTE != 5) THEN
    --
    P_RESPOSTA := 'VOCÊ NÃO ESTÁ AUTORIZADO A ENTRAR NESTE BRINQUEDO, POR FAVOR, FALE COM UM DE NOSSOS FUNCIONÁRIOS, OBRIGADO.';
    --
  ELSIF (V_ID_STATUS_CLIENTE = 5) THEN
    --
    P_RESPOSTA := 'ENTRE NO BRINQUEDO, OBRIGADO.';
    --
    UPDATE FILA F
       SET F.ID_STATUS_CLIENTE             = 6
     WHERE F.ID_DISPOSITIVO = V_ID_DISPOSITIVO
       AND F.ID_BRINQUEDO = P_ID_BRINQUEDO;
    --
  END IF;
  --
  COMMIT;
  --
END PROC_ENTRADA_BRINQUEDO_CATRACA;
/

prompt
prompt Creating procedure PROC_ENVIA_SMS
prompt =================================
prompt
CREATE OR REPLACE PROCEDURE PROC_ENVIA_SMS(P_ID_BRINQUEDO IN NUMBER,
                                           P_QTD_CLIENTE  IN NUMBER) IS

  CURSOR C IS
    SELECT *
      FROM FILA F
     WHERE F.ID_BRINQUEDO = P_ID_BRINQUEDO
       AND F.ID_STATUS_CLIENTE = 2 -- CLIENTE AGUARDANDO BRINQUEDO
       AND ROWNUM <= P_QTD_CLIENTE
     ORDER BY F.DATA_HORA_ENTRADA_FILA_VIRTUAL;
  --
  P C%ROWTYPE;
  --
BEGIN

  FOR P IN C LOOP
    --
    DBMS_OUTPUT.PUT_LINE('ENVIA MENSAGEM PARA ' || P.ID_DISPOSITIVO);
    --
    UPDATE FILA F
       SET F.ID_STATUS_CLIENTE        = 3, ---BRINQUEDO AGUARDANDO CLIENTE
           F.DATA_HOTA_ENVIO_MENSAGEM = SYSDATE
     WHERE F.ID_BRINQUEDO = P_ID_BRINQUEDO
       AND F.ID_DISPOSITIVO = P.ID_DISPOSITIVO;    
    --
     END LOOP;
  
  END;
/

prompt
prompt Creating procedure PROC_EXPIRAR_CLIENTE
prompt =======================================
prompt
CREATE OR REPLACE PROCEDURE PROC_EXPIRAR_CLIENTE(P_ID_BRINQUEDO IN NUMBER) IS

  --
  V_TEMPO_MENSAGEM NUMBER;
  --
  CURSOR C IS
    SELECT

     (TO_NUMBER(TO_CHAR(SYSDATE, 'HH')) * 60 +
     TO_NUMBER(TO_CHAR(SYSDATE, 'MI'))) -

     (TO_NUMBER(TO_CHAR(F.DATA_HOTA_ENVIO_MENSAGEM, 'HH')) * 60 +
     TO_NUMBER(TO_CHAR(F.DATA_HOTA_ENVIO_MENSAGEM, 'MI'))) TEMPO,
     F.ID_DISPOSITIVO ID_DISPOSITIVO

      FROM FILA F
     WHERE F.ID_BRINQUEDO = P_ID_BRINQUEDO
       AND F.ID_STATUS_CLIENTE = 3;

  --
  P C%ROWTYPE;
  --
  V_COUNT NUMBER := 0;
  --
BEGIN

  --
  SELECT B.TEMPO_MENSAGEM
    INTO V_TEMPO_MENSAGEM
    FROM BRINQUEDO B
   WHERE B.ID_BRINQUEDO = P_ID_BRINQUEDO;
  --
  FOR P IN C LOOP
    --
    IF (P.TEMPO > V_TEMPO_MENSAGEM) THEN
      --
      UPDATE FILA F
         SET F.ID_STATUS_CLIENTE = 4
       WHERE F.ID_BRINQUEDO = P_ID_BRINQUEDO
         AND F.ID_DISPOSITIVO = P.ID_DISPOSITIVO
         AND F.ID_STATUS_CLIENTE = 3;
      --
    END IF;
    --
    COMMIT;
    --
  END LOOP;
  --
END PROC_EXPIRAR_CLIENTE;
/

prompt
prompt Creating procedure PROC_HISTORICO_CLIENTE_BRINQ
prompt ===============================================
prompt
CREATE OR REPLACE PROCEDURE PROC_HISTORICO_CLIENTE_BRINQ(P_ID_BRINQUEDO IN NUMBER) IS

  V_ID_CLIENTE NUMBER;
  --
  CURSOR C IS
    select id_fila,
           id_dispositivo,
           id_brinquedo,
           id_status_cliente,
           data_hora_entrada_fila_virtual,
           data_hota_envio_mensagem,
           data_hora_entrada_fila_fisica,
           data_hora_saida_brinquedo
      from fila F
     WHERE F.ID_STATUS_CLIENTE = 6
       AND F.ID_BRINQUEDO = P_ID_BRINQUEDO;
  --
  P C%ROWTYPE;
  --
BEGIN

  FOR P IN C LOOP
    SELECT CLI.ID_CLIENTE
      INTO V_ID_CLIENTE
      FROM CLIENTE CLI
     WHERE CLI.ID_DISPOSITIVO = P.ID_DISPOSITIVO;
  
    insert into historico_cliente_brinquedo
      (id_historico_cliente_brinquedo,
       id_brinquedo,
       data_hora_entrada_fila_virtual,
       data_hora_envio_mensagem,
       data_hora_entrada_fila_fisica,
       data_hora_saida_brinquedo,
       id_cliente)
    values
      (SEQ_HIST_CLIENTE_BRINQUEDO.NEXTVAL,
       P.ID_BRINQUEDO,
       P.DATA_HORA_ENTRADA_FILA_VIRTUAL,
       P.DATA_HOTA_ENVIO_MENSAGEM,
       P.DATA_HORA_ENTRADA_FILA_FISICA,
       P.DATA_HORA_SAIDA_BRINQUEDO,
       V_ID_CLIENTE);
  
  END LOOP;
  
  END;
/

prompt
prompt Creating procedure PROC_PORTARIA_BRINQUEDO
prompt ==========================================
prompt
CREATE OR REPLACE PROCEDURE PROC_PORTARIA_BRINQUEDO(
                                                    
                                                    P_COD_RFID     IN VARCHAR2,
                                                    P_RESPOSTA     OUT VARCHAR2,
                                                    P_ID_BRINQUEDO IN NUMBER
                                                    
                                                    ) IS

  --
  V_ID_DISPOSITIVO              NUMBER;
  V_ID_STATUS_CLIENTE           NUMBER;
  V_QTD_MAX_PESSOAS_FILA_FISICA NUMBER;

  --
BEGIN

  --
  DECLARE
    -- VERIFICA SE O DISPOSITIVO É VÁLIDO
    V_COUNT NUMBER := 0;
  BEGIN
    V_COUNT := FUNC_VALIDA_DISPOSITIVO(P_COD_RFID);
    --
    IF (V_COUNT = 0) THEN
      P_RESPOSTA := 'DISPOSITIVO INVÁLIDO';
      RETURN;
    END IF;
    --
  END;
  --
  SELECT D.ID_DISPOSITIVO
    INTO V_ID_DISPOSITIVO
    FROM DISPOSITIVO D
   WHERE D.ID_RFID = P_COD_RFID;
  --
  SELECT F.ID_STATUS_CLIENTE
    INTO V_ID_STATUS_CLIENTE
    FROM FILA F
   WHERE F.ID_DISPOSITIVO = V_ID_DISPOSITIVO;
  ------------------------------------------------------------
  -- 1 - REL PRIMARIO                                       --
  -- 2 - AGUARDANDO BRINQUEDO                               --
  -- 3 - BRINQUEDO AGUARDANDO CLIENTE                       --
  -- 4 - TEMPO EXPIRADO                                     --
  -- 5 - CLIENTE NA FILA FISICA                             --
  -- 6 - CLIENTE NO BRINQUEDO                               --
  -- 7 - SAIO DO BRINQUEDO                                  --
  ------------------------------------------------------------

  IF (V_ID_STATUS_CLIENTE IN (1, 4, 7)) THEN
    --
    SELECT B.QTD_MAX_PESSOAS_FILA_FISICA
      INTO V_QTD_MAX_PESSOAS_FILA_FISICA
      FROM BRINQUEDO B
     WHERE B.ID_BRINQUEDO = P_ID_BRINQUEDO;
    --
    IF (FUNC_QTDE_CLIENTE_FILA_TOTAL(P_ID_BRINQUEDO) >
       V_QTD_MAX_PESSOAS_FILA_FISICA) THEN
      --
      UPDATE FILA F
         SET F.ID_BRINQUEDO                   = P_ID_BRINQUEDO,
             F.ID_STATUS_CLIENTE              = 2, -- CLIENTE AGUARDANDO BRINQUEDO
             F.DATA_HORA_ENTRADA_FILA_VIRTUAL = SYSDATE,
             F.DATA_HOTA_ENVIO_MENSAGEM       = NULL,
             F.DATA_HORA_ENTRADA_FILA_FISICA  = NULL,
             F.DATA_HORA_SAIDA_BRINQUEDO      = NULL
      
       WHERE F.ID_DISPOSITIVO = V_ID_DISPOSITIVO;
      --
      P_RESPOSTA := 'PARABENS! VOCÊ ESTA CADASTRADO NA FILA DESTE BRINQUEDO, AGUARDE E EM BREVE SERÁ CHAMADO!';
      --
    ELSE
      --
      UPDATE FILA F
         SET ID_BRINQUEDO                    = P_ID_BRINQUEDO,
             ID_STATUS_CLIENTE               = 3, -- BRINQUEDO AGUARDANDO CLIENTE
             DATA_HORA_ENTRADA_FILA_VIRTUAL  = SYSDATE,
             F.DATA_HOTA_ENVIO_MENSAGEM      = NULL,
             F.DATA_HORA_ENTRADA_FILA_FISICA = NULL,
             F.DATA_HORA_SAIDA_BRINQUEDO     = NULL
       WHERE F.ID_DISPOSITIVO = V_ID_DISPOSITIVO;
      --
      P_RESPOSTA := 'PARABÉNS, SUA VEZ ESTÁ PRÓXIMA, PODE DIRIGIR-SE PARA A ENTRADA DO BRINQUEDO';
      --
    END IF;
    --
  ELSIF (V_ID_STATUS_CLIENTE IN (2, 3, 5, 6)) THEN
    -- TRATAR AS RESPOSTAS ****
    P_RESPOSTA := 'VOCÊ NÃO PODE SE CADASTRAR NA FILA';
    --
  END IF;
  --
  COMMIT;
  --
END;
/

prompt
prompt Creating procedure PROC_TEMPO_EXECUCAO
prompt ======================================
prompt
CREATE OR REPLACE PROCEDURE PROC_TEMPO_EXECUCAO(P_ID_BRINQUEDO NUMBER) IS

  QTD_REG                 NUMBER;
  V_MEDIA_TEMPO_BRINQUEDO NUMBER;
BEGIN
  --
  BEGIN
    --
    UPDATE HISTORICO_TEMPO_BRINQUEDO
       SET TEMPO_FIM = SYSDATE
     WHERE ID_BRINQUEDO = P_ID_BRINQUEDO
       AND TEMPO_FIM IS NULL;
    --
    INSERT INTO HISTORICO_TEMPO_BRINQUEDO
      (ID_HIST_TEMP_BRINQUEDO, ID_BRINQUEDO, TEMPO_INICIO, TEMPO_FIM)
    VALUES
      (SEQ_HTB.NEXTVAL, P_ID_BRINQUEDO, SYSDATE, NULL);
    --
  END;
  --
  SELECT COUNT(*)
    INTO QTD_REG
    FROM HISTORICO_TEMPO_BRINQUEDO HTB
   WHERE HTB.ID_BRINQUEDO = P_ID_BRINQUEDO;
  --
  IF (QTD_REG > 9) THEN
    --
    SELECT
    
     AVG((TO_NUMBER(TO_CHAR(HTB.TEMPO_FIM, 'HH')) * 3600 +
         TO_NUMBER(TO_CHAR(HTB.TEMPO_FIM, 'MI')) * 60 +
         TO_NUMBER(TO_CHAR(HTB.TEMPO_FIM, 'SS'))) -
         (TO_NUMBER(TO_CHAR(HTB.TEMPO_INICIO, 'HH')) * 3600 +
         TO_NUMBER(TO_CHAR(HTB.TEMPO_INICIO, 'MI')) * 60 +
         TO_NUMBER(TO_CHAR(HTB.TEMPO_INICIO, 'SS'))))
    
      INTO V_MEDIA_TEMPO_BRINQUEDO
    
      FROM HISTORICO_TEMPO_BRINQUEDO HTB
     WHERE HTB.ID_BRINQUEDO = P_ID_BRINQUEDO
       AND HTB.TEMPO_FIM IS NOT NULL;
    --
    UPDATE BRINQUEDO B
       SET B.TEMPO_EXECUCAO = V_MEDIA_TEMPO_BRINQUEDO
     WHERE B.ID_BRINQUEDO = P_ID_BRINQUEDO;
    --
    DELETE FROM HISTORICO_TEMPO_BRINQUEDO HTB
     WHERE HTB.ID_BRINQUEDO = P_ID_BRINQUEDO
       AND HTB.TEMPO_FIM IS NOT NULL;
    --
    UPDATE BRINQUEDO B
       SET B.QTD_MAX_PESSOAS_FILA_FISICA = (B.TEMPO_MENSAGEM /
                                           B.TEMPO_EXECUCAO) *
                                           B.QTD_PESSOAS_LEVA
     WHERE B.ID_BRINQUEDO = P_ID_BRINQUEDO;
    --
  END IF;
  --
  COMMIT;
  --
END PROC_TEMPO_EXECUCAO;
/

prompt
prompt Creating procedure PROC_TEMPO_FILA_BRINQUEDO
prompt ============================================
prompt
CREATE OR REPLACE PROCEDURE PROC_TEMPO_FILA_BRINQUEDO(P_ID_BRINQUEDO NUMBER) IS

  V_QTD_PESSOAS_LEVA             INTEGER;
  V_QTD_PESSOAS_FILA_TOTAL       INTEGER;
  V_TEMPO_EXECUCAO_BRINQUEDO_SEG INTEGER;
  RESULT_CALC_TEMPO_FILA         INTEGER;
BEGIN
  ------------------------------------------------------------
  -- 1 - REL PRIMARIO                                       --
  -- 2 - AGUARDANDO BRINQUEDO                               --
  -- 3 - BRINQUEDO AGUARDANDO CLIENTE                       --
  -- 4 - TEMPO EXPIRADO                                     --
  -- 5 - CLIENTE NA FILA FISICA                             --
  -- 6 - CLIENTE NO BRINQUEDO                               --
  -- 7 - SAIO DO BRINQUEDO                                  --
  ------------------------------------------------------------
  --
  SELECT B.QTD_PESSOAS_LEVA
    INTO V_QTD_PESSOAS_LEVA
    FROM BRINQUEDO B
   WHERE B.ID_BRINQUEDO = P_ID_BRINQUEDO;
  --
  V_QTD_PESSOAS_FILA_TOTAL := FUNC_QTDE_CLIENTE_FILA_TOTAL(P_ID_BRINQUEDO);
  --
  SELECT B.TEMPO_EXECUCAO
    INTO V_TEMPO_EXECUCAO_BRINQUEDO_SEG
    FROM BRINQUEDO B
   WHERE B.ID_BRINQUEDO = P_ID_BRINQUEDO;
  --
  RESULT_CALC_TEMPO_FILA := V_TEMPO_EXECUCAO_BRINQUEDO_SEG *
                            (V_QTD_PESSOAS_FILA_TOTAL / V_QTD_PESSOAS_LEVA);
  --
  UPDATE BRINQUEDO B
     SET B.ESTIMATIVA_TEMPO_FILA = RESULT_CALC_TEMPO_FILA
   WHERE B.ID_BRINQUEDO = P_ID_BRINQUEDO;
  --
  COMMIT;
  --
END PROC_TEMPO_FILA_BRINQUEDO;
/

prompt
prompt Creating procedure PROC_PRINCIPAL
prompt =================================
prompt
CREATE OR REPLACE PROCEDURE PROC_PRINCIPAL(P_ID_BRINQUEDO IN NUMBER) IS

  V_QTD_MAX_CLIENTE_FILA_FISICA NUMBER;
  --
  V_QTD_CLINTE_PRESENTE_X_CHEGAR NUMBER;
  --
  V_QTD_ENVIA_SMS NUMBER;
  --
BEGIN

  --
  PROC_EXPIRAR_CLIENTE(P_ID_BRINQUEDO);
  --
  PROC_HISTORICO_CLIENTE_BRINQ(P_ID_BRINQUEDO);
  --
  UPDATE FILA F
     SET F.ID_STATUS_CLIENTE = 7, F.DATA_HORA_SAIDA_BRINQUEDO = SYSDATE
   WHERE F.ID_BRINQUEDO = P_ID_BRINQUEDO
     AND F.ID_STATUS_CLIENTE = 6;
  --
  PROC_TEMPO_EXECUCAO(P_ID_BRINQUEDO);
  --
  PROC_TEMPO_FILA_BRINQUEDO(P_ID_BRINQUEDO);
  --
  SELECT B.QTD_MAX_PESSOAS_FILA_FISICA
    INTO V_QTD_MAX_CLIENTE_FILA_FISICA
    FROM BRINQUEDO B
   WHERE B.ID_BRINQUEDO = P_ID_BRINQUEDO;
  --
  SELECT COUNT(*)
    INTO V_QTD_CLINTE_PRESENTE_X_CHEGAR
    FROM FILA F
   WHERE F.ID_BRINQUEDO = P_ID_BRINQUEDO
     AND F.ID_STATUS_CLIENTE IN (3, 5);
  --
  V_QTD_ENVIA_SMS := V_QTD_MAX_CLIENTE_FILA_FISICA -
                     V_QTD_CLINTE_PRESENTE_X_CHEGAR;
  --
  IF (V_QTD_ENVIA_SMS > 0) THEN
    --
    PROC_ENVIA_SMS(P_ID_BRINQUEDO, V_QTD_ENVIA_SMS);
    --
  END IF;
  --

END;
/

prompt
prompt Creating procedure SECURE_DML
prompt =============================
prompt
CREATE OR REPLACE PROCEDURE secure_dml
IS
BEGIN
  IF TO_CHAR (SYSDATE, 'HH24:MI') NOT BETWEEN '08:00' AND '18:00'
        OR TO_CHAR (SYSDATE, 'DY') IN ('SAT', 'SUN') THEN
	RAISE_APPLICATION_ERROR (-20205,
		'You may only make changes during normal office hours');
  END IF;
END secure_dml;
/


spool off
