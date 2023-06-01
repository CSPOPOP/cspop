DROP TABLE IF EXISTS notice_board_upload_file;
DROP TABLE IF EXISTS notice_board;
DROP TABLE IF EXISTS admins;
DROP TABLE IF EXISTS other_qualifications;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS submit_form;
DROP TABLE IF EXISTS final_form;
DROP TABLE IF EXISTS final_form_upload_file;
DROP TABLE IF EXISTS interim_form;
DROP TABLE IF EXISTS interim_form_upload_file;
DROP TABLE IF EXISTS other_form;
DROP TABLE IF EXISTS other_form_upload_file;
DROP TABLE IF EXISTS proposal_form;
DROP TABLE IF EXISTS schedules;
DROP TABLE IF EXISTS excel_board;
DROP TABLE IF EXISTS guidance_board;
DROP TABLE IF EXISTS certification_board;
DROP TABLE IF EXISTS schedule_board;
SET FOREIGN_KEY_CHECKS = 0;

-- -----------------------------------------------------
-- Table `test`.`admins`
-- -----------------------------------------------------
CREATE TABLE admins
(
    `id`                 BIGINT       NOT NULL,
    `created_date`       DATETIME(6)  NULL DEFAULT NULL COMMENT '등록일',
    `last_modified_date` DATETIME(6)  NULL DEFAULT NULL COMMENT '수정일',
    `admin_id`           VARCHAR(255) NOT NULL COMMENT '관리자 아이디',
    `admin_name`         VARCHAR(255) NOT NULL COMMENT '관리자 이름',
    `admin_password`     VARCHAR(255) NOT NULL COMMENT '관리자 비밀번호',
    PRIMARY KEY (`id`)
)
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8mb4;

-- -----------------------------------------------------
-- Table `test`.`notice_board`
-- -----------------------------------------------------
CREATE TABLE notice_board
(
    `id`                 BIGINT       NOT NULL,
    `created_date`       DATETIME(6)  NULL DEFAULT NULL COMMENT '등록일',
    `last_modified_date` DATETIME(6)  NULL DEFAULT NULL COMMENT '수정일',
    `fixed`              BIT(1)       NOT NULL COMMENT '고정 여부',
    `text`               VARCHAR(15000) NOT NULL COMMENT '공지사항 본문',
    `title`              VARCHAR(255) NOT NULL COMMENT '공지사항 제목',
    `views`              INT          NULL DEFAULT NULL COMMENT '조회수',
    `admins_id`          BIGINT       NULL DEFAULT NULL,
    PRIMARY KEY (`id`),
    CONSTRAINT `fk_notice_board_to_admin`
        FOREIGN KEY (admins_id)
            REFERENCES admins (id)
)
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8mb4;

-- -----------------------------------------------------
-- Table `test`.`submit_form`
-- -----------------------------------------------------
CREATE TABLE submit_form
(
    `id`                      BIGINT       NOT NULL,
    `created_date`            DATETIME(6)  NULL DEFAULT NULL COMMENT '등록일',
    `last_modified_date`      DATETIME(6)  NULL DEFAULT NULL COMMENT '수정일',
    `department`              VARCHAR(255) NOT NULL COMMENT '소속학과',
    `student_id`              VARCHAR(255) NOT NULL COMMENT '학번',
    `student_name`            VARCHAR(255) NOT NULL COMMENT '학생 이름',
    `approval`                BIT(1)       NOT NULL COMMENT '승인여부',
    `graduation_requirements` VARCHAR(255) NULL DEFAULT NULL COMMENT '졸업요건',
    PRIMARY KEY (`id`)
)
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8mb4;

