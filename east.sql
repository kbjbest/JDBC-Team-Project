--------------------------------------------------------
--  ������ ������ - ������-12��-07-2016   
--------------------------------------------------------
DROP TABLE "EAST"."TB_AVIATIONINFO" cascade constraints;
DROP TABLE "EAST"."TB_MEMBER" cascade constraints;
DROP TABLE "EAST"."TB_RESERVATIONS" cascade constraints;
DROP SEQUENCE "EAST"."AVIATIONINFO_SEQ";
DROP SEQUENCE "EAST"."MEMBER_SEQ";
DROP SEQUENCE "EAST"."RESERVATIONS_SEQ";
--------------------------------------------------------
--  DDL for Table TB_AVIATIONINFO
--------------------------------------------------------

  CREATE TABLE "EAST"."TB_AVIATIONINFO" 
   (	"PLANENO" NUMBER, 
	"PLANECODE" VARCHAR2(20 BYTE), 
	"PLANENAME" VARCHAR2(10 BYTE), 
	"STARTTIME" VARCHAR2(10 BYTE), 
	"ARRIVALTIME" VARCHAR2(10 BYTE), 
	"STARTLOC" VARCHAR2(20 BYTE), 
	"ARRIVALLOC" VARCHAR2(20 BYTE)
   ) SEGMENT CREATION IMMEDIATE 
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 NOCOMPRESS LOGGING
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "USERS" ;
--------------------------------------------------------
--  DDL for Table TB_MEMBER
--------------------------------------------------------

  CREATE TABLE "EAST"."TB_MEMBER" 
   (	"MEMBERNO" NUMBER, 
	"ID" VARCHAR2(15 BYTE), 
	"PW" VARCHAR2(15 BYTE), 
	"NAME" VARCHAR2(10 BYTE), 
	"ADDRESS" VARCHAR2(50 BYTE), 
	"EMAIL" VARCHAR2(25 BYTE), 
	"PHONE" VARCHAR2(15 BYTE)
   ) SEGMENT CREATION IMMEDIATE 
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 NOCOMPRESS LOGGING
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "USERS" ;
--------------------------------------------------------
--  DDL for Table TB_RESERVATIONS
--------------------------------------------------------

  CREATE TABLE "EAST"."TB_RESERVATIONS" 
   (	"LISTNO" NUMBER, 
	"MEMBERNO" NUMBER, 
	"PLANENO" NUMBER, 
	"SEAT" VARCHAR2(10 BYTE), 
	"STARTDATE" DATE, 
	"ARRIVALDATE" DATE, 
	"CANCEL" CHAR(1 BYTE)
   ) SEGMENT CREATION IMMEDIATE 
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 NOCOMPRESS LOGGING
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "USERS" ;
--------------------------------------------------------
--  DDL for Sequence AVIATIONINFO_SEQ
--------------------------------------------------------

   CREATE SEQUENCE  "EAST"."AVIATIONINFO_SEQ"  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 21 CACHE 20 NOORDER  NOCYCLE ;
--------------------------------------------------------
--  DDL for Sequence MEMBER_SEQ
--------------------------------------------------------

   CREATE SEQUENCE  "EAST"."MEMBER_SEQ"  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 21 CACHE 20 NOORDER  NOCYCLE ;
--------------------------------------------------------
--  DDL for Sequence RESERVATIONS_SEQ
--------------------------------------------------------

   CREATE SEQUENCE  "EAST"."RESERVATIONS_SEQ"  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 21 CACHE 20 NOORDER  NOCYCLE ;
