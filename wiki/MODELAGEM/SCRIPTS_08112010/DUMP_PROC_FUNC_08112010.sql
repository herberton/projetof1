--------------------------------------------------
-- Export file for user HR                      --
-- Created by RicardoMMO on 8/11/2010, 20:42:12 --
--------------------------------------------------

spool DUMP_PROC_FUNC_08112010.log

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


spool off