-- -----------------------------------------------------
-- Table final_form
-- -----------------------------------------------------
CREATE TABLE final_form
(
    `id`                        BIGINT       NOT NULL,
    `created_date`              DATETIME(6)  NULL DEFAULT NULL COMMENT '등록일',
    `last_modified_date`        DATETIME(6)  NULL DEFAULT NULL COMMENT '수정일',
    `approval`                  BIT(1)       NOT NULL COMMENT '승인여부',
    `title`                     VARCHAR(255) NOT NULL COMMENT '제목',
    `division`                  VARCHAR(255) NOT NULL COMMENT '구분',
    `qualification`             VARCHAR(255) NOT NULL COMMENT '자격요건',
    `page_number`               INT          NOT NULL COMMENT '쪽수',
    `rejection`                     BIT(1)       NOT NULL COMMENT '반려여부',
    `reject_reason`             VARCHAR(5000) NULL DEFAULT NULL COMMENT '반려사유',
    `final_form_upload_file_id` BIGINT       NULL DEFAULT NULL,
    PRIMARY KEY (`id`),
    CONSTRAINT `fk_final_form_to_final_form_upload_file`
        FOREIGN KEY (`final_form_upload_file_id`)
            REFERENCES final_form_upload_file (`id`)
)
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8mb4;

-- -----------------------------------------------------
-- Table `test`.`final_form_upload_file`
-- -----------------------------------------------------
CREATE TABLE final_form_upload_file
(
    `id`               BIGINT       NOT NULL,
    `store_file_name`  VARCHAR(255) NULL DEFAULT NULL COMMENT '서버 내부에서 관리하는 파일명',
    `upload_file_name` VARCHAR(255) NULL DEFAULT NULL COMMENT '유저가 업로드한 파일명',
    PRIMARY KEY (`id`)
)
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8mb4;


-- -----------------------------------------------------
-- interim_form
-- -----------------------------------------------------
CREATE TABLE interim_form
(
    `id`                          BIGINT       NOT NULL,
    `created_date`                DATETIME(6)  NULL DEFAULT NULL COMMENT '등록일',
    `last_modified_date`          DATETIME(6)  NULL DEFAULT NULL COMMENT '수정일',
    `approval`                    BIT(1)       NOT NULL COMMENT '승인여부',
    `title`                       VARCHAR(255) NOT NULL COMMENT '제목',
    `division`                    VARCHAR(255) NOT NULL COMMENT '구분',
    `text`                        VARCHAR(5000) NOT NULL COMMENT '진행내용',
    `plan`                        VARCHAR(5000) NOT NULL COMMENT '향후계획',
    `rejection`                      BIT(1)       NOT NULL COMMENT '반려여부',
    `reject_reason`               VARCHAR(5000) NULL DEFAULT NULL COMMENT '반려사유',
    `interim_form_upload_file_id` BIGINT       NULL DEFAULT NULL,
    PRIMARY KEY (`id`),
    CONSTRAINT `fk_interim_form_to_interim_form_upload_file`
        FOREIGN KEY (`interim_form_upload_file_id`)
            REFERENCES interim_form_upload_file (`id`)
)
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8mb4;


-- -----------------------------------------------------
-- Table `test`.`interim_form_upload_file`
-- -----------------------------------------------------
CREATE TABLE interim_form_upload_file
(
    `id`               BIGINT       NOT NULL,
    `store_file_name`  VARCHAR(255) NULL DEFAULT NULL COMMENT '서버 내부에서 관리하는 파일명',
    `upload_file_name` VARCHAR(255) NULL DEFAULT NULL COMMENT '유저가 업로드한 파일명',
    PRIMARY KEY (`id`)
)
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8mb4;

-- -----------------------------------------------------
-- other_form
-- -----------------------------------------------------
CREATE TABLE other_form
(
    `id`                        BIGINT       NOT NULL,
    `created_date`              DATETIME(6)  NULL DEFAULT NULL COMMENT '등록일',
    `last_modified_date`        DATETIME(6)  NULL DEFAULT NULL COMMENT '수정일',
    `approval`                  BIT(1)       NOT NULL COMMENT '승인여부',
    `division`                  VARCHAR(255) NOT NULL COMMENT '구분',
    `title`                     VARCHAR(255) NOT NULL COMMENT '제목',
    `text`                      VARCHAR(5000) NOT NULL COMMENT '본문',
    `rejection`                    BIT(1)       NOT NULL COMMENT '반려여부',
    `reject_reason`             VARCHAR(5000) NULL DEFAULT NULL COMMENT '반려사유',
    `other_form_upload_file_id` BIGINT       NULL DEFAULT NULL,
    PRIMARY KEY (`id`),
    CONSTRAINT `fk_other_form_to_other_form_upload_file`
        FOREIGN KEY (`other_form_upload_file_id`)
            REFERENCES other_form_upload_file (`id`)
)
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8mb4;