REM INSERTING into EAST.TB_AVIATIONINFO
SET DEFINE OFF;
Insert into EAST.TB_AVIATIONINFO (PLANENO,PLANECODE,PLANENAME,STARTTIME,ARRIVALTIME,STARTLOC,ARRIVALLOC) values (1,'A00000230','A','00:00','02:30','����','����');
Insert into EAST.TB_AVIATIONINFO (PLANENO,PLANECODE,PLANENAME,STARTTIME,ARRIVALTIME,STARTLOC,ARRIVALLOC) values (2,'A05300730','A','05:30','07:30','�ѱ�','�̱�');
Insert into EAST.TB_AVIATIONINFO (PLANENO,PLANECODE,PLANENAME,STARTTIME,ARRIVALTIME,STARTLOC,ARRIVALLOC) values (3,'A10301230','A','10:30','12:30','�ѱ�','�߱�');
Insert into EAST.TB_AVIATIONINFO (PLANENO,PLANECODE,PLANENAME,STARTTIME,ARRIVALTIME,STARTLOC,ARRIVALLOC) values (4,'A15301730','A','15:30','17:30','�ѱ�','�Ϻ�');
Insert into EAST.TB_AVIATIONINFO (PLANENO,PLANECODE,PLANENAME,STARTTIME,ARRIVALTIME,STARTLOC,ARRIVALLOC) values (5,'A20302230','A','20:30','22:30','�ѱ�','����');
Insert into EAST.TB_AVIATIONINFO (PLANENO,PLANECODE,PLANENAME,STARTTIME,ARRIVALTIME,STARTLOC,ARRIVALLOC) values (6,'D00000230','D','00:00','02:30','����','����');
Insert into EAST.TB_AVIATIONINFO (PLANENO,PLANECODE,PLANENAME,STARTTIME,ARRIVALTIME,STARTLOC,ARRIVALLOC) values (7,'D05300730','D','05:30','07:30','�̱�','�ѱ�');
Insert into EAST.TB_AVIATIONINFO (PLANENO,PLANECODE,PLANENAME,STARTTIME,ARRIVALTIME,STARTLOC,ARRIVALLOC) values (8,'D10301230','D','10:30','12:30','�߱�','�ѱ�');
Insert into EAST.TB_AVIATIONINFO (PLANENO,PLANECODE,PLANENAME,STARTTIME,ARRIVALTIME,STARTLOC,ARRIVALLOC) values (9,'D15301730','D','15:30','17:30','�Ϻ�','�Ѻ�');
Insert into EAST.TB_AVIATIONINFO (PLANENO,PLANECODE,PLANENAME,STARTTIME,ARRIVALTIME,STARTLOC,ARRIVALLOC) values (10,'D20302230','D','20:30','22:30','����','�ѱ�');
Insert into EAST.TB_AVIATIONINFO (PLANENO,PLANECODE,PLANENAME,STARTTIME,ARRIVALTIME,STARTLOC,ARRIVALLOC) values (11,'J00000230','J','00:00','02:30','�̱�','�߱�');
Insert into EAST.TB_AVIATIONINFO (PLANENO,PLANECODE,PLANENAME,STARTTIME,ARRIVALTIME,STARTLOC,ARRIVALLOC) values (12,'J05300730','J','05:30','07:30','�߱�','�Ϻ�');
Insert into EAST.TB_AVIATIONINFO (PLANENO,PLANECODE,PLANENAME,STARTTIME,ARRIVALTIME,STARTLOC,ARRIVALLOC) values (13,'J10301230','J','10:30','12:30','�Ϻ�','����');
Insert into EAST.TB_AVIATIONINFO (PLANENO,PLANECODE,PLANENAME,STARTTIME,ARRIVALTIME,STARTLOC,ARRIVALLOC) values (14,'J15301730','J','15:30','17:30','����','�ѱ�');
Insert into EAST.TB_AVIATIONINFO (PLANENO,PLANECODE,PLANENAME,STARTTIME,ARRIVALTIME,STARTLOC,ARRIVALLOC) values (15,'J20302230','J','20:30','22:30','����','�λ�');
Insert into EAST.TB_AVIATIONINFO (PLANENO,PLANECODE,PLANENAME,STARTTIME,ARRIVALTIME,STARTLOC,ARRIVALLOC) values (16,'E00000230','E','00:00','02:30','�߱�','�̱�');
Insert into EAST.TB_AVIATIONINFO (PLANENO,PLANECODE,PLANENAME,STARTTIME,ARRIVALTIME,STARTLOC,ARRIVALLOC) values (17,'E05300730','E','05:30','07:30','�Ϻ�','�߱�');
Insert into EAST.TB_AVIATIONINFO (PLANENO,PLANECODE,PLANENAME,STARTTIME,ARRIVALTIME,STARTLOC,ARRIVALLOC) values (18,'E10301230','E','10:30','12:30','����','�Ϻ�');
Insert into EAST.TB_AVIATIONINFO (PLANENO,PLANECODE,PLANENAME,STARTTIME,ARRIVALTIME,STARTLOC,ARRIVALLOC) values (19,'E15301730','E','15:30','17:30','�ѱ�','����');
Insert into EAST.TB_AVIATIONINFO (PLANENO,PLANECODE,PLANENAME,STARTTIME,ARRIVALTIME,STARTLOC,ARRIVALLOC) values (20,'E20302230','E','20:30','22:30','�λ�','����');
REM INSERTING into EAST.TB_MEMBER
SET DEFINE OFF;
Insert into EAST.TB_MEMBER (MEMBERNO,ID,PW,NAME,ADDRESS,EMAIL,PHONE) values (1,'admin','admin','������','�����','admin@admin.com','010-1234-5678');
Insert into EAST.TB_MEMBER (MEMBERNO,ID,PW,NAME,ADDRESS,EMAIL,PHONE) values (2,'aht','1234','������','��õ','test@test.com','010-1234-5678');
Insert into EAST.TB_MEMBER (MEMBERNO,ID,PW,NAME,ADDRESS,EMAIL,PHONE) values (3,'ksr','1234','�輺��','����','test@test.com','010-1234-5678');
Insert into EAST.TB_MEMBER (MEMBERNO,ID,PW,NAME,ADDRESS,EMAIL,PHONE) values (4,'kbj','1234','�����','���','test@test.com','010-1234-5678');
Insert into EAST.TB_MEMBER (MEMBERNO,ID,PW,NAME,ADDRESS,EMAIL,PHONE) values (5,'hwh','1234','�Կ���','��õ','test@test.com','010-1234-5678');
Insert into EAST.TB_MEMBER (MEMBERNO,ID,PW,NAME,ADDRESS,EMAIL,PHONE) values (6,'lyg','1234','�̿���','����','test@test.com','010-1234-5678');
Insert into EAST.TB_MEMBER (MEMBERNO,ID,PW,NAME,ADDRESS,EMAIL,PHONE) values (7,'ghj','1234','������','�ϻ�','test@test.com','010-1234-5678');
Insert into EAST.TB_MEMBER (MEMBERNO,ID,PW,NAME,ADDRESS,EMAIL,PHONE) values (8,'hsy','1234','Ȳ����','����','test@test.com','010-1234-5678');
Insert into EAST.TB_MEMBER (MEMBERNO,ID,PW,NAME,ADDRESS,EMAIL,PHONE) values (9,'test1','1234','�׽���1','�����','test@test.com','010-1234-5678');
Insert into EAST.TB_MEMBER (MEMBERNO,ID,PW,NAME,ADDRESS,EMAIL,PHONE) values (10,'test2','1234','�׽���2','����','test@test.com','010-1234-5678');
Insert into EAST.TB_MEMBER (MEMBERNO,ID,PW,NAME,ADDRESS,EMAIL,PHONE) values (11,'test3','1234','�׽���3','���󳲵�','test@test.com','010-1234-5678');
Insert into EAST.TB_MEMBER (MEMBERNO,ID,PW,NAME,ADDRESS,EMAIL,PHONE) values (12,'aaa','aaa','Ʈ���ÿ���','���','aaa@aaa.com','010-1234-5678');
REM INSERTING into EAST.TB_RESERVATIONS
SET DEFINE OFF;
Insert into EAST.TB_RESERVATIONS (LISTNO,MEMBERNO,PLANENO,SEAT,STARTDATE,ARRIVALDATE,CANCEL) values (1,2,1,'4-C',to_date('16/11/30','RR/MM/DD'),to_date('16/11/30','RR/MM/DD'),'n');
Insert into EAST.TB_RESERVATIONS (LISTNO,MEMBERNO,PLANENO,SEAT,STARTDATE,ARRIVALDATE,CANCEL) values (2,2,4,'5-A',to_date('16/12/03','RR/MM/DD'),to_date('16/12/03','RR/MM/DD'),'n');
Insert into EAST.TB_RESERVATIONS (LISTNO,MEMBERNO,PLANENO,SEAT,STARTDATE,ARRIVALDATE,CANCEL) values (3,2,20,'2-D',to_date('16/12/10','RR/MM/DD'),to_date('16/12/10','RR/MM/DD'),'n');
Insert into EAST.TB_RESERVATIONS (LISTNO,MEMBERNO,PLANENO,SEAT,STARTDATE,ARRIVALDATE,CANCEL) values (4,2,20,'2-D',to_date('16/12/11','RR/MM/DD'),to_date('16/12/11','RR/MM/DD'),'y');
Insert into EAST.TB_RESERVATIONS (LISTNO,MEMBERNO,PLANENO,SEAT,STARTDATE,ARRIVALDATE,CANCEL) values (5,4,6,'3-A',to_date('16/12/05','RR/MM/DD'),to_date('16/12/05','RR/MM/DD'),'n');
Insert into EAST.TB_RESERVATIONS (LISTNO,MEMBERNO,PLANENO,SEAT,STARTDATE,ARRIVALDATE,CANCEL) values (6,3,11,'2-E',to_date('16/12/01','RR/MM/DD'),to_date('16/12/01','RR/MM/DD'),'y');
Insert into EAST.TB_RESERVATIONS (LISTNO,MEMBERNO,PLANENO,SEAT,STARTDATE,ARRIVALDATE,CANCEL) values (7,6,16,'6-D',to_date('16/12/03','RR/MM/DD'),to_date('16/12/03','RR/MM/DD'),'n');
Insert into EAST.TB_RESERVATIONS (LISTNO,MEMBERNO,PLANENO,SEAT,STARTDATE,ARRIVALDATE,CANCEL) values (8,7,7,'1-B',to_date('16/12/05','RR/MM/DD'),to_date('16/12/05','RR/MM/DD'),'n');
Insert into EAST.TB_RESERVATIONS (LISTNO,MEMBERNO,PLANENO,SEAT,STARTDATE,ARRIVALDATE,CANCEL) values (9,8,2,'4-B',to_date('16/12/05','RR/MM/DD'),to_date('16/12/05','RR/MM/DD'),'y');
Insert into EAST.TB_RESERVATIONS (LISTNO,MEMBERNO,PLANENO,SEAT,STARTDATE,ARRIVALDATE,CANCEL) values (10,3,13,'5-C',to_date('16/12/06','RR/MM/DD'),to_date('16/12/06','RR/MM/DD'),'n');
Insert into EAST.TB_RESERVATIONS (LISTNO,MEMBERNO,PLANENO,SEAT,STARTDATE,ARRIVALDATE,CANCEL) values (11,5,19,'1-D',to_date('16/12/08','RR/MM/DD'),to_date('16/12/08','RR/MM/DD'),'n');
Insert into EAST.TB_RESERVATIONS (LISTNO,MEMBERNO,PLANENO,SEAT,STARTDATE,ARRIVALDATE,CANCEL) values (12,4,1,'1-A',to_date('16/12/07','RR/MM/DD'),to_date('16/12/07','RR/MM/DD'),'n');
Insert into EAST.TB_RESERVATIONS (LISTNO,MEMBERNO,PLANENO,SEAT,STARTDATE,ARRIVALDATE,CANCEL) values (13,4,1,'1-B',to_date('16/12/07','RR/MM/DD'),to_date('16/12/07','RR/MM/DD'),'n');
Insert into EAST.TB_RESERVATIONS (LISTNO,MEMBERNO,PLANENO,SEAT,STARTDATE,ARRIVALDATE,CANCEL) values (14,4,1,'1-C',to_date('16/12/07','RR/MM/DD'),to_date('16/12/07','RR/MM/DD'),'n');
--------------------------------------------------------
--  DDL for Index SYS_C0011785
--------------------------------------------------------

  CREATE UNIQUE INDEX "EAST"."SYS_C0011785" ON "EAST"."TB_MEMBER" ("MEMBERNO") 
  PCTFREE 10 INITRANS 2 MAXTRANS 255 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "USERS" ;
