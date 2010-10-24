---------------------------------------------------
-- Export file for user HR                       --
-- Created by RicardoMMO on 24/10/2010, 04:40:47 --
---------------------------------------------------

spool sequencesscript.log

prompt
prompt Creating sequence BAIRRO_SEQ
prompt ============================
prompt
create sequence HR.BAIRRO_SEQ
minvalue 1
maxvalue 999999999999999999999999999
start with 3
increment by 1
cache 2;

prompt
prompt Creating sequence CIDADE_SEQ
prompt ============================
prompt
create sequence HR.CIDADE_SEQ
minvalue 1
maxvalue 999999999999999999999999999
start with 10066
increment by 1
cache 2;

prompt
prompt Creating sequence FUNCIONALIDADE_SEQ
prompt ====================================
prompt
create sequence HR.FUNCIONALIDADE_SEQ
minvalue 1
maxvalue 999999999999999999999999999
start with 16
increment by 1
cache 2;

prompt
prompt Creating sequence LOGRADOURO_SEQ
prompt ================================
prompt
create sequence HR.LOGRADOURO_SEQ
minvalue 1
maxvalue 999999999999999999999999999
start with 11
increment by 1
cache 2;

prompt
prompt Creating sequence MODULO_SISTEMA_SEQ
prompt ====================================
prompt
create sequence HR.MODULO_SISTEMA_SEQ
minvalue 1
maxvalue 999999999999999999999999999
start with 13
increment by 1
cache 3;

prompt
prompt Creating sequence PERFIL_FUNCIONALIDADE_SEQ
prompt ===========================================
prompt
create sequence HR.PERFIL_FUNCIONALIDADE_SEQ
minvalue 1
maxvalue 999999999999999999999999999
start with 11
increment by 1
cache 5;

prompt
prompt Creating sequence PERFIL_SEQ
prompt ============================
prompt
create sequence HR.PERFIL_SEQ
minvalue 1
maxvalue 999999999999999999999999999
start with 20
increment by 1
cache 3;

prompt
prompt Creating sequence PERFIL_USUARIO_SEQ
prompt ====================================
prompt
create sequence HR.PERFIL_USUARIO_SEQ
minvalue 1
maxvalue 999999999999999999999999999
start with 1
increment by 1
cache 10;

prompt
prompt Creating sequence PESSOA_SEQ
prompt ============================
prompt
create sequence HR.PESSOA_SEQ
minvalue 1
maxvalue 999999999999999999999999999
start with 11
increment by 1
cache 2;

prompt
prompt Creating sequence SEQ_BRINQUEDO
prompt ===============================
prompt
create sequence HR.SEQ_BRINQUEDO
minvalue 1
maxvalue 999999999999999999999999999
start with 4
increment by 1
cache 20;

prompt
prompt Creating sequence SEQ_CATRACA
prompt =============================
prompt
create sequence HR.SEQ_CATRACA
minvalue 1
maxvalue 999999999999999999999999999
start with 22
increment by 1
cache 20;

prompt
prompt Creating sequence SEQ_CLIENTE
prompt =============================
prompt
create sequence HR.SEQ_CLIENTE
minvalue 1
maxvalue 999999999999999999999999999
start with 91
increment by 1
cache 20;

prompt
prompt Creating sequence SEQ_DISPOSITIVO
prompt =================================
prompt
create sequence HR.SEQ_DISPOSITIVO
minvalue 1
maxvalue 999999999999999999999999999
start with 24
increment by 1
cache 20;

prompt
prompt Creating sequence SEQ_FILA
prompt ==========================
prompt
create sequence HR.SEQ_FILA
minvalue 1
maxvalue 999999999999999999999999999
start with 1
increment by 1
cache 20;

prompt
prompt Creating sequence SEQ_HIST_CLIENTE_BRINQUEDO
prompt ============================================
prompt
create sequence HR.SEQ_HIST_CLIENTE_BRINQUEDO
minvalue 1
maxvalue 999999999999999999999999999
start with 1
increment by 1
cache 20;

prompt
prompt Creating sequence SEQ_HISTORICO_CLIENTE
prompt =======================================
prompt
create sequence HR.SEQ_HISTORICO_CLIENTE
minvalue 1
maxvalue 999999999999999999999999999
start with 28
increment by 1
cache 20;

prompt
prompt Creating sequence SEQ_STATUS_BRINQUEDO
prompt ======================================
prompt
create sequence HR.SEQ_STATUS_BRINQUEDO
minvalue 1
maxvalue 999999999999999999999999999
start with 2
increment by 1
cache 20;

prompt
prompt Creating sequence SEQ_STATUS_CLIENTE
prompt ====================================
prompt
create sequence HR.SEQ_STATUS_CLIENTE
minvalue 1
maxvalue 999999999999999999999999999
start with 22
increment by 1
cache 20;

prompt
prompt Creating sequence SEQ_STATUS_DISPOSITIVO
prompt ========================================
prompt
create sequence HR.SEQ_STATUS_DISPOSITIVO
minvalue 1
maxvalue 999999999999999999999999999
start with 2
increment by 1
cache 20;

prompt
prompt Creating sequence SEQ_TERMINAL_CONSULTA
prompt =======================================
prompt
create sequence HR.SEQ_TERMINAL_CONSULTA
minvalue 1
maxvalue 999999999999999999999999999
start with 1
increment by 1
cache 20;

prompt
prompt Creating sequence TABELA_AUXILIAR_SEQ
prompt =====================================
prompt
create sequence HR.TABELA_AUXILIAR_SEQ
minvalue 1
maxvalue 999999999999999999999999999
start with 16
increment by 1
cache 2;

prompt
prompt Creating sequence USUARIO_SEQ
prompt =============================
prompt
create sequence HR.USUARIO_SEQ
minvalue 1
maxvalue 999999999999999999999999999
start with 7
increment by 1
cache 5;

prompt
prompt Creating sequence VALOR_TAB_AUX_SEQ
prompt ===================================
prompt
create sequence HR.VALOR_TAB_AUX_SEQ
minvalue 1
maxvalue 999999999999999999999999999
start with 122
increment by 1
cache 10;


spool off