-- -----------------------------------------------------
-- Table `test`.`other_form_upload_file`
-- -----------------------------------------------------
CREATE TABLE other_form_upload_file
(
    `id`               BIGINT       NOT NULL,
    `store_file_name`  VARCHAR(255) NULL DEFAULT NULL COMMENT '서버 내부에서 관리하는 파일명',
    `upload_file_name` VARCHAR(255) NULL DEFAULT NULL COMMENT '유저가 업로드한 파일명',
    PRIMARY KEY (`id`)
)
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8mb4;

-- -----------------------------------------------------
-- proposal_form
-- -----------------------------------------------------
CREATE TABLE proposal_form
(
    `id`                 BIGINT       NOT NULL,
    `created_date`       DATETIME(6)  NULL DEFAULT NULL COMMENT '등록일',
    `last_modified_date` DATETIME(6)  NULL DEFAULT NULL COMMENT '수정일',
    `student_id`         VARCHAR(255) NOT NULL COMMENT '학번',
    `graduation_date`    VARCHAR(255) NOT NULL COMMENT '졸업날짜',
    `advisor`            VARCHAR(255) NOT NULL COMMENT '담당교수',
    `student_name`       VARCHAR(255) NOT NULL COMMENT '학생이름',
    `department`         VARCHAR(255) NOT NULL COMMENT '학부',
    `qualification`      VARCHAR(255) NOT NULL COMMENT '자격',
    `approval`           BIT(1)       NOT NULL COMMENT '승인여부',
    `title`              VARCHAR(255) NOT NULL COMMENT '제목',
    `division`           VARCHAR(255) NOT NULL COMMENT '구분',
    `keyword`            VARCHAR(255) NOT NULL COMMENT '키워드',
    `text`               VARCHAR(5000) NOT NULL COMMENT '본문',
    `rejection`             BIT(1)       NOT NULL COMMENT '반려여부',
    `reject_reason`      VARCHAR(5000) NULL DEFAULT NULL COMMENT '반려사유',
    PRIMARY KEY (`id`)
)
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8mb4;

-- -----------------------------------------------------
-- Table `test`.`users`
-- -----------------------------------------------------
CREATE TABLE users
(
    `id`                 BIGINT       NOT NULL AUTO_INCREMENT,
    `created_date`       DATETIME(6)  NULL DEFAULT NULL COMMENT '등록일',
    `last_modified_date` DATETIME(6)  NULL DEFAULT NULL COMMENT '수정일',
    `birth`              DATE         NOT NULL COMMENT '생일',
    `classification`     VARCHAR(255) NULL DEFAULT NULL COMMENT '신분',
    `department`         VARCHAR(255) NOT NULL COMMENT '소속학과',
    `email`              VARCHAR(255) NOT NULL COMMENT '이메일',
    `phone_number`       VARCHAR(255) NOT NULL COMMENT '전화번호',
    `sex`                VARCHAR(255) NULL DEFAULT NULL COMMENT '성별',
    `student_id`         VARCHAR(255) NOT NULL COMMENT '학번',
    `student_name`       VARCHAR(255) NOT NULL COMMENT '학생 이름',
    `student_password`   VARCHAR(255) NOT NULL COMMENT '학생 비밀번호',
    `answer_Pw`          VARCHAR(255) NOT NULL COMMENT '비밀번호 대답',
    `submit_form_id`     BIGINT       NULL DEFAULT NULL,
    `proposal_form_id`   BIGINT       NULL DEFAULT NULL,
    `interim_form_id`    BIGINT       NULL DEFAULT NULL,
    `final_form_id`      BIGINT       NULL DEFAULT NULL,
    `other_form_id`      BIGINT       NULL DEFAULT NULL,

    PRIMARY KEY (`id`),
    UNIQUE INDEX `submit_form_id_unique` (`submit_form_id` ASC) VISIBLE,
    CONSTRAINT `fk_users_to_submit_form`
        FOREIGN KEY (`submit_form_id`)
            REFERENCES submit_form (id)
)
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8mb4;