--------------------------------------------------------
--  DDL for Index SYS_C0011793
--------------------------------------------------------

  CREATE UNIQUE INDEX "EAST"."SYS_C0011793" ON "EAST"."TB_AVIATIONINFO" ("PLANENO") 
  PCTFREE 10 INITRANS 2 MAXTRANS 255 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "USERS" ;
--------------------------------------------------------
--  DDL for Index SYS_C0011801
--------------------------------------------------------

  CREATE UNIQUE INDEX "EAST"."SYS_C0011801" ON "EAST"."TB_RESERVATIONS" ("LISTNO") 
  PCTFREE 10 INITRANS 2 MAXTRANS 255 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "USERS" ;
--------------------------------------------------------
--  DDL for Index TB_AVIATIONINFO_PLANECODE_PK
--------------------------------------------------------

  CREATE UNIQUE INDEX "EAST"."TB_AVIATIONINFO_PLANECODE_PK" ON "EAST"."TB_AVIATIONINFO" ("PLANECODE") 
  PCTFREE 10 INITRANS 2 MAXTRANS 255 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "USERS" ;
--------------------------------------------------------
--  DDL for Index TB_MEMBER_ID_UK
--------------------------------------------------------

  CREATE UNIQUE INDEX "EAST"."TB_MEMBER_ID_UK" ON "EAST"."TB_MEMBER" ("ID") 
  PCTFREE 10 INITRANS 2 MAXTRANS 255 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "USERS" ;
