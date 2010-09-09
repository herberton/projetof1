-- Generated by Oracle SQL Developer Data Modeler Version: 2.0.0 Build: 584
--   at:        2010-08-07 16:45:08
--   site:      Oracle Database 10g
--   type:      Oracle Database 10g



CREATE TABLE Brinquedo 
    ( 
     ID_BRINQUEDO NUMBER (4)  NOT NULL , 
     NOME VARCHAR2 (50)  NOT NULL , 
     OBSERVACAO VARCHAR2 (100) , 
     TEMPO_MENSAGEM INTEGER , 
     ESTIMATIVA_TEMPO_FILA INTEGER , 
     QTD_PESSOAS_FILA_FISICA INTEGER , 
     QTD_MAX_PESSOAS_FILA_FISICA INTEGER , 
     ID_STATUS_BRINQUEDO NUMBER (3) 
    ) LOGGING 
;


CREATE UNIQUE INDEX PK_BRINQUEDOX ON Brinquedo 
    ( 
     ID_BRINQUEDO ASC 
    ) 
;
CREATE INDEX FK_STATUSBRINQUEDO_BRINQUEDOX ON Brinquedo 
    ( 
     ID_STATUS_BRINQUEDO ASC 
    ) 
;

ALTER TABLE Brinquedo 
    ADD CONSTRAINT PK_BRINQUEDO PRIMARY KEY ( ID_BRINQUEDO ) ;


CREATE TABLE Catraca 
    ( 
     ID_CATRACA NUMBER (4)  NOT NULL , 
     DESCRICAO VARCHAR2 (100)  NOT NULL , 
     LOCALIZACAO VARCHAR2 (150) , 
     ID_BRINQUEDO NUMBER (4) 
    ) LOGGING 
;


CREATE UNIQUE INDEX PK_CATRACAX ON Catraca 
    ( 
     ID_CATRACA ASC 
    ) 
;
CREATE INDEX FK_BRINQUEDO_CATRACAX ON Catraca 
    ( 
     ID_BRINQUEDO ASC 
    ) 
;

ALTER TABLE Catraca 
    ADD CONSTRAINT PK_CATRACA PRIMARY KEY ( ID_CATRACA ) ;


CREATE TABLE Cliente 
    ( 
     ID_CLIENTE NUMBER (7)  NOT NULL , 
     NOME VARCHAR2 (50)  NOT NULL , 
     DATA_NASCIMENTO DATE , 
     SEXO CHAR (1)  NOT NULL CHECK ( SEXO IN ('F' , 'M' )) , 
     CPF VARCHAR2 (11) , 
     RG VARCHAR2 (9) , 
     ALTURA FLOAT , 
     QTD_VISITAS INTEGER 
    ) LOGGING 
;


CREATE UNIQUE INDEX PK_CLIENTEX ON Cliente 
    ( 
     ID_CLIENTE ASC 
    ) 
;

ALTER TABLE Cliente 
    ADD CONSTRAINT PK_CLIENTE PRIMARY KEY ( ID_CLIENTE ) ;


CREATE TABLE Dispositivo 
    ( 
     ID_DISPOSITIVO NUMBER (4)  NOT NULL , 
     ID_STATUS_DISPOSITIVO NUMBER (3) , 
     IP VARCHAR2 (15) , 
     ID_CLIENTE NUMBER (7) , 
     DATA_CADASTRO DATE  NOT NULL 
    ) LOGGING 
;


CREATE UNIQUE INDEX PK_DISPOSITIVOX ON Dispositivo 
    ( 
     ID_DISPOSITIVO ASC 
    ) 
;
CREATE UNIQUE INDEX UK_CLIENTEX ON Dispositivo 
    ( 
     ID_CLIENTE ASC 
    ) 
;
CREATE INDEX FK_CLIENTE_DISPOSITIVOX ON Dispositivo 
    ( 
     ID_CLIENTE ASC 
    ) 