-- -----------------------------------------------------
-- Table `test`.`notice_board_upload_file`
-- -----------------------------------------------------
CREATE TABLE notice_board_upload_file
(
    `id`               BIGINT       NOT NULL,
    `store_file_name`  VARCHAR(255) NULL DEFAULT NULL COMMENT '서버 내부에서 관리하는 파일명',
    `upload_file_name` VARCHAR(255) NULL DEFAULT NULL COMMENT '관리자가 업로드한 파일명',
    `notice_board_id`  BIGINT       NULL DEFAULT NULL,
    PRIMARY KEY (`id`),
    CONSTRAINT `fk_notice_board_upload_file_to_notice_board`
        FOREIGN KEY (`notice_board_id`)
            REFERENCES notice_board (`id`)
)
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8mb4;

-- -----------------------------------------------------
-- Table `test`.`schedules`
-- -----------------------------------------------------
CREATE TABLE schedules
(
    `id`                 BIGINT       NOT NULL,
    `created_date`       DATETIME(6)  NULL DEFAULT NULL COMMENT '등록일',
    `last_modified_date` DATETIME(6)  NULL DEFAULT NULL COMMENT '수정일',
    `end_date`           DATE         NULL DEFAULT NULL COMMENT '종료 일정',
    `schedule_state`     VARCHAR(255) NULL DEFAULT NULL,
    `start_date`         DATE         NULL DEFAULT NULL COMMENT '시작 일정',
    `step`               VARCHAR(255) NULL DEFAULT NULL COMMENT '단계',
    PRIMARY KEY (`id`)
)
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8mb4;

-- -----------------------------------------------------
-- Table `test`.`excel_board`
-- -----------------------------------------------------
CREATE TABLE excel_board
(
    `id`                   BIGINT       NOT NULL,
    `created_date`         DATETIME(6)  NULL DEFAULT NULL COMMENT '등록일',
    `last_modified_date`   DATETIME(6)  NULL DEFAULT NULL COMMENT '수정일',
    `capstone_completion`  VARCHAR(255) NULL DEFAULT NULL COMMENT '캡스톤 이수',
    `graduation_date`      VARCHAR(255) NULL DEFAULT NULL COMMENT '학생 졸업날짜',
    `qualifications` VARCHAR(255) NULL DEFAULT NULL COMMENT '자격',
    `professor_name`       VARCHAR(255) NULL DEFAULT NULL COMMENT '교수 이름',
    `state`                VARCHAR(255) NULL DEFAULT NULL COMMENT '상태',
    `step`                 VARCHAR(255) NULL DEFAULT NULL COMMENT '단계',
    `student_id`           VARCHAR(255) NULL DEFAULT NULL COMMENT '학번',
    `student_name`         VARCHAR(255) NULL DEFAULT NULL COMMENT '학생 이름',
    PRIMARY KEY (`id`)
)
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8mb4;

-- -----------------------------------------------------
-- Table `test`.`guidance_board`
-- -----------------------------------------------------
CREATE TABLE guidance_board
(
    `id`                 BIGINT       NOT NULL,
    `created_date`       DATETIME(6)  NULL DEFAULT NULL COMMENT '등록일',
    `last_modified_date` DATETIME(6)  NULL DEFAULT NULL COMMENT '수정일',
    `text`               VARCHAR(15000) NULL DEFAULT NULL COMMENT '안내 및 내규 본문',
    PRIMARY KEY (`id`)
)
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8mb4;