--------------------------------------------------------
--  Constraints for Table TB_AVIATIONINFO
--------------------------------------------------------

  ALTER TABLE "EAST"."TB_AVIATIONINFO" MODIFY ("PLANECODE" NOT NULL ENABLE);
 
  ALTER TABLE "EAST"."TB_AVIATIONINFO" MODIFY ("PLANENAME" NOT NULL ENABLE);
 
  ALTER TABLE "EAST"."TB_AVIATIONINFO" MODIFY ("STARTTIME" NOT NULL ENABLE);
 
  ALTER TABLE "EAST"."TB_AVIATIONINFO" MODIFY ("ARRIVALTIME" NOT NULL ENABLE);
 
  ALTER TABLE "EAST"."TB_AVIATIONINFO" MODIFY ("STARTLOC" NOT NULL ENABLE);
 
  ALTER TABLE "EAST"."TB_AVIATIONINFO" MODIFY ("ARRIVALLOC" NOT NULL ENABLE);
 
  ALTER TABLE "EAST"."TB_AVIATIONINFO" ADD PRIMARY KEY ("PLANENO")
  USING INDEX PCTFREE 10 INITRANS 2 MAXTRANS 255 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "USERS"  ENABLE;
 
  ALTER TABLE "EAST"."TB_AVIATIONINFO" ADD CONSTRAINT "TB_AVIATIONINFO_PLANECODE_PK" UNIQUE ("PLANECODE")
  USING INDEX PCTFREE 10 INITRANS 2 MAXTRANS 255 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "USERS"  ENABLE;