;
CREATE INDEX FK_DISPOSITIVO_STATUSDISPOX ON Dispositivo 
    ( 
     ID_STATUS_DISPOSITIVO ASC 
    ) 
;

ALTER TABLE Dispositivo 
    ADD CONSTRAINT PK_DISPOSITIVO PRIMARY KEY ( ID_DISPOSITIVO ) ;

ALTER TABLE Dispositivo 
    ADD CONSTRAINT UK_CLIENTE UNIQUE ( ID_CLIENTE ) 
;


CREATE TABLE Fila 
    ( 
     ID_FILA NUMBER (7)  NOT NULL , 
     ID_DISPOSITIVO NUMBER (4)  NOT NULL , 
     ID_BRINQUEDO NUMBER (4)  NOT NULL , 
     ID_STATUS_CLIENTE NUMBER (3) , 
     DATA_HORA_ENTRADA_FILA_VIRTUAL DATE , 
     DATA_HOTA_ENVIO_MENSAGEM DATE , 
     DATA_HORA_ENTRADA_FILA_FISICA DATE , 
     DATA_HORA_SAIDA_BRINQUEDO DATE 
    ) LOGGING 
;


CREATE UNIQUE INDEX PK_FILAX ON Fila 
    ( 
     ID_FILA ASC 
    ) 
;
CREATE UNIQUE INDEX UK_BRINQUEDO_DISPOSITIVOX ON Fila 
    ( 
     ID_DISPOSITIVO ASC , 
     ID_BRINQUEDO ASC 
    ) 
;
CREATE INDEX FK_FILA_BRINQUEDOX ON Fila 
    ( 
     ID_BRINQUEDO ASC 
    ) 
;
CREATE INDEX FK_FILA_DISPOSITIVOX ON Fila 
    ( 
     ID_DISPOSITIVO ASC 
    ) 
;
CREATE INDEX FK_STATUSCLIENTE_FILAX ON Fila 
    ( 
     ID_STATUS_CLIENTE ASC 
    ) 
;

ALTER TABLE Fila 
    ADD CONSTRAINT PK_FILA PRIMARY KEY ( ID_FILA ) ;

ALTER TABLE Fila 
    ADD CONSTRAINT UK_BRINQUEDO_DISPOSITIVO UNIQUE ( ID_DISPOSITIVO , ID_BRINQUEDO ) 
;


CREATE TABLE Historico_Cliente 
    ( 
     ID_HISTORICO_CLINTE NUMBER (7)  NOT NULL , 
     DATA_HORA_ENTRADA_PARQUE DATE  NOT NULL , 
     DATA_HORA_SAIDA_PARQUE DATE , 
     OBSERVACAO VARCHAR2 (150) , 
     ID_DISPOSITIVO NUMBER (4) , 
     ID_STATUS_DISPOSITIVO NUMBER (3) , 
     ID_CLIENTE NUMBER (7) 
    ) LOGGING 
;


CREATE UNIQUE INDEX PK_HISTCLIENTEX ON Historico_Cliente 
    ( 
     ID_HISTORICO_CLINTE ASC 
    ) 
;
CREATE INDEX FK_CLIENTE_HISTCLIENTEX ON Historico_Cliente 
    ( 
     ID_CLIENTE ASC 
    ) 
;
CREATE INDEX FK_HISTCLIENT_DISPOSITIVOX ON Historico_Cliente 
    ( 
     ID_DISPOSITIVO ASC 
    ) 
;
CREATE INDEX FK_HISTCLIENT_STATUSDISPOX ON Historico_Cliente 
    ( 
     ID_STATUS_DISPOSITIVO ASC 
    ) 
;

ALTER TABLE Historico_Cliente 
    ADD CONSTRAINT PK_HISTCLIENTE PRIMARY KEY ( ID_HISTORICO_CLINTE ) ;