-- -----------------------------------------------------
-- Table `test`.`schedule_board`
-- -----------------------------------------------------
CREATE TABLE schedule_board
(
    `id`                        BIGINT       NOT NULL,
    `created_date`              DATETIME(6)  NULL DEFAULT NULL COMMENT '등록일',
    `last_modified_date`        DATETIME(6)  NULL DEFAULT NULL COMMENT '수정일',
    `final_pass_text`           VARCHAR(3000) NULL DEFAULT NULL COMMENT '최종통과 본문',
    `final_report_text`         VARCHAR(2550) NULL DEFAULT NULL COMMENT '최종보고서 본문',
    `interim_report_text`       VARCHAR(2550) NULL DEFAULT NULL COMMENT '중간보고서 본문',
    `other_qualifications_text` VARCHAR(2550) NULL DEFAULT NULL COMMENT '기타자격 본문',
    `proposal_text`             VARCHAR(2550) NULL DEFAULT NULL COMMENT '제안서 본문',
    `received_text`             VARCHAR(2550) NULL DEFAULT NULL COMMENT '신청접수 본문',
    PRIMARY KEY (`id`)
)
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8mb4;

-- -----------------------------------------------------
-- Table `test`.`certification_board`
-- -----------------------------------------------------
CREATE TABLE certification_board
(
    `id`                     BIGINT       NOT NULL,
    `created_date`           DATETIME(6)  NULL DEFAULT NULL COMMENT '등록일',
    `last_modified_date`     DATETIME(6)  NULL DEFAULT NULL COMMENT '수정일',
    `department`             VARCHAR(255) NULL DEFAULT NULL COMMENT '소속 학과',
    `student_id`             VARCHAR(255) NULL DEFAULT NULL COMMENT '학번',
    `student_name`           VARCHAR(255) NULL DEFAULT NULL COMMENT '학생 이름',
    `current_semester`       VARCHAR(255) NULL DEFAULT NULL COMMENT '현재 학기',
    `professional_education` VARCHAR(255) NULL DEFAULT NULL COMMENT '전문교양 학점',
    `msc_bsm`                VARCHAR(255) NULL DEFAULT NULL COMMENT 'MSC/BSM 학점',
    `design`                 VARCHAR(255) NULL DEFAULT NULL COMMENT '설계 학점',
    `major`                  VARCHAR(255) NULL DEFAULT NULL COMMENT '전공 학점',
    `essential`              VARCHAR(255) NULL DEFAULT NULL COMMENT '필수 과목',
    `first_and_last`         VARCHAR(255) NULL DEFAULT NULL COMMENT '선/후수 과목',
    `total`                  VARCHAR(255) NULL DEFAULT NULL COMMENT '총 학점',
    `special_note`           VARCHAR(5000) NULL DEFAULT NULL COMMENT '특이사항',
    PRIMARY KEY (`id`)
)
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8mb4;

-- -----------------------------------------------------
-- alter table
-- -----------------------------------------------------
alter table excel_board
    modify id bigint auto_increment;

alter table excel_board
    auto_increment = 1;

alter table certification_board
    modify id bigint auto_increment;

alter table certification_board
    auto_increment = 1;

alter table admins
    modify id bigint auto_increment;

alter table admins
    auto_increment = 1;

alter table notice_board
    modify id bigint auto_increment;

alter table notice_board
    auto_increment = 1;

alter table notice_board_upload_file
    modify id bigint auto_increment;

alter table notice_board_upload_file
    auto_increment = 1;

alter table submit_form
    modify id bigint auto_increment;

alter table submit_form
    auto_increment = 1;

alter table proposal_form
    modify id bigint auto_increment;

alter table proposal_form
    auto_increment = 1;

alter table interim_form
    modify id bigint auto_increment;

alter table interim_form
    auto_increment = 1;

alter table interim_form_upload_file
    modify id bigint auto_increment;

alter table interim_form_upload_file
    auto_increment = 1;

alter table final_form
    modify id bigint auto_increment;

alter table final_form
    auto_increment = 1;

alter table final_form_upload_file
    modify id bigint auto_increment;

alter table final_form_upload_file
    auto_increment = 1;

alter table other_form
    modify id bigint auto_increment;

alter table other_form
    auto_increment = 1;

alter table other_form_upload_file
    modify id bigint auto_increment;

alter table other_form_upload_file
    auto_increment = 1;