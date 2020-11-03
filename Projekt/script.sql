create table if not exists hibernate_sequences
(
    sequence_name varchar(255) not null
        primary key,
    next_val      bigint       null
);

create table if not exists t_platformy
(
    platforma_id int          not null
        primary key,
    nazev        varchar(255) null
);

create table if not exists t_produkty
(
    produkt_id             int          not null
        primary key,
    cena                   int          null,
    nazev                  varchar(255) null,
    sleva                  int          null,
    platforma_platforma_id int          null,
    aktivni                bit          not null,
    constraint FK2vn5afm3xo0i1phbuq20m4c1o
        foreign key (platforma_platforma_id) references t_platformy (platforma_id)
);

create table if not exists t_stavy
(
    stav_id int          not null
        primary key,
    nazev   varchar(255) null
);

create table if not exists t_typy_uctu
(
    typ_uctu_id int          not null
        primary key,
    nazev       varchar(255) null
);

create table if not exists t_ucty
(
    ucet_id              int          not null
        primary key,
    heslo                varchar(255) null,
    jmeno                varchar(255) null,
    prijmeni             varchar(255) null,
    ucet_login           varchar(255) null,
    typ_uctu_typ_uctu_id int          null,
    aktivni              bit          not null,
    email                varchar(255) null,
    constraint t_ucty_ucet_login_uindex
        unique (ucet_login),
    constraint FKhg3qdwliabtthfd8um5ms27t6
        foreign key (typ_uctu_typ_uctu_id) references t_typy_uctu (typ_uctu_id)
);

create table if not exists t_objednavky
(
    objednavka_id   int         not null
        primary key,
    cena            int         null,
    datum_objednani datetime(6) null,
    ucet_ucet_id    int         null,
    stav_stav_id    int         null,
    constraint FK3oc8pkq5ogq7nx8pbho78x93p
        foreign key (ucet_ucet_id) references t_ucty (ucet_id),
    constraint FK6d4e8jsn4tqrqpmk48ay6e2io
        foreign key (stav_stav_id) references t_stavy (stav_id)
);

create table if not exists t_adresy
(
    adresa_id                int          not null
        primary key,
    cps                      varchar(255) null,
    mesto                    varchar(255) null,
    psc                      varchar(255) null,
    ulice                    varchar(255) null,
    objednavka_objednavka_id int          null,
    ucet_ucet_id             int          null,
    constraint FKfeco72ituh02ji7lqxhjr6emv
        foreign key (objednavka_objednavka_id) references t_objednavky (objednavka_id),
    constraint FKo0c6aaenauiegjpcjgvhnmi0d
        foreign key (ucet_ucet_id) references t_ucty (ucet_id)
);

create table if not exists t_polozky
(
    polozka_id               int not null
        primary key,
    cena                     int not null,
    pocet                    int not null,
    sleva                    int not null,
    objednavka_objednavka_id int null,
    produkt_produkt_id       int null,
    constraint FK22ce5jt8cff486scchhb4o0to
        foreign key (produkt_produkt_id) references t_produkty (produkt_id),
    constraint FKin9lcsoav2w1csnvr5vljbl9m
        foreign key (objednavka_objednavka_id) references t_objednavky (objednavka_id)
);

create table if not exists t_polozky_kosik
(
    polozka_kosiks_id  int not null
        primary key,
    cena               int not null,
    pocet              int not null,
    sleva              int not null,
    produkt_produkt_id int null,
    ucet_ucet_id       int null,
    constraint FK8sin8rvcxay6dtil2i3ifjh4
        foreign key (produkt_produkt_id) references t_produkty (produkt_id),
    constraint FKpr3c3285rmnjaeoe5pop2k8nf
        foreign key (ucet_ucet_id) references t_ucty (ucet_id)
);

create view information_schema.CHARACTER_SETS as
-- missing source code
;

create view information_schema.COLLATIONS as
-- missing source code
;

create view information_schema.COLLATION_CHARACTER_SET_APPLICABILITY as
-- missing source code
;

create view information_schema.COLUMNS as
-- missing source code
;

create view information_schema.COLUMN_PRIVILEGES as
-- missing source code
;

create view information_schema.ENGINES as
-- missing source code
;