CREATE TABLE Historico_Cliente_Brinquedo 
    ( 
     ID_HISTORICO_CLIENTE_BRINQUEDO NUMBER (7)  NOT NULL , 
     ID_BRINQUEDO NUMBER (4) , 
     DATA_HORA_ENTRADA_FILA_VIRTUAL DATE , 
     DATA_HORA_ENVIO_MENSAGEM DATE , 
     DATA_HORA_ENTRADA_FILA_FISICA DATE , 
     DATA_HORA_SAIDA_BRINQUEDO DATE , 
     ID_CLIENTE NUMBER (7) 
    ) LOGGING 
;


CREATE UNIQUE INDEX PK_HISTCLIENTEBRINQX ON Historico_Cliente_Brinquedo 
    ( 
     ID_HISTORICO_CLIENTE_BRINQUEDO ASC 
    ) 
;
CREATE INDEX FK_CLIENTE_HISTCLIENTBRINQX ON Historico_Cliente_Brinquedo 
    ( 
     ID_CLIENTE ASC 
    ) 
;
CREATE INDEX FK_HISTCLIENTBRINQ_BRINQUEDOX ON Historico_Cliente_Brinquedo 
    ( 
     ID_BRINQUEDO ASC 
    ) 
;

ALTER TABLE Historico_Cliente_Brinquedo 
    ADD CONSTRAINT PK_HISTCLIENTEBRINQ PRIMARY KEY ( ID_HISTORICO_CLIENTE_BRINQUEDO ) ;


CREATE TABLE Status_Brinquedo 
    ( 
     ID_STATUS_BRINQUEDO NUMBER (3)  NOT NULL , 
     DESCRICAO VARCHAR2 (100)  NOT NULL 
    ) LOGGING 
;


CREATE UNIQUE INDEX PK_STATUSBRINQUEDOX ON Status_Brinquedo 
    ( 
     ID_STATUS_BRINQUEDO ASC 
    ) 
;

ALTER TABLE Status_Brinquedo 
    ADD CONSTRAINT PK_STATUSBRINQUEDO PRIMARY KEY ( ID_STATUS_BRINQUEDO ) ;


CREATE TABLE Status_Cliente 
    ( 
     ID_STATUS_CLIENTE NUMBER (3)  NOT NULL , 
     DESCRICAO VARCHAR2 (100)  NOT NULL 
    ) LOGGING 
;


CREATE UNIQUE INDEX PK_STATUSCLIENTEX ON Status_Cliente 
    ( 
     ID_STATUS_CLIENTE ASC 
    ) 
;

ALTER TABLE Status_Cliente 
    ADD CONSTRAINT PK_STATUSCLIENTE PRIMARY KEY ( ID_STATUS_CLIENTE ) ;


CREATE TABLE Status_Dispositivo 
    ( 
     ID_STATUS_DISPOSITIVO NUMBER (3)  NOT NULL , 
     DESCRICAO VARCHAR2 (100)  NOT NULL 
    ) LOGGING 
;


CREATE UNIQUE INDEX PK_STATUSDISPOX ON Status_Dispositivo 
    ( 
     ID_STATUS_DISPOSITIVO ASC 
    ) 
;

ALTER TABLE Status_Dispositivo 
    ADD CONSTRAINT PK_STATUSDISPO PRIMARY KEY ( ID_STATUS_DISPOSITIVO ) ;


CREATE TABLE Terminal_Consulta 
    ( 
     ID_TERMINAL_CONSULTA NUMBER (4)  NOT NULL , 
     DESCRICAO VARCHAR2 (100) , 
     LOCALIZACAO VARCHAR2 (150) , 
     IP VARCHAR2 (15) , 
     HOST_NAME VARCHAR2 (30) 
    ) LOGGING 
;


CREATE UNIQUE INDEX PK_TERMINALCONSULTAX ON Terminal_Consulta 
    ( 
     ID_TERMINAL_CONSULTA ASC 
    ) 
;

