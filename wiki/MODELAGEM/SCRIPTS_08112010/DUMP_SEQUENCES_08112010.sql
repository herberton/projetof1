--------------------------------------------------
-- Export file for user HR                      --
-- Created by RicardoMMO on 8/11/2010, 20:42:56 --
--------------------------------------------------

spool DUMP_SEQUENCES_08112010.log

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


spool off
