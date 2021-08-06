CREATE TABLE "TB_REPORT"(
    id number(19,0) not null,
    "PATH" VARCHAR(255) NOT NULL,
	"OWNER" VARCHAR(255) NOT NULL,
	"REQUESTED_DATE" TIMESTAMP NOT NULL,
	"PERCENTAGE_TO_DONE" NUMBER(3,0) NOT NULL,
	"CONCLUDED_DATE" TIMESTAMP,
    "COUNT_LINES" NUMBER(9,0),
	primary key (id)
)

create sequence tb_report_seq start with 1 increment by  1;