ALTER TABLE Terminal_Consulta 
    ADD CONSTRAINT PK_TERMINALCONSULTA PRIMARY KEY ( ID_TERMINAL_CONSULTA ) ;



ALTER TABLE Catraca 
    ADD CONSTRAINT FK_BRINQUEDO_CATRACA FOREIGN KEY 
    ( 
     ID_BRINQUEDO
    ) 
    REFERENCES Brinquedo 
    ( 
     ID_BRINQUEDO
    ) 
    ON DELETE SET NULL 
    NOT DEFERRABLE 
;


ALTER TABLE Dispositivo 
    ADD CONSTRAINT FK_CLIENTE_DISPOSITIVO FOREIGN KEY 
    ( 
     ID_CLIENTE
    ) 
    REFERENCES Cliente 
    ( 
     ID_CLIENTE
    ) 
    ON DELETE SET NULL 
    NOT DEFERRABLE 
;


ALTER TABLE Historico_Cliente_Brinquedo 
    ADD CONSTRAINT FK_CLIENTE_HISTCLIENTBRINQ FOREIGN KEY 
    ( 
     ID_CLIENTE
    ) 
    REFERENCES Cliente 
    ( 
     ID_CLIENTE
    ) 
    ON DELETE SET NULL 
    NOT DEFERRABLE 
;


ALTER TABLE Historico_Cliente 
    ADD CONSTRAINT FK_CLIENTE_HISTCLIENTE FOREIGN KEY 
    ( 
     ID_CLIENTE
    ) 
    REFERENCES Cliente 
    ( 
     ID_CLIENTE
    ) 
    ON DELETE SET NULL 
    NOT DEFERRABLE 
;


ALTER TABLE Dispositivo 
    ADD CONSTRAINT FK_DISPOSITIVO_STATUSDISPO FOREIGN KEY 
    ( 
     ID_STATUS_DISPOSITIVO
    ) 
    REFERENCES Status_Dispositivo 
    ( 
     ID_STATUS_DISPOSITIVO
    ) 
    ON DELETE SET NULL 
    NOT DEFERRABLE 
;


ALTER TABLE Fila 
    ADD CONSTRAINT FK_FILA_BRINQUEDO FOREIGN KEY 
    ( 
     ID_BRINQUEDO
    ) 
    REFERENCES Brinquedo 
    ( 
     ID_BRINQUEDO
    ) 
    ON DELETE CASCADE 
    NOT DEFERRABLE 
;


ALTER TABLE Fila 
    ADD CONSTRAINT FK_FILA_DISPOSITIVO FOREIGN KEY 
    ( 
     ID_DISPOSITIVO
    ) 
    REFERENCES Dispositivo 
    ( 
     ID_DISPOSITIVO
    ) 
    ON DELETE CASCADE 
    NOT DEFERRABLE 
;


ALTER TABLE Historico_Cliente_Brinquedo 
    ADD CONSTRAINT FK_HISTCLIENTBRINQ_BRINQUEDO FOREIGN KEY 
    ( 
     ID_BRINQUEDO
    ) 
    REFERENCES Brinquedo 
    ( 
     ID_BRINQUEDO
    ) 
    ON DELETE SET NULL 
    NOT DEFERRABLE 
;


ALTER TABLE Historico_Cliente 
    ADD CONSTRAINT FK_HISTCLIENT_DISPOSITIVO FOREIGN KEY 
    ( 
     ID_DISPOSITIVO
    ) 
    REFERENCES Dispositivo 
    ( 
     ID_DISPOSITIVO
    ) 
    ON DELETE SET NULL 
    NOT DEFERRABLE 
;


ALTER TABLE Historico_Cliente 
    ADD CONSTRAINT FK_HISTCLIENT_STATUSDISPO FOREIGN KEY 
    ( 
     ID_STATUS_DISPOSITIVO
    ) 
    REFERENCES Status_Dispositivo 
    ( 
     ID_STATUS_DISPOSITIVO
    ) 
    ON DELETE SET NULL 
    NOT DEFERRABLE 
