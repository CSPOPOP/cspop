--  users 데이터
INSERT INTO users
VALUES (1, '2023-03-14 12:35:29.857156', '2023-03-14 12:35:29.857156', '2023-09-01', 'UNDERGRADUATE_STUDENT', '컴퓨터공학부',
        '1234@naver.com', '010-1234-5678', 'FEMALE', '201811612', '유명한',
        '$2a$10$jvXXjxBH9uxs6wCjsOapu.oCqPdQNO22/VvfNYbyWvlAz1GDrjO76', '서울', 1, null, null, null, null);
INSERT INTO users
VALUES (2, '2023-03-14 12:35:29.857156', '2023-03-14 12:35:29.857156', '2023-09-01', 'UNDERGRADUATE_STUDENT', '컴퓨터공학부',
        '1234@naver.com', '010-1234-5678', 'FEMALE', '201812753', '함현준',
        '$2a$10$jvXXjxBH9uxs6wCjsOapu.oCqPdQNO22/VvfNYbyWvlAz1GDrjO76', '서울', 2, null, null, null, null);
INSERT INTO users
VALUES (3, '2023-03-14 12:35:29.857156', '2023-03-14 12:35:29.857156', '2023-09-01', 'UNDERGRADUATE_STUDENT', '컴퓨터공학부',
        '1234@naver.com', '010-1234-5678', 'FEMALE', '201811111', '홍길동',
        '$2a$10$jvXXjxBH9uxs6wCjsOapu.oCqPdQNO22/VvfNYbyWvlAz1GDrjO76', '서울', null, null, null, null, null);

INSERT INTO users
VALUES (4, '2023-03-14 12:35:29.857156', '2023-03-14 12:35:29.857156', '2023-09-01', 'UNDERGRADUATE_STUDENT', '컴퓨터공학부',
        '1234@naver.com', '010-1234-5678', 'FEMALE', '202013149', '고은아',
        '$2a$10$jvXXjxBH9uxs6wCjsOapu.oCqPdQNO22/VvfNYbyWvlAz1GDrjO76', '서울', 3, null, null, null, null);

INSERT INTO users
VALUES (5, '2023-03-14 12:35:29.857156', '2023-03-14 12:35:29.857156', '2023-09-01', 'UNDERGRADUATE_STUDENT', '컴퓨터공학부',
        '1234@naver.com', '010-1234-5678', 'MALE', '202016034', '이도현',
        '$2a$10$jvXXjxBH9uxs6wCjsOapu.oCqPdQNO22/VvfNYbyWvlAz1GDrjO76', '서울', 4, null, null, null, null);


INSERT INTO users
VALUES (6, '2023-03-14 12:35:29.857156', '2023-03-14 12:35:29.857156', '2023-09-01', 'UNDERGRADUATE_STUDENT', '컴퓨터공학부',
        '1234@naver.com', '010-1234-5678', 'MALE', '201812301', '정경원',
        '$2a$10$jvXXjxBH9uxs6wCjsOapu.oCqPdQNO22/VvfNYbyWvlAz1GDrjO76', '서울', 6, null, null, null, null);




--  schedules 데이터
INSERT INTO schedules
VALUES (1, '2023-03-14 13:52:38.000000', '2023-03-14 13:52:46.764849', '2023-03-24', 'WAIT', '2023-03-15', 'RECEIVED'),
       (2, '2023-03-14 13:52:38.000000', '2023-03-14 13:52:50.544745', '2023-03-14', 'PROCEEDING', '2023-03-13',
        'PROPOSAL'),
       (3, '2023-03-14 13:52:38.000000', '2023-03-14 13:52:38.000000', '2023-03-15', 'PROCEEDING', '2023-03-13',
        'INTERIM_REPORT'),
       (4, '2023-03-14 13:52:38.000000', '2023-03-14 13:52:38.000000', '2023-03-15', 'PROCEEDING', '2023-03-13',
        'FINAL_REPORT'),
       (5, '2023-03-14 13:52:38.000000', '2023-03-14 13:52:38.000000', '2023-03-15', 'PROCEEDING', '2023-03-13',
        'OTHER_QUALIFICATIONS'),
       (6, '2023-03-14 13:52:38.000000', '2023-03-14 13:52:38.000000', '2023-03-15', 'PROCEEDING', '2023-03-13',
        'FINAL_PASS');

--  notice_board 데이터
INSERT INTO `CSPOP`.`notice_board` (`id`, `created_date`, `last_modified_date`, `fixed`, `text`, `title`, `views`,
                                    `admins_id`)
VALUES ('1', '1999-10-13', '1999-10-13', 1, 'text1', 'text1', 1, '1');
INSERT INTO `CSPOP`.`notice_board` (`id`, `created_date`, `last_modified_date`, `fixed`, `text`, `title`, `views`,
                                    `admins_id`)
VALUES ('2', '1999-10-13', '1999-10-13', 0, 'text1', 'text1', 1, '1');
INSERT INTO `CSPOP`.`notice_board` (`id`, `created_date`, `last_modified_date`, `fixed`, `text`, `title`, `views`,
                                    `admins_id`)
VALUES ('3', '1999-10-13', '1999-10-13', 0, 'text1', 'text1', 1, '1');
INSERT INTO `CSPOP`.`notice_board` (`id`, `created_date`, `last_modified_date`, `fixed`, `text`, `title`, `views`,
                                    `admins_id`)