create view information_schema.EVENTS as
-- missing source code
;

create view information_schema.FILES as
-- missing source code
;

create view information_schema.GLOBAL_STATUS as
-- missing source code
;

create view information_schema.GLOBAL_VARIABLES as
-- missing source code
;

create view information_schema.INNODB_BUFFER_PAGE as
-- missing source code
;

create view information_schema.INNODB_BUFFER_PAGE_LRU as
-- missing source code
;

create view information_schema.INNODB_BUFFER_POOL_STATS as
-- missing source code
;

create view information_schema.INNODB_CMP as
-- missing source code
;

create view information_schema.INNODB_CMPMEM as
-- missing source code
;

create view information_schema.INNODB_CMPMEM_RESET as
-- missing source code
;

create view information_schema.INNODB_CMP_PER_INDEX as
-- missing source code
;

create view information_schema.INNODB_CMP_PER_INDEX_RESET as
-- missing source code
;

create view information_schema.INNODB_CMP_RESET as
-- missing source code
;

create view information_schema.INNODB_FT_BEING_DELETED as
-- missing source code
;

create view information_schema.INNODB_FT_CONFIG as
-- missing source code
;

create view information_schema.INNODB_FT_DEFAULT_STOPWORD as
-- missing source code
;

create view information_schema.INNODB_FT_DELETED as
-- missing source code
;

create view information_schema.INNODB_FT_INDEX_CACHE as
-- missing source code
;

create view information_schema.INNODB_FT_INDEX_TABLE as
-- missing source code
;

create view information_schema.INNODB_LOCKS as
-- missing source code
;

create view information_schema.INNODB_LOCK_WAITS as
-- missing source code
;

create view information_schema.INNODB_METRICS as
-- missing source code
;

create view information_schema.INNODB_SYS_COLUMNS as
-- missing source code
;

create view information_schema.INNODB_SYS_DATAFILES as
-- missing source code
;

create view information_schema.INNODB_SYS_FIELDS as
-- missing source code
;

create view information_schema.INNODB_SYS_FOREIGN as
-- missing source code
;

create view information_schema.INNODB_SYS_FOREIGN_COLS as
-- missing source code
;

create view information_schema.INNODB_SYS_INDEXES as
-- missing source code
;

create view information_schema.INNODB_SYS_TABLES as
-- missing source code
;

create view information_schema.INNODB_SYS_TABLESPACES as
-- missing source code
;

create view information_schema.INNODB_SYS_TABLESTATS as
-- missing source code
;

create view information_schema.INNODB_SYS_VIRTUAL as
-- missing source code
;

create view information_schema.INNODB_TEMP_TABLE_INFO as
-- missing source code
;

create view information_schema.INNODB_TRX as
-- missing source code
;

create view information_schema.KEY_COLUMN_USAGE as
-- missing source code
;

create view information_schema.OPTIMIZER_TRACE as
-- missing source code
;

create view information_schema.PARAMETERS as
-- missing source code
;

create view information_schema.PARTITIONS as
-- missing source code
;

create view information_schema.PLUGINS as
-- missing source code
;

create view information_schema.PROCESSLIST as
-- missing source code
;

create view information_schema.PROFILING as
-- missing source code
;

create view information_schema.REFERENTIAL_CONSTRAINTS as
-- missing source code
;

create view information_schema.ROUTINES as
-- missing source code
;

create view information_schema.SCHEMATA as
-- missing source code
;

create view information_schema.SCHEMA_PRIVILEGES as
-- missing source code
;

create view information_schema.SESSION_STATUS as
-- missing source code
;

create view information_schema.SESSION_VARIABLES as
-- missing source code
;

create view information_schema.STATISTICS as
-- missing source code
;

create view information_schema.TABLES as
-- missing source code
;

create view information_schema.TABLESPACES as
-- missing source code
;

create view information_schema.TABLE_CONSTRAINTS as
-- missing source code
;

create view information_schema.TABLE_PRIVILEGES as
-- missing source code
;

create view information_schema.TRIGGERS as
-- missing source code
;

create view information_schema.USER_PRIVILEGES as
-- missing source code
;

create view information_schema.VIEWS as
-- missing source code
;