;


ALTER TABLE Brinquedo 
    ADD CONSTRAINT FK_STATUSBRINQUEDO_BRINQUEDO FOREIGN KEY 
    ( 
     ID_STATUS_BRINQUEDO
    ) 
    REFERENCES Status_Brinquedo 
    ( 
     ID_STATUS_BRINQUEDO
    ) 
    ON DELETE SET NULL 
    NOT DEFERRABLE 
;


ALTER TABLE Fila 
    ADD CONSTRAINT FK_STATUSCLIENTE_FILA FOREIGN KEY 
    ( 
     ID_STATUS_CLIENTE
    ) 
    REFERENCES Status_Cliente 
    ( 
     ID_STATUS_CLIENTE
    ) 
    ON DELETE SET NULL 
    NOT DEFERRABLE 
;

CREATE SEQUENCE SEQ_BRINQUEDO 
    START WITH 1 
    INCREMENT BY 1 
    MINVALUE 1 
    
;

CREATE SEQUENCE SEQ_CATRACA 
    START WITH 1 
    INCREMENT BY 1 
    MINVALUE 1 
    
;

CREATE SEQUENCE SEQ_CLIENTE 
    START WITH 1 
    INCREMENT BY 1 
    MINVALUE 1 
    
;

CREATE SEQUENCE SEQ_DISPOSITIVO 
    START WITH 1 
    INCREMENT BY 1 
    MINVALUE 1 
    
;

CREATE SEQUENCE SEQ_FILA 
    START WITH 1 
    INCREMENT BY 1 
    MINVALUE 1 
    
;

CREATE SEQUENCE SEQ_HISTORICO_CLIENTE 
    START WITH 1 
    INCREMENT BY 1 
    MINVALUE 1 
    
;

CREATE SEQUENCE SEQ_HIST_CLIENTE_BRINQUEDO 
    START WITH 1 
    INCREMENT BY 1 
    MINVALUE 1 
    
;

CREATE SEQUENCE SEQ_STATUS_BRINQUEDO 
    START WITH 1 
    INCREMENT BY 1 
    MINVALUE 1 
    
;

CREATE SEQUENCE SEQ_STATUS_CLIENTE 
    START WITH 1 
    INCREMENT BY 1 
    MINVALUE 1 
    
;

CREATE SEQUENCE SEQ_STATUS_DISPOSITIVO 
    START WITH 1 
    INCREMENT BY 1 
    MINVALUE 1 
    
;

CREATE SEQUENCE SEQ_TERMINAL_CONSULTA 
    START WITH 1 
    INCREMENT BY 1 
    MINVALUE 1 
    
;


    
    
    
    
    
    
    
    
    
    
    

-- Oracle SQL Developer Data Modeler Summary Report: 
-- 
-- CREATE TABLE                            11
-- CREATE INDEX                            25
-- ALTER TABLE                             25
-- CREATE VIEW                              0
-- CREATE PROCEDURE                         0
-- CREATE TRIGGER                           0
-- CREATE STRUCTURED TYPE                   0
-- CREATE COLLECTION TYPE                   0
-- CREATE CLUSTER                           0
-- CREATE CONTEXT                           0
-- CREATE DATABASE                          0
-- CREATE DIMENSION                         0
-- CREATE DIRECTORY                         0
-- CREATE DISK GROUP                        0
-- CREATE ROLE                              0
-- CREATE ROLLBACK SEGMENT                  0
-- CREATE SEQUENCE                         11
-- CREATE SNAPSHOT                          0
-- CREATE SYNONYM                           0
-- CREATE TABLESPACE                        0
-- CREATE USER                              0
-- 
-- DROP TABLESPACE                          0
-- DROP DATABASE                            0
-- 
-- ERRORS                                   0
-- WARNINGS                                 0