VALUES ('4', '1999-10-13', '1999-10-13', 1, 'text1', 'text1', 1, '1');
INSERT INTO `CSPOP`.`notice_board` (`id`, `created_date`, `last_modified_date`, `fixed`, `text`, `title`, `views`,
                                    `admins_id`)
VALUES ('5', '1999-10-13', '1999-10-13', 0, 'text1', 'text1', 1, '1');
INSERT INTO `CSPOP`.`notice_board` (`id`, `created_date`, `last_modified_date`, `fixed`, `text`, `title`, `views`,
                                    `admins_id`)
VALUES ('6', '1999-10-13', '1999-10-13', 0, 'text1', 'text1', 1, '1');
INSERT INTO `CSPOP`.`notice_board` (`id`, `created_date`, `last_modified_date`, `fixed`, `text`, `title`, `views`,
                                    `admins_id`)
VALUES ('7', '1999-10-13', '1999-10-13', 0, 'text1', 'text1', 1, '1');
INSERT INTO `CSPOP`.`notice_board` (`id`, `created_date`, `last_modified_date`, `fixed`, `text`, `title`, `views`,
                                    `admins_id`)
VALUES ('8', '1999-10-13', '1999-10-13', 1, 'text1', 'text1', 1, '1');
INSERT INTO `CSPOP`.`notice_board` (`id`, `created_date`, `last_modified_date`, `fixed`, `text`, `title`, `views`,
                                    `admins_id`)
VALUES ('9', '1999-10-13', '1999-10-13', 1, 'text1', 'text1', 1, '1');

-- guidance_board 데이터
INSERT INTO `CSPOP`.`guidance_board`(`id`, `created_date`, `last_modified_date`, `text`)
VALUES (1, NOW(), NOW(), '안녕하세요');

-- schedule_board 데이터
insert into cspop.schedule_board(id, created_date, last_modified_date, final_pass_text, final_report_text,
                                 interim_report_text, other_qualifications_text, proposal_text, received_text)
VALUES (1, NOW(), NOW(), '내용', '내용', '내용', '내용', '내용', '내용');

-- submit_form 데이터
insert into submit_form
values (1, NOW(), NOW(), '컴퓨터공학부', '201811612', '유명한', 1, 'THESIS');
insert into submit_form
values (2, NOW(), NOW(), '컴퓨터공학부', '201812753', '함현준', 1, 'Other_Qualifications');
insert into submit_form
values (3, NOW(), NOW(), '컴퓨터공학부', '202013149', '고은아', 1, 'THESIS');
insert into submit_form
values (4, NOW(), NOW(), '컴퓨터공학부', '202016034', '이도현', 1, 'THESIS');
insert into submit_form
values (6, NOW(), NOW(), '컴퓨터공학부', '201812301', '정경원', 1, 'THESIS');


--  certification_board 데이터
INSERT INTO `CSPOP`.`certification_board`(`id`, `created_date`, `last_modified_date`, `department`, `student_id`,
                                          `student_name`, `current_semester`, `professional_education`, `msc_bsm`,
                                          `design`, `major`, `essential`, `first_and_last`, `total`, `special_note`)
VALUES (1, NOW(), NOW(), 'AI컴퓨터공학부', '201812345', '홍길동', '7', '6', '15', '7.5', '100', '부', '가', '126', '해당사항 없음');

-- excel_board 데이터
INSERT INTO `CSPOP`.`excel_board`
VALUES (1, NOW(), NOW(), '이수', '1999/10/13', '공모전', '김도훈', '미승인', '제안서', '201811612', '유명한');
INSERT INTO `CSPOP`.`excel_board`
VALUES (2, NOW(), NOW(), '이수', '1999/10/13', '공모전', '이은정', '미승인', '제안서', '201812753', '함현준');
INSERT INTO `CSPOP`.`excel_board`
VALUES (3, NOW(), NOW(), '이수', '1999/10/13', '공모전', '이은정', '미승인', '신청접수', '202013149', '고은아');
INSERT INTO `CSPOP`.`excel_board`
VALUES (4, NOW(), NOW(), '이수', '1999/10/13', '공모전', '이은정', '미승인', '신청접수', '202016034', '이도현');
INSERT INTO `CSPOP`.`excel_board`
VALUES (6, NOW(), NOW(), '이수', '1999/10/13', '공모전', '이은정', '미승인', '신청접수', '201812301', '정경원');

INSERT INTO `CSPOP`.`excel_board`
VALUES (96, NOW(), NOW(), '이수', '1999/10/13', '공모전', '이은정', '미승인', '중간보고서', '201812334', '가나다');

INSERT INTO `CSPOP`.`excel_board`
VALUES (97, NOW(), NOW(), '이수', '1999/10/13', '공모전', '이은정', '미승인', '최종보고서', '201812335', '김이최');

INSERT INTO `CSPOP`.`excel_board`
VALUES (98, NOW(), NOW(), '이수', '1999/10/13', '공모전', '이은정', '미승인', '기타자격', '201812336', '라마바');

INSERT INTO `CSPOP`.`excel_board`
VALUES (99, NOW(), NOW(), '이수', '1999/10/13', '공모전', '이은정', '미승인', '최종통과', '201812337', '사아자');