--------------------------------------------------------
--  Constraints for Table TB_MEMBER
--------------------------------------------------------

  ALTER TABLE "EAST"."TB_MEMBER" MODIFY ("ID" NOT NULL ENABLE);
 
  ALTER TABLE "EAST"."TB_MEMBER" MODIFY ("PW" NOT NULL ENABLE);
 
  ALTER TABLE "EAST"."TB_MEMBER" MODIFY ("NAME" NOT NULL ENABLE);
 
  ALTER TABLE "EAST"."TB_MEMBER" ADD PRIMARY KEY ("MEMBERNO")
  USING INDEX PCTFREE 10 INITRANS 2 MAXTRANS 255 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "USERS"  ENABLE;
 
  ALTER TABLE "EAST"."TB_MEMBER" ADD CONSTRAINT "TB_MEMBER_ID_UK" UNIQUE ("ID")
  USING INDEX PCTFREE 10 INITRANS 2 MAXTRANS 255 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "USERS"  ENABLE;
--------------------------------------------------------
--  Constraints for Table TB_RESERVATIONS
--------------------------------------------------------

  ALTER TABLE "EAST"."TB_RESERVATIONS" MODIFY ("MEMBERNO" NOT NULL ENABLE);
 
  ALTER TABLE "EAST"."TB_RESERVATIONS" MODIFY ("PLANENO" NOT NULL ENABLE);
 
  ALTER TABLE "EAST"."TB_RESERVATIONS" MODIFY ("SEAT" NOT NULL ENABLE);
 
  ALTER TABLE "EAST"."TB_RESERVATIONS" MODIFY ("STARTDATE" NOT NULL ENABLE);
 
  ALTER TABLE "EAST"."TB_RESERVATIONS" MODIFY ("ARRIVALDATE" NOT NULL ENABLE);
 
  ALTER TABLE "EAST"."TB_RESERVATIONS" MODIFY ("CANCEL" NOT NULL ENABLE);
 
  ALTER TABLE "EAST"."TB_RESERVATIONS" ADD PRIMARY KEY ("LISTNO")
  USING INDEX PCTFREE 10 INITRANS 2 MAXTRANS 255 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "USERS"  ENABLE;